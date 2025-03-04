package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Attendances {
    private static final String IS_DUPLICATE_ATTENDANCE = "[ERROR] 이미 해당 날짜의 출석 시간으로 체크한 크루입니다.\n";
    private static final String NO_SUCH_ATTENDANCE = "[ERROR] 해당 크루의 해당 날짜에 대한 출석 정보가 없습니다.\n";

    private static final LocalTime DEFAULT_TIME = LocalTime.of(0, 0);
    private static final LocalDate START_DAY = LocalDate.of(2025, 2, 1);
    private static final int ONE_DAY = 1;
    private static final int WEEKDAY_MAX_VALUE = 5;

    private final List<Attendance> attendances;

    public Attendances() {
        attendances = new ArrayList<>();
    }

    public Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void initializeAttendances(final Map<String, List<LocalDateTime>> attendancesData) {
        for (Map.Entry<String, List<LocalDateTime>> entry : attendancesData.entrySet()) {
            CrewName crewName = new CrewName(entry.getKey());
            List<LocalDateTime> attendanceDateTimes = entry.getValue();

            initializeAttendance(crewName, attendanceDateTimes);
        }
    }

    private void initializeAttendance(final CrewName crewName, final List<LocalDateTime> attendanceDateTimes) {
        for (LocalDateTime attendanceDateTime : attendanceDateTimes) {
            AttendanceDate attendanceDate = new AttendanceDate(attendanceDateTime.toLocalDate());
            AttendanceTime attendanceTime = new AttendanceTime(attendanceDateTime.toLocalTime());

            attendances.add(new Attendance(crewName, attendanceDate, attendanceTime));
        }
        fillMissingAttendance(crewName, attendanceDateTimes);
    }

    private void fillMissingAttendance(final CrewName crewName, final List<LocalDateTime> attendanceDateTimes) {
        LocalDate today = LocalDate.now();
        for (LocalDate date = START_DAY; date.isBefore(today); date = date.plusDays(ONE_DAY)) {
            addMissingAttendance(crewName, attendanceDateTimes, date);
        }
    }

    private void addMissingAttendance(final CrewName crewName, final List<LocalDateTime> attendanceDateTimes,
                                      final LocalDate missingDate) {
        if (isMissingAttendance(attendanceDateTimes, missingDate) && !isWeekend(missingDate)) {
            AttendanceDate attendanceDate = new AttendanceDate(missingDate);
            AttendanceTime attendanceTime = new AttendanceTime(DEFAULT_TIME);

            attendances.add(new Attendance(crewName, attendanceDate, attendanceTime));
        }
    }

    private boolean isMissingAttendance(final List<LocalDateTime> attendanceDateTimes, final LocalDate missingDate) {
        return attendanceDateTimes.stream().noneMatch(dateTime -> dateTime.toLocalDate().equals(missingDate));
    }

    private boolean isWeekend(final LocalDate date) {
        return date.getDayOfWeek().getValue() > WEEKDAY_MAX_VALUE;
    }

    public void addAttendance(final Attendance attendance) {
        if (attendances.contains(attendance)) {
            throw new IllegalArgumentException(IS_DUPLICATE_ATTENDANCE);
        }
        attendances.add(attendance);
    }

    public Attendances lookupCrewAttendance(final CrewName crewName) {
        List<Attendance> crewAttendances = new ArrayList<>();

        attendances.stream()
                .filter(attendance -> attendance.hasSameCrewName(crewName.getCrewName()))
                .forEach(crewAttendances::add);

        return new Attendances(sortAttendances(crewAttendances));
    }

    private List<Attendance> sortAttendances(final List<Attendance> attendances) {
        return attendances.stream()
                .sorted(Comparator.comparing(Attendance::getAttendanceDate))
                .toList();
    }

    public Attendance findCrewAttendanceByDate(final CrewName crewName, final AttendanceDate attendanceDate) {
        return attendances.stream()
                .filter(attendance -> attendance.hasSameCrewName(crewName.getCrewName()))
                .filter(attendance -> attendance.hasSameAttendanceDate(attendanceDate.getAttendanceDate()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(NO_SUCH_ATTENDANCE));
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }
}
