package attendance.controller;

import attendance.controller.util.AttendancesFileReader;
import attendance.controller.util.CrewAttendanceParser;
import attendance.controller.util.TimeFormatter;
import attendance.controller.validator.HolidayValidator;
import attendance.controller.validator.OperatingHoursValidator;
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
    private final LocalDate today;

    public AttendanceController(LocalDate today) {
        this.crewsService = new CrewsService();
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.today = today;
    }

    public void run() {
        Crews crews = crewsService.init(CrewAttendanceParser.parseCrewAttendances(AttendancesFileReader.read()), today);
        while (true) {
            try {
                Menu selectedMenu = inputView.inputMenu(today);
                if (selectMenu(selectedMenu, crews)) break;
            } catch (IllegalArgumentException e) {
                outputView.printExceptionMessage(e);
            }
        }
    }

    private boolean selectMenu(Menu selectedMenu, Crews crews) {
        if (selectedMenu.equals(Menu.CHECK_ATTEND)) confirmAttendance(crews);
        if (selectedMenu.equals(Menu.UPDATE_ATTEND)) updateAttendance(crews);
        if (selectedMenu.equals(Menu.PRINT_ATTEND_BY_CREW)) printAttendanceByCrew(crews);
        if (selectedMenu.equals(Menu.PRINT_WARNING)) printWarningCrews(crews);

        return selectedMenu.equals(Menu.QUIT);
    }

    private void confirmAttendance(final Crews crews) {
        HolidayValidator.validate(today);

        Crew crew = crews.findByName(inputView.inputNickname());
        crew.existInAttendances(today);

        LocalDateTime attendDateTime = TimeFormatter.format(today, inputView.inputAttendTime());
        OperatingHoursValidator.validate(attendDateTime);
        Attendance attendance = new Attendance(attendDateTime);

        crew.addAttendance(attendance);
        outputView.printAttendanceResult(attendance);
    }

    private void updateAttendance(final Crews crews) {
        Crew crew = crews.findByName(inputView.inputNickname());

        LocalDate updateDate = LocalDate.of(today.getYear(), today.getMonthValue(), inputView.inputUpdateDate(today));
        HolidayValidator.validate(updateDate);

        String inputUpdateTime = inputView.inputUpdateTime();
        LocalDateTime updateTime = TimeFormatter.format(updateDate, inputUpdateTime);
        OperatingHoursValidator.validate(updateTime);

        Attendance before = crew.findAttendanceByDate(updateDate);
        // TODO : dto로 추출
        AttendanceStatus beforeStatus = before.getStatus();
        LocalDateTime beforeTime = before.getDateTime();

        Attendance after = crew.updateAttendance(updateTime);
        outputView.printUpdateAttendance(beforeTime, beforeStatus, after);
    }

    private void printAttendanceByCrew(final Crews crews) {
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
