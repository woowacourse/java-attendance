package service;

import domain.Attendance;
import domain.Crew;
import exception.AttendanceNotExistException;
import exception.CrewNotExistException;
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

    public AttendanceModifyResponse modify(String name, int date, int newHour, int newMinutes) {
        //TODO : customexception은 예외메세지 클래스 안에 넣어주기
        Crew crew = crewRepository.findByName(name).orElseThrow(() -> new CrewNotExistException("존재하지 않는 크루입니다."));
        Attendance beforeAttendance = attendanceRepository.findByCrewAndDate(crew, date)
                .orElseThrow(AttendanceNotExistException::new);
        Attendance afterAttendance = beforeAttendance.modify(newHour, newMinutes);
        attendanceRepository.replace(beforeAttendance, afterAttendance);
        return new AttendanceModifyResponse(
                beforeAttendance.getTime(),
                beforeAttendance.getStatus(),
                afterAttendance.getTime(),
                afterAttendance.getStatus()
        );
    }
}
