package controller;

import domain.AttendanceReader;
import domain.CrewAttendances;
import strategy.AttendanceCurrentDateGenerateStrategy;

public class AttendanceController {

    private final CrewAttendances crewAttendances;

    public AttendanceController(String fileName) {
        AttendanceReader attendanceReader = new AttendanceReader(fileName);
        crewAttendances = new CrewAttendances(new AttendanceCurrentDateGenerateStrategy(),
                attendanceReader.readAttendances());
    }

    public AttendanceController() {
        crewAttendances = new CrewAttendances(new AttendanceCurrentDateGenerateStrategy());
    }

    public void start() {

    }
}
