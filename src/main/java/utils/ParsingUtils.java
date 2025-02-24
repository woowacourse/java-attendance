package utils;

import java.util.Arrays;
import java.util.List;

public class ParsingUtils {
    public static List<String> parseRecordToNameAndDate(String existedRecords) {
        return Arrays.asList(existedRecords.split(","));
    }

    public static List<String> parseDateToDayAndTime(String date) {
        return Arrays.asList(date.split(" "));
    }
}