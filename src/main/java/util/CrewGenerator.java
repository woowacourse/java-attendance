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
import java.time.LocalDateTime;
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
        for (String[] parsedCrewData : parsedCrewsData) {
            String nickname = parsedCrewData[NICKNAME_IDX];
            String localDateTime = parsedCrewData[LOCAL_DATE_TIME_IDX];
            AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(localDateTime);
            final Nickname name = new Nickname(nickname);
            final Attendance attendance = new Attendance(attendanceDateTime);
            crewData.computeIfAbsent(name, k -> new LinkedList<>()).add(attendance);
        }

        final List<Integer> validDates = getValidDates(nowDate);
        List<Crew> crews = new ArrayList<>();
        for (Entry<Nickname, LinkedList<Attendance>> nicknameListEntry : crewData.entrySet()) {
            final Attendances attendances = getAttendances(nicknameListEntry, validDates);
            crews.add(new Crew(nicknameListEntry.getKey(), attendances, AttendanceCounter.of(attendances)));

        }
        return new Crews(crews);
    }

    private static Attendances getAttendances(final Entry<Nickname, LinkedList<Attendance>> nicknameListEntry,
                                              final List<Integer> validDates) {
        LinkedList<Attendance> attendancesData = nicknameListEntry.getValue();
        attendancesData.sort(Comparator.comparing(Attendance::getLocalDateTime));
        final Attendances attendances = new Attendances(attendancesData);
        List<Integer> alreadyAttendanceDates = attendances.getDates();

        List<Integer> noPresentAttendanceDates = new ArrayList<>(validDates);
        noPresentAttendanceDates.removeAll(alreadyAttendanceDates);
        for (Integer attendanceDate : noPresentAttendanceDates) {
            AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(LocalDateTime.of(
                    LocalDate.of(Constants.FIXED_YEAR, Constants.FIXED_MONTH, attendanceDate),
                    Constants.ABSENCE_TIME));
            Attendance attendance = new Attendance(attendanceDateTime);
            attendances.addSorted(attendance);
        }
        return attendances;
    }

    public static List<Integer> getValidDates(final LocalDate localDate) {
        final int today = localDate.getDayOfMonth();

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
