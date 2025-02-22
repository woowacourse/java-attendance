package attendance.controller;

import attendance.dto.AttendanceDto;
import attendance.dto.AttendanceDto.AttendanceDetailDto;
import attendance.dto.WarningCrewsDto;
import attendance.model.AttendanceDetail;
import attendance.model.Crew;
import attendance.model.Crews;
import attendance.model.FixedCustomClock;
import attendance.model.WoowaDate;
import attendance.util.DateFormatUtil;
import attendance.util.DateUtil;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalTime;
import java.util.Map;

public class Controller {

    private final InputView inputView;
    private final OutputView outputView;
    private final FixedCustomClock fixedCustomClock;
    private final Crews crews;

    public Controller(InputView inputView, OutputView outputView, FixedCustomClock fixedCustomClock, Crews crews) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.fixedCustomClock = fixedCustomClock;
        this.crews = crews;
    }

    public void run() {

        Map<String, Runnable> commands = Map.of(
                "1", this::processAddAttendance,
                "2", this::processModifyAttendance,
                "3", this::processDisplayAttendanceHistory,
                "4", this::processDisplayWarningCrew,
                "Q", () -> System.exit(0)
        );

        Runnable action = commands.getOrDefault(inputView.inputCommand(), this::run);
        action.run();

        run();
    }

    private void processAddAttendance() {
        process(() -> {
            WoowaDate woowaDate = new WoowaDate(fixedCustomClock.now().toLocalDate(), fixedCustomClock);
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
        return new WoowaDate(
                DateUtil.parseDate(inputView.inputModifyAttendanceDate()),
                fixedCustomClock
        );
    }

    private void modifyAttendanceAndDisplayResult(Crew crew, WoowaDate modifyDate) {
        AttendanceDetail attendanceDetail = crew.findAttendanceDetail(modifyDate.getLocalDate());
        AttendanceDetail beforeModify = copyAttendanceDetail(attendanceDetail);

        attendanceDetail.modify(DateFormatUtil.parseTime(inputView.inputModifyAttendanceTime()));

        outputView.printModifyResult(
                AttendanceDetailDto.from(beforeModify),
                AttendanceDetailDto.from(attendanceDetail)
        );
    }

    private AttendanceDetail copyAttendanceDetail(AttendanceDetail attendanceDetail) {
        return new AttendanceDetail(
                attendanceDetail.getAttendanceDate(),
                attendanceDetail.getAttendanceTime()
        );
    }

    private void processDisplayAttendanceHistory() {
        process(() -> outputView.printAttendanceHistory(
                AttendanceDto.from(crews.findCrew(inputView.inputCrewName())))
        );
    }

    private void processDisplayWarningCrew() {
        process(() -> outputView.printWarningCrews(WarningCrewsDto.from(crews)));
    }

    private void process(Runnable runnable) {
        try {
            runnable.run();
        } catch (IllegalArgumentException exception) {
            outputView.printError(exception.getMessage());
        }
    }

}
