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

    public static Crews generate(final List<String[]> parsedCrewsData, LocalDate nowDate) {
        Map<Nickname, LinkedList<Attendance>> crewData = new HashMap<>();
        parsedCrewsData
                .forEach(parsedCrewData -> addCrewData(parsedCrewData, crewData));
        final List<Integer> validDates = findValidDatesUntilToday(nowDate);
        List<Crew> crews = new ArrayList<>();
        crewData.entrySet()
                .forEach(nicknameListEntry -> addCrewAttendances(nicknameListEntry, validDates, crews));
        return new Crews(crews);
    }

    private static void addCrewData(String[] parsedCrewData, Map<Nickname, LinkedList<Attendance>> crewData) {
        String nickname = parsedCrewData[NICKNAME_IDX];
        String localDateTime = parsedCrewData[LOCAL_DATE_TIME_IDX];
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(localDateTime);
        final Nickname name = new Nickname(nickname);
        final Attendance attendance = new Attendance(attendanceDateTime);
        crewData.computeIfAbsent(name, k -> new LinkedList<>()).add(attendance);
    }

    private static void addCrewAttendances(Entry<Nickname, LinkedList<Attendance>> nicknameListEntry,
                                           List<Integer> validDates,
                                           List<Crew> crews) {
        final Attendances attendances = getAttendances(nicknameListEntry, validDates);
        crews.add(new Crew(nicknameListEntry.getKey(), attendances, AttendanceCounter.of(attendances)));
    }

    private static Attendances getAttendances(final Entry<Nickname, LinkedList<Attendance>> nicknameListEntry,
                                              final List<Integer> validDates) {
        LinkedList<Attendance> attendancesData = nicknameListEntry.getValue();
        attendancesData.sort(Comparator.comparing(Attendance::getLocalDateTime));
        final Attendances attendances = new Attendances(attendancesData);
        List<Integer> noPresentAttendanceDates = findNoPresentAttendanceDates(validDates, attendances);
        for (Integer dayOfMonth : noPresentAttendanceDates) {
            addAttendance(dayOfMonth, attendances);
        }
        return attendances;
    }

    private static List<Integer> findNoPresentAttendanceDates(List<Integer> validDates, Attendances attendances) {
        List<Integer> alreadyAttendanceDates = attendances.getDates();

        List<Integer> noPresentAttendanceDates = new ArrayList<>(validDates);
        noPresentAttendanceDates.removeAll(alreadyAttendanceDates);
        return noPresentAttendanceDates;
    }

    private static void addAttendance(Integer dayOfMonth, Attendances attendances) {
        LocalDate date = LocalDate.of(Constants.FIXED_YEAR, Constants.FIXED_MONTH, dayOfMonth);
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(date, Constants.ABSENCE_TIME);
        Attendance attendance = new Attendance(attendanceDateTime);
        attendances.addSorted(attendance);
    }

    public static List<Integer> findValidDatesUntilToday(final LocalDate date) {
        final int today = date.getDayOfMonth();

        List<Integer> allDays = IntStream.range(1, today).boxed().collect(Collectors.toList());
        allDays.removeAll(getExcludeNotAttendanceDays());

        return allDays;
    }

    public static List<Integer> getExcludeNotAttendanceDays() {
        List<Integer> excludeNotAttendanceDays = new ArrayList<>();
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
        DayOfWeek dayOfWeek = LocalDate.of(Constants.FIXED_YEAR, Constants.FIXED_MONTH, day).getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
