package service;

import repository.AttendanceRepository;
import repository.CrewRepository;
import service.dto.AttendanceModifyResponse;

public class AttendanceModifyService {
    private final CrewRepository crewRepository;
    private final AttendanceRepository attendanceRepository;

    public AttendanceModifyService(CrewRepository crewRepository, AttendanceRepository attendanceRepository) {
        this.crewRepository = crewRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public AttendanceModifyResponse modify(String name, int date, int hour, int minutes) {
        return null;
    }
}
