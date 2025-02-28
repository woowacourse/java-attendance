package attendance.controller;

import attendance.domain.*;
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
        Crews crews = CrewsFactory.initFromCsv(ATTENDANCES_CSV, today);

        String selectedCommand = inputView.selectCommand(today);

        if(selectedCommand.equals("1")) confirmAttendance(today, crews);
        if(selectedCommand.equals("2")) updateAttendance(today, crews);
        if(selectedCommand.equals("3")) printCrewAttendances(today, crews);
    }

    private void confirmAttendance(final LocalDate today, final Crews crews) {
        Holiday.isHoliday(today.atStartOfDay());

        String nickname = inputView.inputNickname();
        Crew crew = crews.findCrewByNickname(nickname);

        LocalTime attendanceTime = inputView.inputAttendanceTime();
        Attendance attendance = crew.addAttendance(LocalDateTime.of(today, attendanceTime));

        outputView.printConfirmResult(attendance.getDateTime(), attendance.getStatus());
    }

    private void updateAttendance(final LocalDate today, final Crews crews) {
        String nickname = inputView.inputUpdateCrew();
        Crew crew = crews.findCrewByNickname(nickname);

        int updateDateInput = inputView.inputUpdateDate();
        LocalDate updateDate = LocalDate.of(today.getYear(), today.getMonth(), updateDateInput);
        Attendance beforeUpdateAttendance = crew.findAttendanceByDate(updateDate);

        LocalTime updateTime = inputView.inputUpdateAttendanceTime();
        Attendance afterUpdateAttendance = crew.updateAttendance(updateDate, updateTime);

        outputView.printUpdateResult(beforeUpdateAttendance, afterUpdateAttendance);
    }

    private void printCrewAttendances(final LocalDate today, final Crews crews) {
        String nickname = inputView.inputNickname();
        Crew crew = crews.findCrewByNickname(nickname);

        outputView.printCrewAttendances(crew);
        outputView.printCrewAttendanceStatusCount(crew);
    }
}
