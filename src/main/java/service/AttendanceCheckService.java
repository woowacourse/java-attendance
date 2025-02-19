package service;

import domain.Attendance;
import domain.Crew;
import exception.CrewNotExistException;
import exception.DuplicateAttendanceException;
import repository.AttendanceRepository;
import repository.CrewRepository;

import java.time.LocalDateTime;

public class AttendanceCheckService {
    private final CrewRepository crewRepository;
    private final AttendanceRepository attendanceRepository;

    public AttendanceCheckService(CrewRepository crewRepository, AttendanceRepository attendanceRepository) {
        this.crewRepository = crewRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public Crew findCrew(String name) {
        return crewRepository.findByName(name).orElseThrow(() -> new CrewNotExistException("존재하지 않는 크루입니다."));
    }

    public Attendance register(String name, LocalDateTime time) {
        Crew crew = findCrew(name);
        for (Attendance attendance : attendanceRepository.findByCrew(crew)) {
            if (attendance.isSameDateWith(time)) {
                throw new DuplicateAttendanceException("이미 출석한 날짜입니다.");
            }
        }
        Attendance attendance = new Attendance(crew, time);
        attendanceRepository.save(attendance);
        return attendance;
    }
}
