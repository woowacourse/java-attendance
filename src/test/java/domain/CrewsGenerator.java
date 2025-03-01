package domain;

import java.util.HashMap;
import java.util.Map;

public class CrewsGenerator {

    private CrewsGenerator() {

    }

    public static Map<String, Crew> generate() {
        return new HashMap<String, Crew>();
    }
}
