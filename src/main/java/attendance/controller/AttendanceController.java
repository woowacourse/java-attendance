package attendance.controller;

import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.dto.AttendanceContentDTO;
import attendance.repository.AttendanceRepository;
import attendance.utils.AttendanceChecker;
import attendance.utils.AttendanceReader;
import attendance.utils.FileReader;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;

    private AttendanceBook attendanceBook;
    private AttendanceRepository attendanceRepository;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {

        initAttendanceSystem();

        LocalDateTime today = LocalDateTime.now();
        int functionValue = inputView.inputFunction(today.getMonthValue(), today.getDayOfMonth(),
                today.getDayOfWeek().getDisplayName(
                        TextStyle.FULL, Locale.KOREAN));

        if (functionValue == 1) {
            attendanceCheckFunction();
        }
    }

    private void initAttendanceSystem() {
        AttendanceContentDTO attendanceRecordContent = AttendanceReader.getAttendanceRecordContent(
                FileReader.parseToFile("src/main/resources/attendances.csv"));

        attendanceRepository = new AttendanceRepository(attendanceRecordContent.attendances());
        attendanceBook = new AttendanceBook(attendanceRecordContent.names());
    }

    private void attendanceCheckFunction() {

        String crewName = inputView.inputCrewName();
        attendanceBook.checkName(crewName);
        String attendanceTime = inputView.inputTime();
        LocalDateTime todayDateTime = createTodayTime(attendanceTime);
        attendanceRepository.add(new Attendance(crewName, todayDateTime));

        outputView.printAttendance(todayDateTime, AttendanceChecker.check(todayDateTime));
    }

    private LocalDateTime createTodayTime(String attendanceTime) {
        String[] split = attendanceTime.split(":");
        LocalDate todayDate = LocalDateTime.now().toLocalDate();
        return todayDate.atTime(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

}
