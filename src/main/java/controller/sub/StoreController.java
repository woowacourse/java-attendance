package controller.sub;

import service.AttendanceStoreService;

public class StoreController implements SubController {
    private final AttendanceStoreService attendanceStoreService;

    public StoreController(AttendanceStoreService attendanceStoreService) {
        this.attendanceStoreService = attendanceStoreService;
    }

    @Override
    public void run() {
        String attendanceStorePath = "./src/main/resources/attendances.csv";
        attendanceStoreService.save(attendanceStorePath);
    }
}
