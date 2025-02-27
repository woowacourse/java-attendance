package service;

import domain.AttendanceHistory;
import domain.AttendanceStorage;
import domain.Crew;
import dto.AttendanceStatusDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AttendanceService {
    private final AttendanceStorage attendanceStorage;

    public AttendanceService(AttendanceStorage attendanceStorage) {
        this.attendanceStorage = attendanceStorage;
    }

    public boolean checkNicknameRegistered(String nickname) {
        return attendanceStorage.containsSameNickname(nickname);
    }

    public boolean checkHistoryAlreadyExists(Crew crew, LocalDateTime localDateTime) {
        return attendanceStorage.containsSameHistoryOf(crew, localDateTime);
    }

    public AttendanceStatusDto addAttendanceHistoryOf(Crew crew, LocalDateTime localDateTime) {
        AttendanceHistory history = AttendanceHistory.of(crew, localDateTime);
        attendanceStorage.add(history);
        return AttendanceStatusDto.of(localDateTime, history.getAttendanceType());
    }

    public List<AttendanceStatusDto> replaceAttendanceHistoryOf(Crew crew, LocalDateTime newLocalDateTime) {
        AttendanceHistory newHistory = AttendanceHistory.of(crew, newLocalDateTime);

        int replaceTargetIndex = attendanceStorage.indexOfSameDateAndCrew(newHistory);
        AttendanceHistory oldHistory = attendanceStorage.getAttendanceHistory(replaceTargetIndex);
        LocalDateTime oldLocalDateTime = oldHistory.getDateTime().getLocalDateTime();
        AttendanceStatusDto oldStatusDto = AttendanceStatusDto.of(oldLocalDateTime, oldHistory.getAttendanceType());

        attendanceStorage.replace(newHistory);

        AttendanceStatusDto newStatusDto = AttendanceStatusDto.of(newLocalDateTime, newHistory.getAttendanceType());

        return List.of(oldStatusDto, newStatusDto);
    }
}
