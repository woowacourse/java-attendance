package controller;

import domain.CrewAttendanceStorage;
import domain.FileStoreManager;

public class StoreController implements Controller {
    private static final String FILE = "src/main/resources/attendances.csv";

    private final CrewAttendanceStorage crewAttendanceStorage;

    public StoreController(CrewAttendanceStorage crewAttendanceStorage) {
        this.crewAttendanceStorage = crewAttendanceStorage;
    }

    @Override
    public void run() {
        save();
    }

    private void save() {
        FileStoreManager fileStoreManager = new FileStoreManager(crewAttendanceStorage);
        fileStoreManager.save(FILE);
    }
}
