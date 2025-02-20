package util;

import static util.Constants.HOLIDAYS;
import static util.Constants.LENGTH_OF_MONTH;
import static util.Constants.MONTH;

import domain.Attendance;
import domain.AttendanceCounter;
import domain.Attendances;
import domain.Crew;
import domain.Crews;
import domain.Nickname;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
            final Attendances attendances = new Attendances(nicknameListEntry.getValue());
            List<Integer> alreadyAttendanceDates = attendances.getDates();

            List<Integer> noPresentAttendanceDates = new ArrayList<>(validDates);
            noPresentAttendanceDates.removeAll(alreadyAttendanceDates);
            for (Integer attendanceDate : noPresentAttendanceDates) {
                LocalDateTime dateTime = LocalDateTime.of(LocalDate.of(2024, 12, attendanceDate), LocalTime.of(0, 0));
                Attendance attendance = new Attendance(dateTime);
                attendances.add(attendance);
            }
            crews.add(new Crew(nicknameListEntry.getKey(), attendances, AttendanceCounter.of(attendances)));

        }
        return new Crews(crews);
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
        excludeNotAttendanceDays.addAll(HOLIDAYS);

        return excludeNotAttendanceDays;
    }

    private static Collection<Integer> getWeekendDays() {
        return IntStream.rangeClosed(1, LENGTH_OF_MONTH)
                .filter(CrewGenerator::excludeNotAttendanceDays)
                .boxed()
                .toList();
    }

    public static boolean excludeNotAttendanceDays(int day) {
        DayOfWeek dayOfWeek = LocalDate.of(2024, MONTH, day).getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
