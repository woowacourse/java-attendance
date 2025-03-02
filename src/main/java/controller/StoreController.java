package controller;

import domain.CrewAttendanceStorage;

import java.io.BufferedReader;
import java.io.FileReader;

public class StoreController implements Controller {
    private final String FILE = "";
    private final CrewAttendanceStorage crewAttendanceStorage;

    public StoreController(CrewAttendanceStorage crewAttendanceStorage) {
        this.crewAttendanceStorage = crewAttendanceStorage;
    }

    @Override
    public void run() {
        save();
    }

    private void save() {

    }
}
