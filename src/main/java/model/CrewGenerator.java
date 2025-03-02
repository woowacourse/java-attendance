package model;

import java.time.LocalTime;
import java.util.*;

public class CrewGenerator {

    private static final int NICKNAME_IDX = 0;
    private static final int DATETIME_IDX = 1;

    public static AttendanceManager parseCrewAndAttendanceBook(final AttendanceDateTime todayDateTime, final List<String[]> parsedDatas) {
        final Map<Crew, AttendanceBook> attendanceBooks = new HashMap<>();
        final int toDayOfMonth = findLastNonPresentDayOfMonth(todayDateTime);
        final Set<Integer> validDatesTo = ValidManager.getInstance().getDatesTo(toDayOfMonth);
        for (final String[] parsedData : parsedDatas) {
            final String nicknameData = parsedData[NICKNAME_IDX];
            final String dateTimeData = parsedData[DATETIME_IDX];

            final Crew crew = Crew.of(nicknameData);
            final AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(dateTimeData);
            final Attendance attendance = Attendance.of(attendanceDateTime);

            final AttendanceBook attendanceBook = attendanceBooks.computeIfAbsent(crew, k -> new AttendanceBook(new TreeSet<>()));
            attendanceBook.add(attendance);
        }

        for (final AttendanceBook attendanceBook : attendanceBooks.values()) {
            final Set<Integer> nonPresentAttendanceDays = calculateNonPresentAttendanceDats(attendanceBook, validDatesTo);
            for (final int day : nonPresentAttendanceDays) {
                final AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2024, 12, day, LocalTime.of(0, 0));
                final Attendance attendance = Attendance.of(attendanceDateTime);

                attendanceBook.add(attendance);
            }
        }

        return new AttendanceManager(attendanceBooks);
    }

    private static int findLastNonPresentDayOfMonth(final AttendanceDateTime todayDateTime) {
        final int todayDayOfMonth = todayDateTime.getDateTime().getDayOfMonth();
        return ValidManager.getInstance().getLastByDayOfMonth(todayDayOfMonth);
    }

    private static Set<Integer> calculateNonPresentAttendanceDats(final AttendanceBook attendanceBook, final Set<Integer> validateDatesTo) {
        final Set<Integer> nonPresentAttendanceDays = new HashSet<>(validateDatesTo);
        final Set<Integer> alreadyAttendanceDays = attendanceBook.getAllDayOfMonth();
        nonPresentAttendanceDays.removeAll(alreadyAttendanceDays);
        return nonPresentAttendanceDays;
    }
}
