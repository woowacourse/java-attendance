package service;

import domain.Attendance;
import domain.Crew;
import repository.AttendanceRepository;
import repository.CrewRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public class AttendanceCheckService {
    private final CrewRepository crewRepository;
    private final AttendanceRepository attendanceRepository;

    public AttendanceCheckService(CrewRepository crewRepository, AttendanceRepository attendanceRepository) {
        this.crewRepository = crewRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public Crew findCrew(String name) {
        Optional<Crew> crew = crewRepository.findByName(name);
        return crew.orElseThrow(IllegalArgumentException::new);
    }

    public Attendance register(Crew crew, LocalDateTime time) {
        for (Attendance attendance : attendanceRepository.findByCrew(crew)) {
            if (attendance.isSameDateWith(time)) {
                throw new IllegalArgumentException("이미 출석한 날짜입니다.");
            }
        }
        Attendance attendance = new Attendance(crew, time);
        attendanceRepository.save(attendance);
        return attendance;
    }
}

