package service;

import domain.AttendanceHistory;
import domain.AttendanceStorage;
import domain.Crew;
import dto.AttendanceStatusDto;
import java.time.LocalDateTime;

public class AttendanceService {
    private final AttendanceStorage attendanceStorage;

    public AttendanceService(AttendanceStorage attendanceStorage) {
        this.attendanceStorage = attendanceStorage;
    }

    public boolean checkNicknameRegistered(String nickname) {
        return attendanceStorage.containsSameNickname(nickname);
    }

    public boolean checkHistoryAlreadyExists(Crew crew, LocalDateTime dateTime) {
        return attendanceStorage.containsSameHistoryOf(crew, dateTime);
    }

    public AttendanceStatusDto addAttendanceOf(Crew crew, LocalDateTime dateTime) {
        AttendanceHistory history = AttendanceHistory.of(crew, dateTime);
        attendanceStorage.add(history);
        return new AttendanceStatusDto(
                dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getDayOfWeek(),
                dateTime.getHour(),
                dateTime.getMinute(),
                history.getAttendanceType()
        );
    }
}
