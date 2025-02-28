package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class CrewGenerator {

    private static final int NICKNAME_IDX = 0;
    private static final int DATETIME_IDX = 1;

    public static AttendanceManager parseCrewAndAttendanceBook(final List<String[]> parsedDatas) {
        final Map<Crew, AttendanceBook> attendanceBooks = new HashMap<>();
        for (final String[] parsedData : parsedDatas) {
            final String nicknameData = parsedData[NICKNAME_IDX];
            final String dateTimeData = parsedData[DATETIME_IDX];

            final Crew crew = Crew.of(nicknameData);
            final AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(dateTimeData);
            final Attendance attendance = Attendance.of(attendanceDateTime);

            final AttendanceBook attendanceBook = attendanceBooks.computeIfAbsent(crew, k -> new AttendanceBook(new TreeSet<>()));
            attendanceBook.add(attendance);
        }

        return new AttendanceManager(attendanceBooks);
    }
}
