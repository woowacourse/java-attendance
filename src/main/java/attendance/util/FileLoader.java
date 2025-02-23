package attendance.util;

import attendance.domain.CrewAttendance;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FileLoader {

    public static List<CrewAttendance> loadAll(final List<String> contents) {
        List<CrewAttendance> crewAttendances = new ArrayList<>();
        contents.forEach(content -> {
            List<String> seperatedContent = Arrays.stream(content.split(",")).toList();
            LocalDateTime localDateTime = LocalDateTime.parse(seperatedContent.get(1).replace(" ", "T"));

            Optional<CrewAttendance> crewAttendance = crewAttendances.stream()
                    .filter(crew -> crew.isNameMatch(seperatedContent.get(0)))
                    .findFirst();

            if (crewAttendance.isEmpty()) {
                crewAttendances.add(createCrewAttendance(seperatedContent.get(0), localDateTime));
                return;
            }
            crewAttendance.get().add(localDateTime);
        });
        return crewAttendances;
    }

    public static CrewAttendance createCrewAttendance(final String name, final LocalDateTime localDateTime) {
        CrewAttendance crewAttendance = new CrewAttendance(name);
        crewAttendance.add(localDateTime);
        return crewAttendance;
    }
}
