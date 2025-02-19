package service;

import controller.dto.AttendanceHistoryDto;
import controller.dto.AttendanceUpdateResultDto;
import domain.AttendanceDate;
import domain.AttendanceDateTime;
import domain.AttendanceHistories;
import domain.AttendanceHistory;
import domain.AttendanceType;
import domain.Crew;
import domain.Crews;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class AttendanceService {
    private final Crews crews;
    private final AttendanceHistories attendanceHistories;

    public AttendanceService(Crews crews, AttendanceHistories attendanceHistories) {
        this.crews = crews;
        this.attendanceHistories = attendanceHistories;
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

    public Map<Integer, AttendanceHistoryDto> checkAttendanceOf(String nickname, int day) {
        Crew crew = crews.findCrewBy(nickname);
        List<AttendanceHistory> foundHistories = attendanceHistories.findAllHistoriesOf(crew);

        // TODO: 변수명 생각 .. .
        Map<Integer, AttendanceHistoryDto> historyTemp = new HashMap<>();

        for (AttendanceHistory history : foundHistories) {
            historyTemp.put(history.getDay(),
                    AttendanceHistoryDto.from(history.getAttendanceDateTime()));
        }

        for (int currentDay = 1; currentDay < day; currentDay++) {
            if (AttendanceDate.isRestDay(currentDay)) {
                continue;
            }

            if (!historyTemp.containsKey(currentDay)) {
                AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(currentDay, 0, 0);
                historyTemp.put(currentDay, AttendanceHistoryDto.from(attendanceDateTime));
            }
        }

        return historyTemp;
    }
}
