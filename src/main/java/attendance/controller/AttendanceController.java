package attendance.controller;

import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.dto.AttendanceContentDTO;
import attendance.repository.AttendanceRepository;
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

        if (functionValue == 2) {
            attendanceModifyFunction();
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
        LocalDateTime todayDateTime = createTime(LocalDate.now(), attendanceTime);
        Attendance attendance = new Attendance(crewName, todayDateTime);
        attendanceRepository.add(attendance);

        outputView.printAttendance(todayDateTime, attendance.getAttendanceStatus());
    }

    private LocalDateTime createTime(LocalDate date, String attendanceTime) {
        String[] split = attendanceTime.split(":");
        return date.atTime(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    private void attendanceModifyFunction() {

        String crewName = inputView.inputModifyCrewName();
        attendanceBook.checkName(crewName);
        int modifyDay = inputView.inputModifyDay();
        String modifyTime = inputView.inputModifyTime();

        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        LocalDateTime modifyDateTime = createTime(LocalDate.of(year, month, modifyDay), modifyTime);

        Attendance attendance = attendanceRepository.findAttendanceByNameAndDateTime(crewName,
                modifyDay);

        LocalDateTime previousDateTime = attendance.getAttendanceTime();
        String previousAttendanceStatus = attendance.getAttendanceStatus();

        outputView.printModifyAttendanceResult(previousDateTime, previousAttendanceStatus, modifyDateTime,
                attendance.getAttendanceStatus());
    }

}
