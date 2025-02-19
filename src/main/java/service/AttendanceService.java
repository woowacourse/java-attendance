package service;

import controller.dto.AttendanceHistoryDto;
import controller.dto.AttendanceUpdateResultDto;
import domain.AttendanceDateTime;
import domain.AttendanceHistories;
import domain.AttendanceHistory;
import domain.AttendanceTime;
import domain.Crew;
import domain.Crews;

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

    public AttendanceHistoryDto checkAttendance(String nickname, AttendanceDateTime attendanceDateTime) {
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
}
