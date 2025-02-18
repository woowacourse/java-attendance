import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    public static List<String> parse(List<String> loadedData) {
        loadedData.removeFirst();
        return loadedData;
    }

    public static List<List<String>> parseName(List<String> removedData) {
        List<List<String>> result = new ArrayList<>();
        for (String data: removedData) {
            List<String> nameSeperatedData = Arrays.asList(data.split(","));
            result.add(nameSeperatedData);
        }
        return result;
    }

    public static List<String> parseDate(String rawDate) {
        return Arrays.asList(rawDate.split(" "));
    }
}