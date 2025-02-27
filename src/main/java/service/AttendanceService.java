package service;

import domain.AttendanceDateTime;
import domain.AttendanceHistory;
import domain.AttendanceStorage;
import domain.Crew;
import dto.AttendanceStatusDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        LocalDateTime oldLocalDateTime = oldHistory.getAttendanceDateTime().getLocalDateTime();
        AttendanceStatusDto oldStatusDto = AttendanceStatusDto.of(oldLocalDateTime, oldHistory.getAttendanceType());

        attendanceStorage.replace(newHistory);

        AttendanceStatusDto newStatusDto = AttendanceStatusDto.of(newLocalDateTime, newHistory.getAttendanceType());

        return List.of(oldStatusDto, newStatusDto);
    }

    public List<AttendanceStatusDto> getHistoriesDtoFrom(Crew crew) {
        List<AttendanceStatusDto> attendanceStatusDtos = new ArrayList<>();
        Map<Integer, AttendanceHistory> historyOfDays = getAllHistoryOfDaysFrom(crew);
        for (int currentDay = 1; currentDay <= 31; currentDay++) {
            AttendanceHistory currentDayHistory = historyOfDays.get(currentDay);
            if (currentDayHistory.isRecorded()) {
                AttendanceStatusDto currentStatusDto = AttendanceStatusDto.of(
                        currentDayHistory.getAttendanceDateTime().getLocalDateTime(),
                        currentDayHistory.getAttendanceType()
                );
                attendanceStatusDtos.add(currentStatusDto);
                continue;
            }

            AttendanceStatusDto currentStatusDto = AttendanceStatusDto.generateNotRecordedOf(currentDayHistory.getAttendanceDateTime().getLocalDateTime());
            attendanceStatusDtos.add(currentStatusDto);
        }

        return attendanceStatusDtos;
    }

    private Map<Integer, AttendanceHistory> getAllHistoryOfDaysFrom(Crew crew) {
        List<AttendanceHistory> foundHistories = attendanceStorage.getAllHistoriesFrom(crew);
        Map<Integer, AttendanceHistory> historyOfDays = foundHistories.stream()
                .collect(
                        Collectors.toMap(
                                history -> history.getAttendanceDateTime().getLocalDateTime().getDayOfMonth(),
                                Function.identity()
                        )
                );

        // 비어있는 값 (결석) 채우기
        for (int currentDay = 1; currentDay <= 31; currentDay++) {
            if (historyOfDays.containsKey(currentDay)) {
                continue;
            }

            AttendanceDateTime notRecordedAttendance = AttendanceDateTime.generateNotRecordedAttendanceOf(2024, 12, currentDay);
            AttendanceHistory absenceHistory = AttendanceHistory.of(crew, notRecordedAttendance);
            historyOfDays.put(currentDay, absenceHistory);
        }

        return historyOfDays;
    }
}
