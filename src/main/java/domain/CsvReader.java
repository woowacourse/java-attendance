package domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class CsvReader {

    private static final String CSV_DELIMITER = ",";
    private static final String DATE_TIME_DELIMITER = " ";
    private static final int TOTAL_ROW_COUNT = 2;
    private static final int TOTAL_DATE_TIME_COUNT = 2;
    private static final int NAME_INDEX = 0;
    private static final int DATE_TIME_INDEX = 1;
    private static final int DATE_INDEX = 0;
    private static final int TIME_INDEX = 1;

    public static List<String> readCsv(String csvPath) {
        URL fileURL = CsvReader.class.getClassLoader().getResource(csvPath);
        return readFile(fileURL);
    }

    public static String parseName(String row) {
        String[] split = splitRow(row);
        validRowFormat(split);
        return split[NAME_INDEX].strip();
    }

    public static Attend parseAttend(String row) {
        String[] split = splitRow(row);
        validRowFormat(split);
        String dateTime = split[DATE_TIME_INDEX];
        String[] dateTimeParse = splitTime(dateTime);
        validDateTimeFormat(dateTimeParse);
        return Attend.of(parseDate(dateTimeParse[DATE_INDEX].strip()), parseTime(dateTimeParse[TIME_INDEX].strip()));
    }

    private static String[] splitRow(String row) {
        return row.split(CSV_DELIMITER);
    }

    private static void validRowFormat(String[] split) {
        if (split.length != TOTAL_ROW_COUNT) {
            throw new IllegalArgumentException("파일 행 구조가 잘못되었습니다.");
        }
    }

    private static LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("날짜 형식이 잘못되었습니다.");
        }
    }

    private static LocalTime parseTime(String time) {
        try {
            return LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("시간 형식이 잘못되었습니다.");
        }
    }

    private static String[] splitTime(String dateTime) {
        String[] result = dateTime.split(DATE_TIME_DELIMITER);
        validDateTimeFormat(result);
        return result;
    }

    private static List<String> readFile(URL fileURL) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileURL.toURI()))) {
            return removeTitleRow(reader);
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException(String.format("경로 문제: %s", fileURL.getPath()));
        }
    }

    private static List<String> removeTitleRow(BufferedReader reader) {
        return reader.lines().skip(1).toList();
    }

    private static void validDateTimeFormat(String[] dateTimeParse) {
        if (dateTimeParse.length != TOTAL_DATE_TIME_COUNT) {
            throw new IllegalArgumentException("datetime 형식이 잘못되었습니다.");
        }
    }
}
