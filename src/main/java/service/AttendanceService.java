package service;

import controller.dto.AttendanceHistoryDto;
import controller.dto.AttendanceHistoryWithPenaltyTypeDto;
import controller.dto.AttendanceRequestDto;
import controller.dto.AttendanceTypeCountDto;
import controller.dto.AttendanceUpdateResultDto;
import domain.AttendanceDate;
import domain.AttendanceDateTime;
import domain.AttendanceHistories;
import domain.AttendanceHistory;
import domain.AttendanceTypeCount;
import domain.Crew;
import domain.Crews;
import domain.PenaltyType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceService {
    private final Crews crews;
    private final AttendanceHistories attendanceHistories;

    public AttendanceService(Crews crews, AttendanceHistories attendanceHistories) {
        this.crews = crews;
        this.attendanceHistories = attendanceHistories;
    }

    public void saveCrews(List<String> names) {
        names.forEach(crews::add);
    }

    public void checkNicknameIsExisted(String nickname) {
        crews.findCrewBy(nickname);
    }

    public AttendanceHistoryDto applyAttendance(String nickname, AttendanceDateTime attendanceDateTime) {
        Crew crew = crews.findCrewBy(nickname);
        attendanceHistories.add(crew, attendanceDateTime);
        return AttendanceHistoryDto.from(attendanceDateTime);
    }

    public AttendanceUpdateResultDto editAttendance(String nickname, AttendanceDateTime newDateTime) {
        Crew crew = crews.findCrewBy(nickname);

        AttendanceHistory newAttendanceHistory = AttendanceHistory.of(crew, newDateTime);
        AttendanceHistory beforeAttendanceHistory = attendanceHistories.findHistoryBy(newAttendanceHistory);

        attendanceHistories.update(beforeAttendanceHistory, newAttendanceHistory);

        return AttendanceUpdateResultDto.from(beforeAttendanceHistory.getAttendanceDateTime(), newDateTime);
    }

    public AttendanceHistoryWithPenaltyTypeDto checkAttendanceOf(String nickname, int day) {
        Crew crew = crews.findCrewBy(nickname);
        List<AttendanceHistory> foundHistories = attendanceHistories.findAllHistoriesOf(crew);

        Map<Integer, AttendanceHistoryDto> historyDtoOfDay = new HashMap<>();
        AttendanceTypeCount attendanceTypeCount = AttendanceTypeCount.from(day, foundHistories);

        for (AttendanceHistory history : foundHistories) {
            historyDtoOfDay.put(history.getDay(), AttendanceHistoryDto.from(history.getAttendanceDateTime()));
        }

        for (int currentDay = 1; currentDay < day; currentDay++) {
            if (AttendanceDate.isRestDay(currentDay)) {
                continue;
            }

            if (!historyDtoOfDay.containsKey(currentDay)) {
                AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(currentDay, 0, 0);
                historyDtoOfDay.put(currentDay, AttendanceHistoryDto.from(attendanceDateTime));
            }
        }

        PenaltyType penaltyType = PenaltyType.getPenaltyType(attendanceTypeCount.getTotalAbsenceCount());

        return new AttendanceHistoryWithPenaltyTypeDto(historyDtoOfDay, penaltyType);
    }

    public List<AttendanceTypeCountDto> checkWarningCrew(int day) {
        List<AttendanceTypeCountDto> attendanceTypeCountDtos = new ArrayList<>();
        for (Crew crew : crews.getCrews()) {
            List<AttendanceHistory> beforeHistoriesOfCrew = attendanceHistories.findHistoriesBefore(crew, day);

            AttendanceTypeCount attendanceTypeCount = AttendanceTypeCount.from(day, beforeHistoriesOfCrew);
            PenaltyType penaltyType = PenaltyType.getPenaltyType(attendanceTypeCount.getTotalAbsenceCount());
            if (penaltyType == PenaltyType.NONE) {
                continue;
            }

            attendanceTypeCountDtos.add(AttendanceTypeCountDto.of(crew, attendanceTypeCount, penaltyType));
        }

        return attendanceTypeCountDtos;
    }

    public void initializeAttendanceHistories(List<AttendanceRequestDto> attendanceRequestDtos) {
        for (AttendanceRequestDto dto : attendanceRequestDtos) {
            String nickname = dto.nickname();

            Crew crew = crews.findCrewBy(nickname);
            AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(dto.day(), dto.attendanceTimeDto().hour(),
                    dto.attendanceTimeDto().minute());

            attendanceHistories.add(crew, attendanceDateTime);
        }
    }
}
