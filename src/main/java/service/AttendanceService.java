package service;

import domain.AttendanceStorage;
import domain.Crew;
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
}
