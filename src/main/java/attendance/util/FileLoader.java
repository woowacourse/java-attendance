package attendance.util;

import attendance.domain.CrewAttendance;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileLoader {

    public static Map<String, CrewAttendance> loadAll(final List<String> contents) {
        Map<String, CrewAttendance> crewAttendances = new HashMap<>();
        contents.forEach(content -> {
            List<String> seperatedContent = Arrays.stream(content.split(",")).toList();
            String crewName = seperatedContent.getFirst();
            LocalDateTime localDateTime = LocalDateTime.parse(seperatedContent.getLast().replace(" ", "T"));

            CrewAttendance crewAttendance = createCrewAttendance(crewName, crewAttendances);
            crewAttendance.add(localDateTime);
        });
        return crewAttendances;
    }

    public static CrewAttendance createCrewAttendance(final String crewName,
                                                      final Map<String, CrewAttendance> crewAttendances) {
        if (!crewAttendances.containsKey(crewName)) {
            crewAttendances.put(crewName, new CrewAttendance(crewName));
        }
        return crewAttendances.get(crewName);
    }
}
