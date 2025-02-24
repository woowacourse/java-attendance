package util;


import domain.Attendance;
import domain.AttendanceCounter;
import domain.AttendanceDateTime;
import domain.Attendances;
import domain.Crew;
import domain.Crews;
import domain.Nickname;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class CrewGenerator {

    private static final int NICKNAME_IDX = 0;
    private static final int LOCAL_DATE_TIME_IDX = 1;

    private CrewGenerator() {
    }

    public static Crews generate(final List<String[]> parsedCrewsData, final LocalDate nowDate) {
        final Map<Nickname, LinkedList<Attendance>> crewData = new HashMap<>();
        parsedCrewsData
                .forEach(parsedCrewData -> addCrewData(parsedCrewData, crewData));
        final List<Integer> validDates = findValidDatesUntilToday(nowDate);
        final List<Crew> crews = new ArrayList<>();
        crewData.entrySet()
                .forEach(nicknameListEntry -> addCrewAttendances(nicknameListEntry, validDates, crews));
        return new Crews(crews);
    }

    private static void addCrewData(final String[] parsedCrewData, final Map<Nickname, LinkedList<Attendance>> crewData) {
        final String nickname = parsedCrewData[NICKNAME_IDX];
        final String localDateTime = parsedCrewData[LOCAL_DATE_TIME_IDX];
        final AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(localDateTime);
        final Nickname name = new Nickname(nickname);
        final Attendance attendance = new Attendance(attendanceDateTime);
        crewData.computeIfAbsent(name, k -> new LinkedList<>()).add(attendance);
    }

    private static void addCrewAttendances(final Entry<Nickname, LinkedList<Attendance>> nicknameListEntry,
                                           final List<Integer> validDates,
                                           final List<Crew> crews) {
        final Attendances attendances = getAttendances(nicknameListEntry, validDates);
        crews.add(new Crew(nicknameListEntry.getKey(), attendances, AttendanceCounter.of(attendances)));
    }

    private static Attendances getAttendances(final Entry<Nickname, LinkedList<Attendance>> nicknameListEntry,
                                              final List<Integer> validDates) {
        final LinkedList<Attendance> attendancesData = nicknameListEntry.getValue();
        attendancesData.sort(Comparator.comparing(Attendance::getLocalDateTime));
        final Attendances attendances = new Attendances(attendancesData);
        final List<Integer> noPresentAttendanceDates = findNoPresentAttendanceDates(validDates, attendances);
        for (Integer dayOfMonth : noPresentAttendanceDates) {
            addAttendance(dayOfMonth, attendances);
        }
        return attendances;
    }

    private static List<Integer> findNoPresentAttendanceDates(final List<Integer> validDates, final Attendances attendances) {
        final List<Integer> alreadyAttendanceDates = attendances.getDates();

        final List<Integer> noPresentAttendanceDates = new ArrayList<>(validDates);
        noPresentAttendanceDates.removeAll(alreadyAttendanceDates);
        return noPresentAttendanceDates;
    }

    private static void addAttendance(final Integer dayOfMonth, final Attendances attendances) {
        final LocalDate date = LocalDate.of(Constants.FIXED_YEAR, Constants.FIXED_MONTH, dayOfMonth);
        final AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(date, Constants.ABSENCE_TIME);
        final Attendance attendance = new Attendance(attendanceDateTime);
        attendances.addSorted(attendance);
    }

    public static List<Integer> findValidDatesUntilToday(final LocalDate date) {
        final int today = date.getDayOfMonth();

        final List<Integer> allDays = IntStream.range(1, today).boxed().collect(Collectors.toList());
        allDays.removeAll(getExcludeNotAttendanceDays());

        return allDays;
    }

    public static List<Integer> getExcludeNotAttendanceDays() {
        final List<Integer> excludeNotAttendanceDays = new ArrayList<>();
        excludeNotAttendanceDays.addAll(getWeekendDays());
        excludeNotAttendanceDays.addAll(HolidayManager.getHOLIDAYS());

        return excludeNotAttendanceDays;
    }

    private static Collection<Integer> getWeekendDays() {
        return IntStream.rangeClosed(1, Constants.LENGTH_OF_MONTH)
                .filter(CrewGenerator::excludeNotAttendanceDays)
                .boxed()
                .toList();
    }

    public static boolean excludeNotAttendanceDays(int day) {
        final DayOfWeek dayOfWeek = LocalDate.of(Constants.FIXED_YEAR, Constants.FIXED_MONTH, day).getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
