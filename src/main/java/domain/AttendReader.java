package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class AttendReader {

    private final String csvPath;

    public AttendReader(final String csvPath) {
        this.csvPath = csvPath;
    }

    public AttendanceBook loadAttendanceBook(LocalDate today) {
        AttendanceBook attendanceBook = new AttendanceBook(today);
        List<String> rows = CsvReader.readFile(csvPath);
        CsvReader.removeFirstRow(rows);
        for (String row : rows) {
            addAttendData(row, attendanceBook);
        }
        return attendanceBook;
    }

    private void addAttendData(final String row, final AttendanceBook attendanceBook) {
        List<String> splitRow = CsvReader.splitRow(row);
        String name = splitRow.get(0);
        isEmptyString(name);
        Nickname nickName = new Nickname(name);
        attendanceBook.register(nickName);
        addAttendPerUser(attendanceBook, nickName, splitRow.get(1));
    }

    private void addAttendPerUser(final AttendanceBook attendanceBook, final Nickname name, final String rawDateTime) {
        isEmptyString(rawDateTime);
        String[] splitDateTime = rawDateTime.split(" ");
        Attend attend = createAttend(splitDateTime);
        attendanceBook.addAttend(name, attend);
    }

    private static void isEmptyString(final String rawName) {
        if (rawName == null || rawName.isBlank()) {
            throw new IllegalArgumentException("빈 문자열 입니다.");
        }
    }

    private Attend createAttend(final String[] splitDateTime) {
        isNotMatchDateTimeRowFormat(splitDateTime);
        String rawDate = splitDateTime[0];
        LocalDate date = convertDate(rawDate);
        String rawTime = splitDateTime[1];
        LocalTime time = convertTime(rawTime);
        return new Attend(date, time);
    }

    private void isNotMatchDateTimeRowFormat(final String[] splitDateTime) {
        if (splitDateTime.length != 2) {
            throw new IllegalArgumentException("날짜 형식이 잘못되었습니다");
        }
    }

    private LocalDate convertDate(final String rawDate) {
        try {
            return LocalDate.parse(rawDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("잘못된 날짜 형식입니다");
        }
    }

    private LocalTime convertTime(final String rawTime) {
        try {
            return LocalTime.parse(rawTime);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("잘못된 시간 형식입니다");
        }
    }
}
