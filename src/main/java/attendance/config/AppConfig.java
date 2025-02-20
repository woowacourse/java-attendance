package attendance.config;

import attendance.controller.AttendanceController;
import attendance.domain.AttendanceManager;
import attendance.repository.AttendanceFileRepository;
import attendance.service.AttendanceManagerService;
import attendance.service.CrewDismissService;

public class AppConfig {
    private AttendanceManager attendanceManager() {
        return AttendanceManager.getInstance();
    }

    private AttendanceManagerService attendanceManagerService() {
        return new AttendanceManagerService(attendanceManager(), attendanceFileRepository());
    }

    private AttendanceFileRepository attendanceFileRepository() {
        return new AttendanceFileRepository();
    }

    public AttendanceController attendanceController() {
        return new AttendanceController(attendanceManagerService(), crewDismissService());
    }

    private CrewDismissService crewDismissService() {
        return new CrewDismissService(attendanceManager());
    }
}
