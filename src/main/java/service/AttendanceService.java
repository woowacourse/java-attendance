package service;

import domain.AttendanceDateTime;
import domain.AttendanceHistory;
import domain.AttendanceStorage;
import domain.AttendanceType;
import domain.Crew;
import domain.PenaltyType;
import dto.AttendanceStatusDto;
import dto.AttendanceStatusesOfCrewDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AttendanceService {
    private final AttendanceStorage attendanceStorage;

    public AttendanceService(AttendanceStorage attendanceStorage) {
        this.attendanceStorage = attendanceStorage;
    }

    public void validateNicknameRegistered(String nickname) {
        if (!attendanceStorage.containsSameNickname(nickname)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
    }

    public void validateHistoryNotDuplicated(Crew crew, LocalDateTime localDateTime) {
        if (attendanceStorage.containsSameHistoryOf(crew, localDateTime)) {
            throw new IllegalArgumentException("해당 날짜에 출석 기록이 이미 존재합니다. 수정 기능을 이용해주세요.");
        }
    }

    public void validateIsSchoolDay(LocalDate localDate) {
        if (AttendanceDateTime.generateWithoutTimeFrom(localDate).isRestDay()) {
            throw new IllegalArgumentException(String.format("%d월 %d일 %s은 등교일이 아닙니다.",
                    localDate.getMonthValue(),
                    localDate.getDayOfMonth(),
                    localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)));
        }
    }

    public AttendanceStatusDto addAttendanceHistory(Crew crew, LocalDateTime localDateTime) {
        AttendanceHistory history = AttendanceHistory.of(crew, localDateTime);
        attendanceStorage.add(history);
        return AttendanceStatusDto.of(localDateTime, history.getAttendanceType());
    }

    public List<AttendanceStatusDto> replaceAttendanceHistory(Crew crew, LocalDateTime newLocalDateTime) {
        AttendanceHistory newHistory = AttendanceHistory.of(crew, newLocalDateTime);
        int replaceTargetIndex = attendanceStorage.indexOfSameDateAndCrew(newHistory);
        AttendanceHistory oldHistory = attendanceStorage.getAttendanceHistory(replaceTargetIndex);

        attendanceStorage.replace(newHistory);

        LocalDateTime oldLocalDateTime = oldHistory.getAttendanceDateTime().getLocalDateTime();
        AttendanceStatusDto oldStatusDto = AttendanceStatusDto.of(oldLocalDateTime, oldHistory.getAttendanceType());
        AttendanceStatusDto newStatusDto = AttendanceStatusDto.of(newLocalDateTime, newHistory.getAttendanceType());

        return List.of(oldStatusDto, newStatusDto);
    }

    public AttendanceStatusesOfCrewDto getAllHistories(Crew crew, int untilDay) {
        List<AttendanceStatusDto> attendanceStatusDtos = new ArrayList<>();
        Map<Integer, AttendanceHistory> historyForEachDay = getHistoryForEachDay(crew, untilDay);
        Map<AttendanceType, Integer> attendanceTypeCount = getAttendanceTypeCount(historyForEachDay, untilDay);

        for (int currentDay = 1; currentDay < untilDay; currentDay++) {
            AttendanceHistory currentDayHistory = historyForEachDay.get(currentDay);
            if (currentDayHistory.getAttendanceDateTime().isRestDay()) {
                continue;
            }
            attendanceStatusDtos.add(makeAttendanceStatus(currentDayHistory));
        }

        return new AttendanceStatusesOfCrewDto(attendanceStatusDtos, attendanceTypeCount, PenaltyType.getFrom(attendanceTypeCount));
    }

    public Map<Crew, Map<AttendanceType, Integer>> getAllAttendanceTypeCountOfCrew(int untilDay) {
        Set<Crew> registeredCrews = attendanceStorage.getCrews();
        Map<Crew, Map<AttendanceType, Integer>> attendanceTypeCountOfCrew = new HashMap<>();

        for (Crew crew : registeredCrews) {
            Map<Integer, AttendanceHistory> historyForEachDay = getHistoryForEachDay(crew, untilDay);
            Map<AttendanceType, Integer> attendanceTypeCount = getAttendanceTypeCount(historyForEachDay, untilDay);
            attendanceTypeCountOfCrew.put(crew, attendanceTypeCount);
        }

        return attendanceTypeCountOfCrew;
    }

    private AttendanceStatusDto makeAttendanceStatus(AttendanceHistory attendanceHistory) {
        if (attendanceHistory.isRecorded()) {
            return AttendanceStatusDto.of(
                    attendanceHistory.getAttendanceDateTime().getLocalDateTime(),
                    attendanceHistory.getAttendanceType()
            );
        }

        return AttendanceStatusDto.generateNotRecordedOf(attendanceHistory.getAttendanceDateTime().getLocalDateTime());
    }

    private Map<Integer, AttendanceHistory> getHistoryForEachDay(Crew crew, int untilDay) {
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


    private Map<AttendanceType, Integer> getAttendanceTypeCount(Map<Integer, AttendanceHistory> historyForEachDay, int untilDay) {
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
