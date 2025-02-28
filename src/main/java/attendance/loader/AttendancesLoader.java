package attendance.loader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AttendancesLoader {
    private static final String FILE_NAME = "attendances.csv";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final Map<String, List<LocalDateTime>> rawDatas = new HashMap<>();

    public void load() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(FILE_NAME))
        ));

        bufferedReader.lines().skip(1).forEach(line -> {
            String[] parsed = line.split(",");
            String name = parsed[0].trim();
            String dateTimeStr = parsed[1].trim();

            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);
            rawDatas.computeIfAbsent(name, key -> new ArrayList<>())
                    .add(dateTime);
        });
    }

    public Map<String, List<LocalDateTime>> getRawDatas() {
        return Collections.unmodifiableMap(rawDatas);
    }
}
