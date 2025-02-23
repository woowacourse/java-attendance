package attendance.controller;

import attendance.dto.AttendanceDto;
import attendance.dto.AttendanceDto.AttendanceDetailDto;
import attendance.dto.WarningCrewsDto;
import attendance.model.AttendanceDetail;
import attendance.model.AttendanceReport;
import attendance.model.Crew;
import attendance.model.Crews;
import attendance.model.CustomClock;
import attendance.model.WoowaDate;
import attendance.util.DateFormatUtil;
import attendance.util.DateUtil;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class Controller {
    private final static LocalDate trainingStartDate = LocalDate.of(2024, 12, 1);

    private final InputView inputView;
    private final OutputView outputView;
    private final CustomClock clock;
    private final Crews crews;

    public Controller(InputView inputView, OutputView outputView, CustomClock clock, Crews crews) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.clock = clock;
        this.crews = crews;
    }

    public void run() {

        Map<String, Runnable> commands = Map.of("1", this::processAddAttendance, "2", this::processModifyAttendance,
                "3", this::processDisplayAttendanceHistory, "4", this::processDisplayWarningCrew, "Q",
                () -> System.exit(0));

        Runnable action = commands.getOrDefault(inputView.inputCommand(), this::run);
        action.run();

        run();
    }

    private void processAddAttendance() {
        process(() -> {
            WoowaDate woowaDate = new WoowaDate(clock.now().toLocalDate(), clock);
            Crew crew = crews.findCrew(inputView.inputCrewName());
            LocalTime entryTime = DateFormatUtil.parseTime(inputView.inputEntryTime());

            AttendanceDetail attendanceDetail = new AttendanceDetail(woowaDate, entryTime);
            crew.attend(attendanceDetail);

            outputView.printAttendanceDetail(AttendanceDetailDto.from(attendanceDetail));
        });
    }

    private void processModifyAttendance() {
        process(() -> {
            Crew crew = crews.findCrew(inputView.inputModifyAttendanceCrewName());
            WoowaDate modifyDate = parseModifyDate();

            modifyAttendanceAndDisplayResult(crew, modifyDate);
        });
    }

    private WoowaDate parseModifyDate() {
        return new WoowaDate(DateUtil.parseDate(inputView.inputModifyAttendanceDate()), clock);
    }

    private void modifyAttendanceAndDisplayResult(Crew crew, WoowaDate modifyDate) {
        AttendanceDetail attendanceDetail = crew.findAttendanceDetail(modifyDate.getLocalDate());
        AttendanceDetail beforeModify = copyAttendanceDetail(attendanceDetail);

        attendanceDetail.modify(DateFormatUtil.parseTime(inputView.inputModifyAttendanceTime()));

        outputView.printModifyResult(AttendanceDetailDto.from(beforeModify),
                AttendanceDetailDto.from(attendanceDetail));
    }

    private AttendanceDetail copyAttendanceDetail(AttendanceDetail attendanceDetail) {
        return new AttendanceDetail(attendanceDetail.getAttendanceDate(), attendanceDetail.getAttendanceTime());
    }

    private void processDisplayAttendanceHistory() {
        process(() -> {
            Crew crew = crews.findCrew(inputView.inputCrewName());

            AttendanceReport attendanceReport = new AttendanceReport(clock, crew.getAttendanceHistory(),
                    trainingStartDate);

            LocalDate trainingStartDate = LocalDate.of(clock.nowDate().getYear(), clock.nowDate().getMonthValue(), 1);
            outputView.printAttendanceHistory(
                    AttendanceDto.from(crew, attendanceReport), trainingStartDate, clock
            );
        });
    }

    private void processDisplayWarningCrew() {
        process(() -> outputView.printWarningCrews(WarningCrewsDto.from(crews, clock, trainingStartDate)));
    }

    private void process(Runnable runnable) {
        try {
            runnable.run();
        } catch (IllegalArgumentException exception) {
            outputView.printError(exception.getMessage());
        }
    }

}
