package attendance.controller;

import attendance.domain.*;
import attendance.view.InputView;
import attendance.view.OutputView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CrewsController {
    private static final String ATTENDANCES_CSV = "src/main/resources/attendances.csv";
    private static final LocalDate today = LocalDate.of(2024, 12, 16);

    private final InputView inputView;
    private final OutputView outputView;

    private final Map<Command, Consumer<Crews>> commandProcesses =
            new HashMap<>() {{
                put(Command.CONFIRM_ATTENDANCE, crews -> confirmAttendance(crews));
                put(Command.UPDATE_ATTENDANCE, crews -> updateAttendance(crews));
                put(Command.PRINT_CREW_ATTENDANCES, crews -> printCrewAttendances(crews));
                put(Command.PRINT_WARNING_EXPULSION_CREWS, crews -> printWarningExpulsionCrews(crews));
            }};

    public CrewsController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void run() {
        Crews crews = CrewsFactory.initFromCsv(ATTENDANCES_CSV, today);
        while(true) {
            try {
                String selectedCommand = inputView.selectCommand(today);
                if(selectedCommand.equals("Q")) break;

                if(selectedCommand.equals("1")) confirmAttendance(crews);
                if(selectedCommand.equals("2")) updateAttendance(crews);
                if(selectedCommand.equals("3")) printCrewAttendances(crews);
                if(selectedCommand.equals("4")) printWarningCrews(crews);
            } catch (IllegalArgumentException e) {
                outputView.printExceptionMessage(e);
            }
        }
    }

    private void confirmAttendance(final Crews crews) {
        Holiday.isHoliday(today.atStartOfDay());

        String nickname = inputView.inputNickname();
        Crew crew = crews.findCrewByNickname(nickname);

        LocalTime attendanceTime = inputView.inputAttendanceTime();
        Attendance attendance = crew.addAttendance(LocalDateTime.of(today, attendanceTime));

        outputView.printConfirmResult(attendance.getDateTime(), attendance.getStatus());
    }

    private void updateAttendance(final Crews crews) {
        String nickname = inputView.inputUpdateCrew();
        Crew crew = crews.findCrewByNickname(nickname);

        int updateDateInput = inputView.inputUpdateDate();
        LocalDate updateDate = LocalDate.of(today.getYear(), today.getMonth(), updateDateInput);
        Attendance beforeUpdateAttendance = crew.findAttendanceByDate(updateDate);

        LocalTime updateTime = inputView.inputUpdateAttendanceTime();
        Attendance afterUpdateAttendance = crew.updateAttendance(updateDate, updateTime);

        outputView.printUpdateResult(beforeUpdateAttendance, afterUpdateAttendance);
    }

    private void printCrewAttendances(final Crews crews) {
        String nickname = inputView.inputNickname();
        Crew crew = crews.findCrewByNickname(nickname);

        outputView.printCrewAttendances(crew);
        outputView.printCrewAttendanceStatusCount(crew);
    }

    private void printWarningCrews(final Crews crews) {
        outputView.printPenaltyCrews(crews.findWarningExpulsionCrews());
    }
}
