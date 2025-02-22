package attendance.controller;

import static attendance.domain.AcademicStatus.EXPELLED;
import static attendance.domain.AcademicStatus.INTERVIEW;
import static attendance.domain.AcademicStatus.WARNING;

import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceFunctionExecutor;
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
import java.util.stream.Stream;


public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;

    private AttendanceBook attendanceBook;
    private AttendanceRepository attendanceRepository;

    public AttendanceController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        initAttendanceSystem();
        AttendanceFunctionExecutor executor = new AttendanceFunctionExecutor(this);

        while (true) {
            String functionValue = functionInput(LocalDateTime.now());
            if (executor.execute(functionValue)) {
                return;
            }
        }
    }

    private String functionInput(final LocalDateTime today) {
        return inputView.inputFunction(today.getMonthValue(), today.getDayOfMonth(),
                today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));
    }

    private void initAttendanceSystem() {
        AttendanceContentDTO attendanceRecordContent = AttendanceReader.getAttendanceRecordContent(
                FileReader.parseToFile("src/main/resources/attendances.csv"));

        attendanceRepository = new AttendanceRepository(attendanceRecordContent.attendances());
        attendanceBook = new AttendanceBook(attendanceRecordContent.names());

        attendanceBook.initAbsent(attendanceRepository);
    }

    public void attendanceCheckFunction() {

        String crewName = inputView.inputCrewName();
        attendanceBook.checkName(crewName);
        Time todayDateTime = createTime(LocalDate.now(), inputView.inputTime());

        Attendance attendance = new Attendance(crewName, todayDateTime);
        attendanceRepository.add(attendance);

        outputView.printAttendance(todayDateTime, attendance.getAttendanceStatus());
    }

    private Time createTime(final LocalDate date, final String attendanceTime) {
        String[] split = attendanceTime.split(":");
        return new Time(date, split[0], split[1], false);
    }

    public void attendanceModifyFunction() {

        String crewName = inputView.inputModifyCrewName();
        attendanceBook.checkName(crewName);
        int modifyDay = inputView.inputModifyDay();
        String modifyTime = inputView.inputModifyTime();
        modifyAttendance(modifyDay, modifyTime, crewName);
    }

    private void modifyAttendance(int modifyDay, final String modifyTime, final String crewName) {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        Time modifyDateTime = createTime(LocalDate.of(year, month, modifyDay), modifyTime);

        Attendance attendance = attendanceRepository.findAttendanceByNameAndLocalDate(crewName, year, month, modifyDay);

        Time previousDateTime = attendance.getAttendanceTime();
        String previousAttendanceStatus = attendance.getAttendanceStatus();

        attendance.modifyAttendanceTime(modifyDateTime);

        outputView.printModifyAttendanceResult(previousDateTime, previousAttendanceStatus, modifyDateTime,
                attendance.getAttendanceStatus());
    }

    public void attendanceHistoryByNameFunction() {

        String crewName = inputView.inputCrewName();

        attendanceBook.checkName(crewName);
        List<Attendance> attendances = attendanceRepository.findAllAttendanceByName(crewName);

        outputView.printNameAndAttendances(crewName, attendances);

        outputView.printAcademicStatusResult(attendanceRepository.getAcademicStatusByName(crewName));
    }

    public void crewAtRiskOfExpulsion() {
        outputView.printCrewsAtRiskOfExpulsionStartMessage();

        Stream.of(EXPELLED.getValue(), INTERVIEW.getValue(), WARNING.getValue())
                .map(value -> attendanceBook.getCrewAtRiskOfExpulsion(attendanceRepository, value))
                .forEach(outputView::printCrewsAtRiskOfExpulsion);
    }

    public void printErrorMessage(String message) {
        outputView.printErrorMessage(message);
    }

}
