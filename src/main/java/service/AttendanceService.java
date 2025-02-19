package service;

import domain.AttendanceDateTime;
import domain.AttendanceHistories;
import domain.Crew;
import domain.Crews;

public class AttendanceService {
    private final Crews crews;
    private final AttendanceHistories attendanceHistories;

    public AttendanceService(Crews crews, AttendanceHistories attendanceHistories) {
        this.crews = crews;
        this.attendanceHistories = attendanceHistories;
    }

    public void checkAttendance(String nickname, AttendanceDateTime attendanceDateTime) {
        Crew crew = crews.findCrewBy(nickname);
        attendanceHistories.add(crew, attendanceDateTime);
    }
}
