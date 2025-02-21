package util;


import domain.Attendance;
import domain.AttendanceCounter;
import domain.Attendances;
import domain.Crew;
import domain.Crews;
import domain.Nickname;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class CrewGenerator {

    private CrewGenerator() {
    }

    public static Crews generate(List<String[]> parsedCrewsData, LocalDate nowDate) {
        Map<Nickname, List<Attendance>> crewData = new HashMap<>();
        for (String[] parsedCrewData : parsedCrewsData) {
            String nickname = parsedCrewData[0];
            String localDateTime = parsedCrewData[1];
            final Nickname name = new Nickname(nickname);
            final Attendance attendance = Attendance.of(localDateTime);
            crewData.computeIfAbsent(name, k -> new ArrayList<>()).add(attendance);
        }

        final List<Integer> validDates = getValidDates(nowDate);
        List<Crew> crews = new ArrayList<>();
        for (Entry<Nickname, List<Attendance>> nicknameListEntry : crewData.entrySet()) {
            final Attendances attendances = getAttendances(nicknameListEntry, validDates);
            crews.add(new Crew(nicknameListEntry.getKey(), attendances, AttendanceCounter.of(attendances)));

        }
        return new Crews(crews);
    }

    private static Attendances getAttendances(final Entry<Nickname, List<Attendance>> nicknameListEntry,
                                              final List<Integer> validDates) {
        final Attendances attendances = new Attendances(nicknameListEntry.getValue());
        List<Integer> alreadyAttendanceDates = attendances.getDates();

        List<Integer> noPresentAttendanceDates = new ArrayList<>(validDates);
        noPresentAttendanceDates.removeAll(alreadyAttendanceDates);
        for (Integer attendanceDate : noPresentAttendanceDates) {
            Attendance attendance = Attendance.generateAbsentAttendance(attendanceDate);
            attendances.add(attendance);
        }
        return attendances;
    }

    public static List<Integer> getValidDates(LocalDate localDate) {
        final int today = localDate.getDayOfMonth();

        List<Integer> allDays = IntStream.range(1, today).boxed().collect(Collectors.toList());
        allDays.removeAll(getExcludeNotAttendanceDays());

        return allDays;
    }

    public static List<Integer> getExcludeNotAttendanceDays() {
        List<Integer> excludeNotAttendanceDays = new ArrayList<>();
        excludeNotAttendanceDays.addAll(getWeekendDays());
        excludeNotAttendanceDays.addAll(Constants.HOLIDAYS);

        return excludeNotAttendanceDays;
    }

    private static Collection<Integer> getWeekendDays() {
        return IntStream.rangeClosed(1, Constants.LENGTH_OF_MONTH)
                .filter(CrewGenerator::excludeNotAttendanceDays)
                .boxed()
                .toList();
    }

    public static boolean excludeNotAttendanceDays(int day) {
        DayOfWeek dayOfWeek = LocalDate.of(2024, Constants.FIXED_MONTH, day).getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
