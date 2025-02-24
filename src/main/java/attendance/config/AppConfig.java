package attendance.config;

import attendance.domain.AttendanceInit;
import attendance.domain.AttendanceManager;
import attendance.domain.Holiday;
import attendance.utility.CurrentDateGeneratorImpl;
import attendance.utility.DateGenerator;
import attendance.view.InputView;
import attendance.view.OutputView;

public class AppConfig {

    private final InputView inputView;
    private final OutputView outputView;
    private final Holiday holiday;
    private final DateGenerator dateGenerator;
    private final AttendanceInit attendanceInit;
    private final AttendanceManager attendanceManager;

    public AppConfig() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.holiday = new Holiday();
        this.dateGenerator = new CurrentDateGeneratorImpl();
        this.attendanceManager = new AttendanceManager();
        this.attendanceInit = new AttendanceInit(attendanceManager, holiday, dateGenerator);
    }

    public InputView getInputView() {
        return inputView;
    }

    public OutputView getOutputView() {
        return outputView;
    }

    public DateGenerator getDateGenerator() {
        return dateGenerator;
    }

    public Holiday getHoliday() {
        return holiday;
    }

    public AttendanceManager getAttendanceManager() {
        return attendanceManager;
    }

    public AttendanceInit getAttendanceInit() {
        return attendanceInit;
    }
}
