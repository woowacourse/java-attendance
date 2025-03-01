package model;

import static constant.AttendanceConstant.COMMA_SEPARATOR;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import util.InputParser;

public class Attendances {

    private final Map<Crew, List<Attendance>> attendances;

    private Attendances(Map<Crew, List<Attendance>> attendances) {
        this.attendances = attendances;
    }

    public static Attendances from(List<String> inputs) {
        return new Attendances(inputs.stream()
                .map(line -> InputParser.split(line, COMMA_SEPARATOR))
                .collect(Collectors.toMap(
                        line -> Crew.of(line.get(0)),
                        line -> new ArrayList<>(List.of(Attendance.of(line.get(1)))),
                        (existing, replacement) -> {
                            existing.addAll(replacement);
                            return existing;
                        },
                        HashMap::new
                )));
    }

    public Map<Crew, List<Attendance>> getAttendances() {
        return Collections.unmodifiableMap(attendances);
    }
}
