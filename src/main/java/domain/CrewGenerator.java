package domain;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CrewGenerator {

    private final int referenceYear;
    private final int referenceMonth;

    public CrewGenerator(int referenceYear, int referenceMonth) {
        this.referenceYear = referenceYear;
        this.referenceMonth = referenceMonth;
    }

    public Crews generate(final List<String[]> parsedCrewsData, final LocalDate nowDate) {
        final Map<Nickname, TreeSet<Attendance>> crewData = generateCrewData(parsedCrewsData);
        final List<Integer> validDates = getValidDates(nowDate);
        final List<Crew> crews = new ArrayList<>();

        processCrewData(crewData, validDates, crews);

        return new Crews(crews);
    }

    private Map<Nickname, TreeSet<Attendance>> generateCrewData(final List<String[]> parsedCrewsData) {
        final Map<Nickname, TreeSet<Attendance>> crewData = new HashMap<>();

        for (String[] parsedCrewData : parsedCrewsData) {
            final String nickname = parsedCrewData[0];
            final String localDateTime = parsedCrewData[1];
            final Nickname name = new Nickname(nickname);
            final Attendance attendance = Attendance.from(localDateTime);
            crewData.computeIfAbsent(name, k -> new TreeSet<>()).add(attendance);
        }

        return crewData;
    }

    private void processCrewData(final Map<Nickname, TreeSet<Attendance>> crewData,
                                 final List<Integer> validDates,
                                 final List<Crew> crews) {
        for (Map.Entry<Nickname, TreeSet<Attendance>> entry : crewData.entrySet()) {
            final Attendances attendances = getAttendances(entry, validDates);
            crews.add(new Crew(entry.getKey(), attendances, AttendanceCounter.of(attendances)));
        }
    }

    private Attendances getAttendances(final Map.Entry<Nickname, TreeSet<Attendance>> entry,
                                       final List<Integer> validDates) {
        final Attendances attendances = new Attendances(entry.getValue());
        final List<Integer> alreadyAttendanceDates = attendances.getDayOfMonth();
        final List<Integer> noPresentAttendanceDates = new ArrayList<>(validDates);
        noPresentAttendanceDates.removeAll(alreadyAttendanceDates);

        for (Integer attendanceDate : noPresentAttendanceDates) {
            Attendance attendance = Attendance.generateAbsentAttendance(attendanceDate);
            attendances.add(attendance);
        }

        return attendances;
    }

    private List<Integer> getValidDates(final LocalDate localDate) {
        final int today = localDate.getDayOfMonth();
        final List<Integer> allDays = IntStream.range(1, today)
                .boxed()
                .collect(Collectors.toList());
        allDays.removeAll(getExcludeNotAttendanceDays());

        return allDays;
    }

    private List<Integer> getExcludeNotAttendanceDays() {
        final List<Integer> excludeNotAttendanceDays = new ArrayList<>();
        excludeNotAttendanceDays.addAll(getWeekendDays());
        excludeNotAttendanceDays.addAll(Week.HOLIDAYS);
        return excludeNotAttendanceDays;
    }

    private List<Integer> getWeekendDays() {
        final YearMonth yearMonth = YearMonth.of(referenceYear, referenceMonth);
        final int lengthOfMonth = yearMonth.lengthOfMonth();
        return IntStream.rangeClosed(1, lengthOfMonth)
                .filter(this::excludeNotAttendanceDays)
                .boxed()
                .toList();
    }

    public boolean excludeNotAttendanceDays(final int day) {
        final DayOfWeek dayOfWeek = LocalDate.of(referenceYear, referenceMonth, day).getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}

