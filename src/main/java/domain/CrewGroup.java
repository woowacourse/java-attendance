package domain;

import static domain.AlertCode.NORMAL;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CrewGroup {
    private final Map<String, Attendances> crewInformation = new HashMap<>();

    public void add(String name, Attendances attendances) {
        crewInformation.put(name, attendances);
    }

    public void validateCrewName(String name) {
        if (!crewInformation.containsKey(name)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
    }

    public Attendances getSpecificAttendances(String name) {
        validateCrewName(name);
        return crewInformation.get(name);
    }

    public Map<String, Attendances> getAlertCrews() {
        return crewInformation.entrySet().stream()
                .filter(stringAttendancesEntry -> stringAttendancesEntry.getValue().calucateAlertCode() != NORMAL)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
