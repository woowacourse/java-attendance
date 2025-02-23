package attendance.controller;

import static attendance.domain.AcademicStatus.EXPELLED;
import static attendance.domain.AcademicStatus.INTERVIEW;
import static attendance.domain.AcademicStatus.WARNING;

import attendance.domain.AcademicStatus;
import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceTime;
import attendance.domain.CrewAttendanceInformation;
import attendance.domain.Function;
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
import java.util.function.Supplier;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;

    private AttendanceBook attendanceBook;
    private AttendanceRepository attendanceRepository;

    private static final String attendanceFilePath = "src/main/resources/attendances.csv";

    public AttendanceController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {

        initAttendanceSystem();
        attendanceSystemStart();
    }

    private void initAttendanceSystem() {

        AttendanceContentDTO attendanceRecordContent = AttendanceReader.getAttendanceRecordContent(
                FileReader.parseToFile(attendanceFilePath));

        attendanceRepository = new AttendanceRepository(attendanceRecordContent.attendances());
        attendanceBook = new AttendanceBook(attendanceRecordContent.names());
        attendanceBook.initAbsent(attendanceRepository);
    }

    private void attendanceSystemStart() {

        Function function = functionInput();
        if (isQuit(function)) {
            return;
        }
        runFunction(function);
        attendanceSystemStart();
    }

    private Function functionInput() {

        return retryInput(() -> {
            LocalDateTime today = LocalDateTime.now();
            String functionValue = inputView.inputFunction(today.getMonthValue(), today.getDayOfMonth(),
                    today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));
            return Function.getFunction(functionValue);
        });
    }

    private boolean isQuit(final Function function) {

        return function == Function.QUIT;
    }

    private void runFunction(final Function function) {

        if (function == Function.ATTEND) {
            attendanceCheckFunction();
        }
        if (function == Function.MODIFY_ATTENDANCE) {
            attendanceModifyFunction();
        }
        if (function == Function.GET_ATTENDANCES) {
            attendanceHistoryByName();
        }
        if (function == Function.GET_CREWS_AT_RISK_OF_EXPULSION) {
            crewAtRiskOfExpulsion();
        }
    }

    private void attendanceCheckFunction() {

        if (isNotAttendanceDate()) {
            return;
        }

        String crewName = inputCrewName();
        AttendanceTime todayAttendanceTime = inputAttendedTime();

        Attendance attendance = new Attendance(crewName, todayAttendanceTime);
        attendanceRepository.add(attendance);

        outputView.printAttendance(todayAttendanceTime, attendance.getAttendanceStatus());
    }

    private boolean isNotAttendanceDate() {

        try {
            AttendanceTime.validateAttendanceDate(LocalDate.now());
            return false;
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return true;
        }
    }

    private String inputCrewName() {

        return retryInput(() -> {
            String crewName = inputView.inputCrewName();
            attendanceBook.validateCrewName(crewName);
            return crewName;
        });
    }

    private AttendanceTime inputAttendedTime() {

        return retryInput(() -> {
            String attendedTime = inputView.inputTime();
            return createAttendanceTime(LocalDate.now(), attendedTime);
        });
    }

    private AttendanceTime createAttendanceTime(final LocalDate date, final String attendanceTime) {

        String[] split = attendanceTime.split(":");
        return new AttendanceTime(date, split[0], split[1], false);
    }

    private void attendanceModifyFunction() {

        String crewName = inputModifyCrewName();
        AttendanceTime modifiedAttendanceTime = inputModifyAttendedTime();
        modifyAttendance(crewName, modifiedAttendanceTime);
    }

    private String inputModifyCrewName() {

        return retryInput(() -> {
            String crewName = inputView.inputModifyCrewName();
            attendanceBook.validateCrewName(crewName);
            return crewName;
        });
    }

    private AttendanceTime inputModifyAttendedTime() {

        return retryInput(() -> {
            int modifyDay = inputView.inputModifyDay();
            String modifyTime = inputView.inputModifyTime();
            return createAttendanceTime(
                    LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), modifyDay),
                    modifyTime
            );
        });
    }

    private void modifyAttendance(final String crewName, final AttendanceTime modifiedAttendanceTime) {

        Attendance attendance = getTargetDateAttendance(crewName, modifiedAttendanceTime);

        AttendanceTime previousDateAttendanceTime = attendance.getAttendanceTime();
        String previousAttendanceStatus = attendance.getAttendanceStatus();

        attendance.modifyAttendanceTime(modifiedAttendanceTime);
        outputView.printModifyAttendanceResult(previousDateAttendanceTime, previousAttendanceStatus,
                modifiedAttendanceTime,
                attendance.getAttendanceStatus());
    }

    private Attendance getTargetDateAttendance(String crewName, AttendanceTime modifiedAttendanceTime) {

        int year = modifiedAttendanceTime.getYear();
        int month = modifiedAttendanceTime.getMonth();
        int modifyDay = modifiedAttendanceTime.getDay();
        return attendanceRepository.findAttendanceByNameAndLocalDate(crewName, year, month, modifyDay);
    }

    private void attendanceHistoryByName() {

        String crewName = inputCrewName();
        List<Attendance> attendances = attendanceRepository.findAllAttendanceByName(crewName);

        outputView.printNameAndAttendances(crewName, attendances);
        CrewAttendanceInformation academicStatusByName = attendanceRepository.getAcademicStatusByName(crewName);
        outputView.printAcademicStatusResult(academicStatusByName);
    }

    private void crewAtRiskOfExpulsion() {

        outputView.printCrewsAtRiskOfExpulsionStartMessage();

        printTargetCrews(EXPELLED);
        printTargetCrews(INTERVIEW);
        printTargetCrews(WARNING);
        outputView.printNewLine();
    }

    private void printTargetCrews(AcademicStatus status) {

        List<CrewAttendanceInformation> targetCrews = attendanceBook.getCrewAtRiskOfExpulsion(
                attendanceRepository, status.getValue());
        outputView.printCrewsAtRiskOfExpulsion(targetCrews);
    }

    private <T> T retryInput(Supplier<T> supplier) {

        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return retryInput(supplier);
        }
    }
}
