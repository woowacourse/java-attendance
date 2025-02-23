package attendance.controller;

import attendance.controller.util.AttendancesFileReader;
import attendance.controller.util.CrewAttendanceParser;
import attendance.controller.util.TimeFormatter;
import attendance.controller.validator.HolidayValidator;
import attendance.controller.validator.OperatingHoursValidator;
import attendance.domain.*;
import attendance.dto.AttendanceResultResponse;
import attendance.dto.UpdateAfterAttendanceResponse;
import attendance.dto.UpdateBeforeAttendanceResponse;
import attendance.view.InputView;
import attendance.view.OutputView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;
    private final LocalDate today;

    private final Map<Menu, Consumer<Crews>> menuProcesses =
            new HashMap<>() {{
                put(Menu.CHECK_ATTEND, crews -> checkAttendance(crews));
                put(Menu.UPDATE_ATTEND, crews -> updateAttendance(crews));
                put(Menu.PRINT_ATTEND_BY_CREW, crews -> printAttendanceByCrew(crews));
                put(Menu.PRINT_WARNING, crews -> printWarningCrews(crews));
            }};

    public AttendanceController(LocalDate today) {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.today = today;
    }

    public void run() {
        Crews crews = Crews.init(CrewAttendanceParser.parseCrewAttendances(AttendancesFileReader.read()), today);
        while (true) {
            String selectedMenuInput = inputView.inputMenu(today);
            if (Menu.isQuit(selectedMenuInput)) break;
            try {
                runMenu(selectedMenuInput, crews);
            } catch (IllegalArgumentException e) {
                outputView.printExceptionMessage(e);
            }
        }
    }

    public void runMenu(String selectedMenuInput, Crews crews) {
        Menu selectedMenu = Menu.of(selectedMenuInput);
        menuProcesses.get(selectedMenu).accept(crews);
    }

    private void checkAttendance(final Crews crews) {
        HolidayValidator.validate(today);

        Crew crew = crews.findByName(inputView.inputNickname());
        crew.existInAttendances(today);

        LocalDateTime attendDateTime = TimeFormatter.format(today, inputView.inputAttendTime());
        OperatingHoursValidator.validate(attendDateTime);
        Attendance attendance = new Attendance(attendDateTime);

        crew.addAttendance(attendance);
        outputView.printAttendanceResult(AttendanceResultResponse.from(attendance));
    }

    private void updateAttendance(final Crews crews) {
        Crew crew = crews.findByName(inputView.inputNickname());
        LocalDate updateDate = LocalDate.of(today.getYear(), today.getMonthValue(), inputView.inputUpdateDate(today));
        HolidayValidator.validate(updateDate);

        LocalDateTime updateTime = TimeFormatter.format(updateDate, inputView.inputUpdateTime());
        OperatingHoursValidator.validate(updateTime);

        UpdateBeforeAttendanceResponse beforeResponse = UpdateBeforeAttendanceResponse.from(crew.findAttendanceByDate(updateDate));
        UpdateAfterAttendanceResponse afterResponse = UpdateAfterAttendanceResponse.from(crew.updateAttendance(updateTime));

        outputView.printUpdateAttendance(beforeResponse, afterResponse);
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
