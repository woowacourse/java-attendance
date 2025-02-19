package attendance.controller;

import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.domain.Time;
import attendance.dto.AttendanceContentDTO;
import attendance.repository.AttendanceRepository;
import attendance.utils.AttendanceReader;
import attendance.utils.FileReader;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
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

        if (functionValue == 3) {
            attendanceHistoryByName();
        }
    }


    private void initAttendanceSystem() {
        AttendanceContentDTO attendanceRecordContent = AttendanceReader.getAttendanceRecordContent(
                FileReader.parseToFile("src/main/resources/attendances.csv"));

        attendanceRepository = new AttendanceRepository(attendanceRecordContent.attendances());
        attendanceBook = new AttendanceBook(attendanceRecordContent.names());

        attendanceBook.initAbsent(attendanceRepository);
    }

    private void attendanceCheckFunction() {

        String crewName = inputView.inputCrewName();
        attendanceBook.checkName(crewName);
        String attendanceTime = inputView.inputTime();
        Time todayDateTime = createTime(LocalDate.now(), attendanceTime);

        Attendance attendance = new Attendance(crewName, todayDateTime);
        attendanceRepository.add(attendance);

        outputView.printAttendance(todayDateTime, attendance.getAttendanceStatus());
    }

    private Time createTime(LocalDate date, String attendanceTime) {
        String[] split = attendanceTime.split(":");
        return new Time(date, split[0], split[1], false);
    }

    private void attendanceModifyFunction() {

        String crewName = inputView.inputModifyCrewName();
        attendanceBook.checkName(crewName);
        int modifyDay = inputView.inputModifyDay();
        String modifyTime = inputView.inputModifyTime();

        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        Time modifyDateTime = createTime(LocalDate.of(year, month, modifyDay), modifyTime);

        Attendance attendance = attendanceRepository.findAttendanceByNameAndLocalDate(crewName, year, month, modifyDay);

        Time previousDateTime = attendance.getAttendanceTime();
        String previousAttendanceStatus = attendance.getAttendanceStatus();

        outputView.printModifyAttendanceResult(previousDateTime, previousAttendanceStatus, modifyDateTime,
                attendance.getAttendanceStatus());
    }

    private void attendanceHistoryByName() {

        String crewName = inputView.inputCrewName();

        List<Attendance> attendances = attendanceRepository.findAllAttendanceByName(crewName);

        outputView.printNameAndAttendances(crewName, attendances);
    }

}
