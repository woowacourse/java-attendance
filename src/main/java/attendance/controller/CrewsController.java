package attendance.controller;

import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.domain.CrewsFactory;
import attendance.domain.Holiday;
import attendance.view.InputView;
import attendance.view.OutputView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CrewsController {
    public static final String ATTENDANCES_CSV = "src/main/resources/attendances.csv";

    private final InputView inputView;
    private final OutputView outputView;

    public CrewsController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void run(LocalDate today) {
        Crews crews = CrewsFactory.initFromCsv(ATTENDANCES_CSV);

        String selectedCommand = inputView.selectCommand(today);

        if(selectedCommand.equals("1")) confirmAttendance(today, crews);
    }

    private void confirmAttendance(final LocalDate today, final Crews crews) {
        Holiday.isHoliday(today.atStartOfDay());

        String nickname = inputView.inputNickname();
        Crew crew = crews.findCrewByNickname(nickname);

        LocalTime attendanceTime = inputView.inputAttendanceTime();
        crew.addAttendance(LocalDateTime.of(today, attendanceTime));
        Attendance attendance = crew.addAttendance(LocalDateTime.of(today, attendanceTime));

        outputView.printConfirmAttendanceResult(attendance.getDateTime(), attendance.getStatus());
    }
}
