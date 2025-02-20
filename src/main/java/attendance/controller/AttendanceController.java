package attendance.controller;

import attendance.controller.util.AttendancesFileReader;
import attendance.controller.util.CrewAttendanceParser;
import attendance.controller.util.TimeFormatter;
import attendance.domain.*;
import attendance.service.CrewsService;
import attendance.view.InputView;
import attendance.view.OutputView;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttendanceController {
    private final CrewsService crewsService;
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController() {
        this.crewsService = new CrewsService();
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void run() {
        LocalDate now = LocalDate.of(2024, 12, 17);
        Crews crews = crewsService.init(CrewAttendanceParser.parseCrewAttendances(AttendancesFileReader.read()), now);
        while (true) {
            try {
                String inputMenu = inputView.inputMenu(now);
                if (selectMenu(inputMenu, crews, now)) break;
            } catch (Exception e) {
                outputView.printExceptionMessage(e);
            }
        }
    }

    private boolean selectMenu(String inputMenu, Crews crews, LocalDate now) {
        if (inputMenu.equals("1")) confirmAttendance(crews, now);
        if (inputMenu.equals("2")) updateAttendance(crews, now);
        if (inputMenu.equals("3")) printAttendanceByCrew(crews, now);
        if (inputMenu.equals("4")) printWarningCrews(crews);

        return inputMenu.equals("Q");
    }

    private void confirmAttendance(final Crews crews, final LocalDate now) {
        Crew crew = crews.findByName(inputView.inputNickname());
        crew.existInAttendances(now);

        LocalDateTime attendDateTime = TimeFormatter.format(now, inputView.inputAttendTime());
        Attendance attendance = new Attendance(attendDateTime);

        crew.addAttendance(attendance);
        outputView.printAttendanceResult(attendance);
    }

    private void updateAttendance(final Crews crews, final LocalDate now) {
        Crew crew = crews.findByName(inputView.inputNickname());

        LocalDate updateDate = LocalDate.of(now.getYear(), now.getMonthValue(), inputView.inputUpdateDate());
        String inputUpdateTime = inputView.inputUpdateTime();

        Attendance before = crew.findAttendanceByDate(updateDate);

        // TODO : dto로 추출
        AttendanceStatus beforeStatus = before.getStatus();
        LocalDateTime beforeTime = before.getDateTime();

        Attendance after = crew.updateAttendance(TimeFormatter.format(updateDate, inputUpdateTime));
        outputView.printUpdateAttendance(beforeTime, beforeStatus, after);
    }

    private void printAttendanceByCrew(final Crews crews, final LocalDate now) {
        Crew crew = crews.findByName(inputView.inputNickname());
        outputView.printAttendanceByCrew(crew);

        Warning warning = crew.checkWarning();
        if (!warning.equals(Warning.NONE)) {
            outputView.printWarning(warning);
        }
    }

    private void printWarningCrews(final Crews crews) {
        outputView.printWarningCrews(crews.collectWarningCrews());
    }
}
