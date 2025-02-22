package attendance.config;

import attendance.domain.AttendanceManager;
import attendance.domain.HolidayChecker;
import attendance.service.AttendanceInitService;
import attendance.service.AttendanceService;
import attendance.utility.CurrentDateGeneratorImpl;
import attendance.utility.DateGenerator;
import attendance.view.InputView;
import attendance.view.OutputView;

public class AppConfig {

    private final InputView inputView;
    private final OutputView outputView;
    private final HolidayChecker holidayChecker;
    private final DateGenerator dateGenerator;
    private final AttendanceManager attendanceManager;
    private final AttendanceInitService attendanceInitService;
    private final AttendanceService attendanceService;

    public AppConfig() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.holidayChecker = new HolidayChecker();
        this.dateGenerator = new CurrentDateGeneratorImpl();
        this.attendanceManager = new AttendanceManager(holidayChecker, dateGenerator);
        this.attendanceInitService = new AttendanceInitService(attendanceManager);
        this.attendanceService = new AttendanceService(inputView, attendanceManager);
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

    public AttendanceInitService getAttendanceInitService() {
        return attendanceInitService;
    }

    public AttendanceService getAttendanceService() {
        return attendanceService;
    }
}
