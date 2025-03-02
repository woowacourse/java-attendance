package attendance.controller;

import attendance.domain.AcademicStatus;
import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceStatus;
import attendance.domain.Attendances;
import attendance.domain.Time;
import attendance.utils.AttendanceReader;
import attendance.utils.FileReader;
import attendance.utils.Parser;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AttendanceController {

    private final static String FILE_PATH = "src/main/resources/attendances.csv";
    private final static String CHECK_FUNCTION = "1";
    private final static String MODIFY_FUNCTION = "2";
    private final static String HISTORY_FUNCTION = "3";
    private final static String RISK_OF_EXPULSION_FUNCTION = "4";
    private final static String QUIT_FUNCTION = "Q";
    private final static String TIME_REGEX = "^([01]\\d|2[0-3]):[0-5]\\d$";

    private final InputView inputView;
    private final OutputView outputView;
    private AttendanceBook attendanceBook;
    private final Map<String, Supplier<Boolean>> functions;

    private final int inputYear = LocalDate.now().getYear();
    private final int inputMonth = 2;
    private final int inputDay = LocalDate.now().getDayOfMonth();


    public AttendanceController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.functions = initializeFunctions();
    }

    private Map<String, Supplier<Boolean>> initializeFunctions() {
        return Map.of(
                CHECK_FUNCTION, () -> executeWithExceptionHandling(this::attendanceAddFunction),
                MODIFY_FUNCTION, () -> executeWithExceptionHandling(this::attendanceModifyFunction),
                HISTORY_FUNCTION, () -> executeWithExceptionHandling(this::attendanceHistoryByNameFunction),
                RISK_OF_EXPULSION_FUNCTION, () -> executeWithExceptionHandling(this::crewAtRiskOfExpulsion),
                QUIT_FUNCTION, () -> true
        );
    }

    private boolean executeWithExceptionHandling(final Supplier<Boolean> function) {
        try {
            return function.get();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return false;
        }
    }

    public void run() {

        initAttendanceBook();

        while (true) {
            String functionValue = inputView.inputAttendanceFunction(LocalDateTime.now().getMonthValue(),
                    LocalDateTime.now().getDayOfMonth(),
                    LocalDateTime.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));

            if (execute(functionValue)) {
                return;
            }
        }
    }

    private void initAttendanceBook() {

        Set<Attendance> attendances = AttendanceReader.getAttendancesOnFile(FileReader.parseToFile(FILE_PATH));
        Set<String> crewNames = AttendanceReader.getCrewNamesOnFile(FileReader.parseToFile(FILE_PATH));

        attendanceBook = new AttendanceBook(crewNames, new Attendances(attendances));
    }

    public boolean execute(final String functionValue) {
        return functions.getOrDefault(functionValue, this::handleInvalidFunction).get();
    }

    private boolean handleInvalidFunction() {
        outputView.printErrorMessage("[ERROR] 올바른 기능을 입력해주세요.");
        return false;
    }

    public boolean attendanceAddFunction() {

        String crewName = inputView.inputCrewName();
        attendanceBook.hasCrew(crewName);
        Time todayDateTime = createTime(LocalDate.now(), inputView.inputAttendTime());

        Attendance attendance = new Attendance(crewName, todayDateTime);
        attendanceBook.addAttendance(attendance);

        outputView.printAttendance(todayDateTime, attendance.checkStatus().getValue());

        return false;
    }

    public boolean attendanceModifyFunction() {

        String crewName = inputView.inputModifyCrewName();
        attendanceBook.hasCrew(crewName);
        int modifyDay = Parser.parseToInt(inputView.inputModifyDay());
        String modifyTime = inputView.inputModifyAttendTime();
        modifyAttendance(modifyDay, modifyTime, crewName);

        return false;
    }

    private void modifyAttendance(final int modifyDay, final String modifyTime, final String crewName) {
        int year = inputYear;
        int month = inputMonth;
        Time modifyDateTime = createTime(createLocalDate(year, month, modifyDay), modifyTime);

        Attendance attendance = attendanceBook.findAttendanceByCrewNameAndLocalDate(crewName,
                LocalDate.of(year, month, modifyDay));

        Time previousDateTime = attendance.getAttendanceTime();
        String previousAttendanceStatus = attendance.checkStatus().getValue();

        attendance.modifyAttendanceTime(createLocalTime(modifyTime));

        outputView.printModifyAttendanceResult(previousDateTime, previousAttendanceStatus, modifyDateTime,
                attendance.checkStatus().getValue());
    }

    public boolean attendanceHistoryByNameFunction() {

        String crewName = inputView.inputCrewName();

        attendanceBook.hasCrew(crewName);

        Map<LocalDate, Attendance> monthlyAttendances = attendanceBook.findAttendancesByCrewNameAndYearAndMonth(
                crewName, inputYear, inputMonth);

        outputView.printNameAndAttendances(monthlyAttendances, crewName, inputYear, inputMonth);

        long attend = attendanceBook.getCountAttendanceStatus(monthlyAttendances, AttendanceStatus.ATTEND);
        long late = attendanceBook.getCountAttendanceStatus(monthlyAttendances, AttendanceStatus.LATE);
        long absent = attendanceBook.getCountAttendanceStatus(monthlyAttendances, AttendanceStatus.ABSENT);

        AcademicStatus academicStatus = attendanceBook.getAcademicStatusByCalendar(monthlyAttendances);
        outputView.printAcademicStatusResult(attend, late, absent, academicStatus);

        return false;
    }

    public boolean crewAtRiskOfExpulsion() {
        outputView.printRiskOfExpulsionIntro();

        LocalDate localDate = LocalDate.of(inputYear, inputMonth, inputDay);

        Stream.of(AcademicStatus.EXPELLED, AcademicStatus.INTERVIEW, AcademicStatus.WARNING)
                .forEach(status -> printCrewsByAcademicStatus(status, localDate));

        return false;
    }

    private void printCrewsByAcademicStatus(AcademicStatus status, LocalDate targetDate) {
        List<String> crewNames = attendanceBook.getExpulsionCrews(status, targetDate);

        List<Object[]> sortedCrewsInfo = getSortedCrewsInfo(crewNames, status);

        for (Object[] crew : sortedCrewsInfo) {
            String crewName = (String) crew[0];
            long absent = (long) crew[1];
            long late = (long) crew[2];

            outputView.printCrewAtRiskOfExpulsion(crewName, absent, late, status);
        }
    }

    private List<Object[]> getSortedCrewsInfo(List<String> crewNames, AcademicStatus status) {
        return crewNames.stream()
                .map(crewName -> {
                    Map<LocalDate, Attendance> monthlyAttendances =
                            attendanceBook.getMonthlyAttendances(crewName, inputYear, inputMonth);
                    long absent = attendanceBook.getCountAttendanceStatus(monthlyAttendances, AttendanceStatus.ABSENT);
                    long late = attendanceBook.getCountAttendanceStatus(monthlyAttendances, AttendanceStatus.LATE);

                    return new Object[]{crewName, absent, late, absent + late};
                })
                .sorted(createCrewInfoComparator())
                .collect(Collectors.toList());
    }

    private Comparator<Object[]> createCrewInfoComparator() {
        return Comparator
                .comparingLong((Object[] crew) -> (long) crew[3]).reversed()
                .thenComparing(crew -> (String) crew[0]);
    }

    private Time createTime(final LocalDate date, final String attendanceTime) {
        if (attendanceTime.matches(TIME_REGEX)) {
            String[] split = attendanceTime.split(":");
            return new Time(LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                    Parser.parseToInt(split[0]), Parser.parseToInt(split[1])));
        }
        throw new IllegalArgumentException("[ERROR] 올바른 시간 형식(HH:mm)으로 입력해주세요.");

    }

    private LocalTime createLocalTime(final String time) {
        if (time.matches(TIME_REGEX)) {
            String[] split = time.split(":");
            return LocalTime.of(Parser.parseToInt(split[0]), Parser.parseToInt(split[1]));
        }
        throw new IllegalArgumentException("[ERROR] 올바른 시간 형식(HH:mm)으로 입력해주세요.");
    }

    private LocalDate createLocalDate(final int year, final int month, final int day) {
        try {
            return LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 입력입니다.");
        }
    }
}
