package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Attendances {
    private static final String ALREADY_CHECKED_ERROR_MESSAGE = "[ERROR] 오늘은 이미 출석을 체크하셨습니다.";
    private static final String SAME_TIME_ERROR_MESSAGE = "[ERROR] 이미 동일한 시간에 출석 기록이 존재합니다.";
    private static final String INVALID_CREW_DATE_ERROR_MESSAGE = "[ERROR] 해당 날짜에는 출석 체크 기록이 존재할 수 없습니다.";

    private static final String SPACE_DELIMITER = " ";

    private static final int HOUR_DEFAULT_VALUE = 0;
    private static final int MINUTE_DEFAULT_VALUE = 0;

    private static final int DAY_VALUE = 1;
    private static final LocalDate FIRST_DAY = LocalDate.of(2025, 2, 1);

    private final List<Attendance> attendances;

    public Attendances() {
        this.attendances = new ArrayList<>();
    }

    public Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void initAttendances(final Crews crews, final List<List<String>> attendanceRecords) {
        for (List<String> attendanceRecord : attendanceRecords) {
            String crewName = attendanceRecord.getFirst();
            Crew crew = crews.findCrew(crewName);

            LocalDateTime localDateTime = convertToLocalDateTime(attendanceRecord);

            AttendanceType status = AttendanceType.of(localDateTime);
            attendances.add(new Attendance(crew, localDateTime, status));
        }
        fillAbsentDay(crews);
    }

    private LocalDateTime convertToLocalDateTime(final List<String> attendanceRecord) {
        List<String> dateInfo = List.of(attendanceRecord.getLast().split(SPACE_DELIMITER));

        return LocalDateTime.of(
                LocalDate.parse(dateInfo.getFirst()),
                LocalTime.parse(dateInfo.getLast())
        );
    }

    private void fillAbsentDay(final Crews crews) {
        LocalDate today = LocalDate.now();

        for (Crew crew : crews.getCrews()) {
            checkDaysBeforeToday(crew, today);
        }
    }

    private void checkDaysBeforeToday(final Crew crew, final LocalDate today) {
        for (LocalDate day = FIRST_DAY; day.isBefore(today); day = day.plusDays(DAY_VALUE)) {
            checkNotExistingWorkingDay(crew, day);
        }
    }

    private void checkNotExistingWorkingDay(final Crew crew, final LocalDate day) {
        if (isWorkDay(day) && !isExistingDay(crew, day)) {
            LocalTime localTime = LocalTime.of(HOUR_DEFAULT_VALUE, MINUTE_DEFAULT_VALUE);
            LocalDateTime localDateTime = LocalDateTime.of(day, localTime);

            attendances.add(new Attendance(crew, localDateTime, AttendanceType.ABSENT));
        }
    }

    private boolean isWorkDay(final LocalDate today) {
        return !today.getDayOfWeek().equals(DayOfWeek.SATURDAY) && !today.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }

    private boolean isExistingDay(final Crew crew, final LocalDate day) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.isSameCrewDate(crew, day));
    }

    public void add(final Attendance attendance) {
        attendances.add(attendance);
    }

    public void hasCheckedAttendance(final Crew crew, final LocalDate today) {
        boolean isCheckedAttendance = attendances.stream()
                .anyMatch(attendance -> attendance.isSameCrewDate(crew, today));

        if (isCheckedAttendance) {
            throw new IllegalArgumentException(ALREADY_CHECKED_ERROR_MESSAGE);
        }
    }

    public List<Attendance> findCrewAttendances(final Crew crew) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameCrew(crew))
                .toList();
    }

    public Attendance findMatchCrewDate(final Crew crew, final LocalDate localDate) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameCrewDate(crew, localDate))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_CREW_DATE_ERROR_MESSAGE));
    }

    public String findOriginalTime(final Crew crew, final LocalDate localDate) {
        return findMatchCrewDate(crew, localDate).getTimeValue();
    }

    public AttendanceType findOriginalType(final Crew crew, final LocalDate localDate) {
        return findMatchCrewDate(crew, localDate).getType();
    }

    public void modifyAttendances(final Crew crew, final LocalDateTime localDateTime) {
        for (Attendance attendance : attendances) {
            modifyAttendance(crew, localDateTime, attendance);
        }
    }

    private void modifyAttendance(final Crew crew, final LocalDateTime localDateTime,
                                  final Attendance attendance) {
        LocalDate localDate = localDateTime.toLocalDate();
        checkSameTimeModification(attendance, localDateTime);

        if (attendance.isSameCrewDate(crew, localDate)) {
            attendance.modifyLocalDateTime(localDateTime);
        }
    }

    private void checkSameTimeModification(final Attendance attendance, final LocalDateTime localDateTime) {
        if (attendance.isSameTime(localDateTime)) {
            throw new IllegalArgumentException(SAME_TIME_ERROR_MESSAGE);
        }
    }

    public List<Attendance> sortCrewAttendances() {
        return attendances.stream()
                .sorted(Comparator.comparing(Attendance::getDate))
                .toList();
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }
}
