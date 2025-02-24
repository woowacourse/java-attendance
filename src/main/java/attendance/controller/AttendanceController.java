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
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;


public class AttendanceController {

    private final static String FILE_PATH = "src/main/resources/attendances.csv";
    private final static String CHECK_FUNCTION = "1";
    private final static String MODIFY_FUNCTION = "2";
    private final static String HISTORY_FUNCTION = "3";
    private final static String RISK_OF_EXPULSION_FUNCTION = "4";
    private final static String QUIT_FUNCTION = "Q";

    private final InputView inputView;
    private final OutputView outputView;

    private AttendanceBook attendanceBook;
    private AttendanceRepository attendanceRepository;
    private final Map<String, Supplier<Boolean>> functions;


    public AttendanceController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.functions = initializeFunctions();
    }

    public void start() {
        initAttendanceSystem();

        while (true) {
            String functionValue = functionInput(LocalDateTime.now());
            if (execute(functionValue)) {
                return;
            }
        }
    }

    private Map<String, Supplier<Boolean>> initializeFunctions() {
        return Map.of(
                CHECK_FUNCTION, () -> executeWithExceptionHandling(this::attendanceCheckFunction),
                MODIFY_FUNCTION, () -> executeWithExceptionHandling(this::attendanceModifyFunction),
                HISTORY_FUNCTION, () -> executeWithExceptionHandling(this::attendanceHistoryByNameFunction),
                RISK_OF_EXPULSION_FUNCTION, () -> executeWithExceptionHandling(this::crewAtRiskOfExpulsion),
                QUIT_FUNCTION, () -> true
        );
    }

    private boolean executeWithExceptionHandling(Supplier<Boolean> function) {
        try {
            return function.get();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return false;
        }
    }

    private void initAttendanceSystem() {
        AttendanceContentDTO attendanceRecordContent = AttendanceReader.getAttendanceRecordContent(
                FileReader.parseToFile(FILE_PATH));

        attendanceRepository = new AttendanceRepository(attendanceRecordContent.attendances());
        attendanceBook = new AttendanceBook(attendanceRecordContent.names());

        attendanceBook.initAbsent(attendanceRepository);
    }

    public boolean execute(final String functionValue) {
        return functions.getOrDefault(functionValue, this::handleInvalidFunction).get();
    }

    private boolean handleInvalidFunction() {
        outputView.printErrorMessage("[ERROR] 올바른 기능을 입력해주세요.");
        return false;
    }

    private String functionInput(final LocalDateTime today) {
        return inputView.inputFunction(today.getMonthValue(), today.getDayOfMonth(),
                today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));
    }

    public boolean attendanceCheckFunction() {

        String crewName = inputView.inputCrewName();
        attendanceBook.checkName(crewName);
        Time todayDateTime = createTime(LocalDate.now(), inputView.inputTime());

        Attendance attendance = new Attendance(crewName, todayDateTime);
        attendanceRepository.add(attendance);

        outputView.printAttendance(todayDateTime, attendance.getAttendanceStatus());

        return false;
    }

    private Time createTime(final LocalDate date, final String attendanceTime) {
        String[] split = attendanceTime.split(":");
        return new Time(date, split[0], split[1], false);
    }

    public boolean attendanceModifyFunction() {

        String crewName = inputView.inputModifyCrewName();
        attendanceBook.checkName(crewName);
        int modifyDay = inputView.inputModifyDay();
        String modifyTime = inputView.inputModifyTime();
        modifyAttendance(modifyDay, modifyTime, crewName);

        return false;
    }

    private void modifyAttendance(final int modifyDay, final String modifyTime, final String crewName) {
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

    public boolean attendanceHistoryByNameFunction() {

        String crewName = inputView.inputCrewName();

        attendanceBook.checkName(crewName);
        List<Attendance> attendances = attendanceRepository.findAllAttendanceByName(crewName,
                LocalDate.now().getMonthValue());

        outputView.printNameAndAttendances(crewName, attendances);

        outputView.printAcademicStatusResult(
                attendanceRepository.getAcademicStatusByName(crewName, LocalDate.now().getMonthValue()));

        return false;
    }

    public boolean crewAtRiskOfExpulsion() {
        outputView.printCrewsAtRiskOfExpulsionStartMessage();

        Stream.of(EXPELLED.getValue(), INTERVIEW.getValue(), WARNING.getValue())
                .map(value -> attendanceBook.getCrewAtRiskOfExpulsion(attendanceRepository, value,
                        LocalDate.now().getMonthValue()))
                .forEach(outputView::printCrewsAtRiskOfExpulsion);

        return false;
    }
}
