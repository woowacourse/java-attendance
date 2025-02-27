package domain;

import java.util.HashMap;
import java.util.Map;

public class CrewGroup {
    private final Map<String,Crew> crews;

    public CrewGroup() {
        this.crews = new HashMap<>();
    }

    public void addCrew(String crewName){
        if(has(crewName)){
            throw new IllegalArgumentException("[ERROR] 중복 되는 닉네임입니다.");
        }
        crews.put(crewName,new Crew(crewName));
    }

    public Crew findByName(String crewName){
        if(!has(crewName)){
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
        return crews.get(crewName);
    }

    private boolean has(String crewName){
        return crews.containsKey(crewName);
    }
}
