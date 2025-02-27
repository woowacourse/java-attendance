package model;

import java.util.Map;

public class AttendanceBook {

    private final Map<String, Crew> crewRecords;

    public AttendanceBook(Map<String, Crew> crewRecords) {
        this.crewRecords = crewRecords;
    }
}
