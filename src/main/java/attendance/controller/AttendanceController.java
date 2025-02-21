package attendance.controller;

import attendance.controller.util.AttendancesFileReader;
import attendance.controller.util.CrewAttendanceParser;
import attendance.controller.util.TimeFormatter;
import attendance.controller.validator.HolidayValidator;
import attendance.controller.validator.OperatingHoursValidator;
import attendance.domain.Attendance;
import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.domain.Menu;
import attendance.domain.Warning;
import attendance.dto.AttendanceResultResponse;
import attendance.dto.CrewAttendanceResponse;
import attendance.dto.UpdateAfterAttendanceResponse;
import attendance.dto.UpdateBeforeAttendanceResponse;
import attendance.dto.WarningCrewResponse;
import attendance.service.CrewsService;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
        while (!processMenu(today, crews)) {
        }
    }

    private boolean processMenu(LocalDate today, Crews crews) {
        try {
            Menu selectedMenu = inputView.inputMenu(today);
            return executeMenu(selectedMenu, crews);
        } catch (IllegalArgumentException e) {
            outputView.printExceptionMessage(e);
            return false;
        }
    }

    private boolean executeMenu(Menu selectedMenu, Crews crews) {
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
        Attendance attendance = Attendance.from(attendDateTime);

        crew.addAttendance(attendance);
        outputView.printAttendanceResult(AttendanceResultResponse.from(attendance));
    }

    private void updateAttendance(final Crews crews) {
        Crew crew = crews.findByName(inputView.inputNickname());
        LocalDate updateDate = LocalDate.of(today.getYear(), today.getMonthValue(),
                inputView.inputUpdateDate(today));
        HolidayValidator.validate(updateDate);

        LocalDateTime updateTime = TimeFormatter.format(updateDate, inputView.inputUpdateTime());
        OperatingHoursValidator.validate(updateTime);

        UpdateBeforeAttendanceResponse beforeResponse = UpdateBeforeAttendanceResponse.of(
                crew.findAttendanceByDate(updateDate));
        UpdateAfterAttendanceResponse afterResponse = UpdateAfterAttendanceResponse.of(
                crew.updateAttendance(updateTime));

        outputView.printUpdateAttendance(beforeResponse, afterResponse);
    }

    private void printAttendanceByCrew(final Crews crews) {
        Crew crew = crews.findByName(inputView.inputNickname());

        CrewAttendanceResponse response = CrewAttendanceResponse.from(crew);
        outputView.printAttendanceByCrew(response);

        Warning warning = crew.checkWarning();
        if (!warning.equals(Warning.NONE)) {
            outputView.printWarning(warning);
        }
    }

    private void printWarningCrews(final Crews crews) {
        List<Crew> warningCrews = crews.collectWarningCrews();
        List<WarningCrewResponse> responses = warningCrews.stream()
                .map(WarningCrewResponse::from)
                .toList();
        outputView.printWarningCrews(responses);
    }
}
