package service;

import domain.AttendanceDateTime;
import domain.AttendanceHistory;
import domain.AttendanceStorage;
import domain.AttendanceType;
import domain.Crew;
import domain.PenaltyType;
import dto.AttendanceStatusDto;
import dto.AttendanceStatusesOfCrewDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    public AttendanceStatusesOfCrewDto getHistoriesDtoFrom(Crew crew, int untilDay) {
        List<AttendanceStatusDto> attendanceStatusDtos = new ArrayList<>();
        Map<Integer, AttendanceHistory> historyOfDays = getHistoryForEachDayOf(crew, untilDay);
        Map<AttendanceType, Integer> attendanceTypeCount = new HashMap<>();

        for (int currentDay = 1; currentDay < untilDay; currentDay++) {
            AttendanceHistory currentDayHistory = historyOfDays.get(currentDay);
            if (currentDayHistory.getAttendanceDateTime().isRestDay()) {
                continue;
            }
            attendanceTypeCount.merge(currentDayHistory.getAttendanceType(), 1, Integer::sum);

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

        return new AttendanceStatusesOfCrewDto(attendanceStatusDtos, attendanceTypeCount, PenaltyType.getFrom(attendanceTypeCount));
    }

    private Map<Integer, AttendanceHistory> getHistoryForEachDayOf(Crew crew, int untilDay) {
        List<AttendanceHistory> foundHistories = attendanceStorage.getAllHistoriesOf(crew, untilDay);
        Map<Integer, AttendanceHistory> historyForEachDay = foundHistories.stream()
                .collect(
                        Collectors.toMap(
                                history -> history.getAttendanceDateTime().getLocalDateTime().getDayOfMonth(),
                                Function.identity()
                        )
                );

        // 비어있는 값 (결석) 채우기
        for (int currentDay = 1; currentDay < untilDay; currentDay++) {
            if (historyForEachDay.containsKey(currentDay)) {
                continue;
            }
            AttendanceDateTime notRecordedAttendance = AttendanceDateTime.generateNotRecordedAttendanceOf(2024, 12, currentDay);
            AttendanceHistory absenceHistory = AttendanceHistory.of(crew, notRecordedAttendance);
            historyForEachDay.put(currentDay, absenceHistory);
        }

        return historyForEachDay;
    }

    public Map<Crew, Map<AttendanceType, Integer>> getAllAttendanceTypeCountOfCrew(int untilDay) {
        Set<Crew> registeredCrews = attendanceStorage.getCrews();
        Map<Crew, Map<AttendanceType, Integer>> attendanceTypeCountOfCrew = new HashMap<>();
        for (Crew crew : registeredCrews) {
            Map<Integer, AttendanceHistory> historyForEachDay = getHistoryForEachDayOf(crew, untilDay);
            Map<AttendanceType, Integer> attendanceTypeCount = getAttendanceTypeCountFrom(historyForEachDay, untilDay);
            attendanceTypeCountOfCrew.put(crew, attendanceTypeCount);
        }

        return attendanceTypeCountOfCrew;
    }

    private Map<AttendanceType, Integer> getAttendanceTypeCountFrom(Map<Integer, AttendanceHistory> historyForEachDay, int untilDay) {
        Map<AttendanceType, Integer> attendanceTypeCount = new HashMap<>();

        for (int currentDay = 1; currentDay < untilDay; currentDay++) {
            AttendanceHistory currentDayHistory = historyForEachDay.get(currentDay);
            if (currentDayHistory.getAttendanceDateTime().isRestDay()) {
                continue;
            }
            attendanceTypeCount.merge(currentDayHistory.getAttendanceType(), 1, Integer::sum);
        }

        return attendanceTypeCount;
    }
}
