package domain;

import domain.attendance.StudentStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class CrewGroup {
    private final Map<String,Crew> crews;

    public CrewGroup() {
        this.crews = new HashMap<>();
    }

    public void addCrew(String crewName){
        if(has(crewName)){
            throw new IllegalArgumentException("중복 되는 닉네임입니다.");
        }
        crews.put(crewName,new Crew(crewName));
    }

    public Crew findByName(String crewName){
        if(!has(crewName)){
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
        return crews.get(crewName);
    }

    public boolean has(String crewName){
        return crews.containsKey(crewName);
    }

    public List<Crew> getSortedWarningCrews() {
        Predicate<Map.Entry<String,Crew>> isNotNoneStatus = crewEntry -> !crewEntry.getValue()
                .getAttendanceRecord()
                .getStudentStatus()
                .equals(StudentStatus.NONE);

        return crews.entrySet().stream()
                .filter(isNotNoneStatus)
                .map(Map.Entry::getValue)
                .sorted(Crew.CREW_COMPARATOR)
                .toList();
    }
}
