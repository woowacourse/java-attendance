package controller;

import service.AttendanceStoreService;

public class StoreController implements Controller {
    private final AttendanceStoreService attendanceStoreService;

    public StoreController(AttendanceStoreService attendanceStoreService) {
        this.attendanceStoreService = attendanceStoreService;
    }

    @Override
    public void run() {
        attendanceStoreService.save("src/main/resources/attendances.csv");
    }
}
