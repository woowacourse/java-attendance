package controller;

import domain.AttendanceStoreManager;

public class StoreController implements Controller {
    private final AttendanceStoreManager attendanceStoreManager;

    public StoreController(AttendanceStoreManager attendanceStoreManager) {
        this.attendanceStoreManager = attendanceStoreManager;
    }

    @Override
    public void run() {
        attendanceStoreManager.save("src/main/resources/attendances.csv");
    }
}
