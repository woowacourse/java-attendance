package attendance.controller;

import static attendance.domain.AcademicStatus.EXPELLED;
import static attendance.domain.AcademicStatus.INTERVIEW;
import static attendance.domain.AcademicStatus.WARNING;

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

    public AttendanceController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {

        initAttendanceSystem();
        do {
            LocalDateTime today = LocalDateTime.now();
            String functionValue = functionInput(today);

            try {
                if (choiceFunction(functionValue)) {
                    return;
                }
                throw new IllegalArgumentException("[ERROR] 올바른 기능을 입력해주세요.");
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }

        } while (true);
    }

    private boolean choiceFunction(final String functionValue) {
        if (functionValue.equals("1")) {
            attendanceCheckFunction();
        }

        if (functionValue.equals("2")) {
            attendanceModifyFunction();
        }

        if (functionValue.equals("3")) {
            attendanceHistoryByName();
        }

        if (functionValue.equals("4")) {
            crewAtRiskOfExpulsion();
        }

        return functionValue.equals("Q");
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

    private void attendanceCheckFunction() {

        String crewName = inputView.inputCrewName();
        attendanceBook.checkName(inputView.inputCrewName());
        Time todayDateTime = createTime(LocalDate.now(), inputView.inputTime());

        Attendance attendance = new Attendance(crewName, todayDateTime);
        attendanceRepository.add(attendance);

        outputView.printAttendance(todayDateTime, attendance.getAttendanceStatus());
    }

    private Time createTime(final LocalDate date, final String attendanceTime) {
        String[] split = attendanceTime.split(":");
        return new Time(date, split[0], split[1], false);
    }

    private void attendanceModifyFunction() {

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

    private void attendanceHistoryByName() {

        String crewName = inputView.inputCrewName();

        attendanceBook.checkName(crewName);
        List<Attendance> attendances = attendanceRepository.findAllAttendanceByName(crewName);

        outputView.printNameAndAttendances(crewName, attendances);

        outputView.printAcademicStatusResult(attendanceRepository.getAcademicStatusByName(crewName));
    }

    private void crewAtRiskOfExpulsion() {

        outputView.printCrewsAtRiskOfExpulsionStartMessage();

        outputView.printCrewsAtRiskOfExpulsion(
                attendanceBook.getCrewAtRiskOfExpulsion(attendanceRepository, EXPELLED.getValue()));
        outputView.printCrewsAtRiskOfExpulsion(
                attendanceBook.getCrewAtRiskOfExpulsion(attendanceRepository, INTERVIEW.getValue()));
        outputView.printCrewsAtRiskOfExpulsion(
                attendanceBook.getCrewAtRiskOfExpulsion(attendanceRepository, WARNING.getValue()));
    }

}
