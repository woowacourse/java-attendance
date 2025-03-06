package util;

import java.util.Arrays;
import java.util.List;

public class InputParser {

    private InputParser() {
    }

    public static String trim(String input) {
        return input.trim();
    }

    public static List<String> split(String input, String separator) {
        return Arrays.stream(input.split(separator))
                .toList();
    }
}
