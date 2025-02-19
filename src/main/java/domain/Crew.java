package domain;

public class Crew {
    Nickname nickname;
    Attendances attendances;
    AttendanceCounter attendanceCounter;

    public Crew(final Nickname nickname, Attendances attendances, AttendanceCounter attendanceCounter) {
        this.nickname = nickname;
        this.attendances = attendances;
        this.attendanceCounter = attendanceCounter;
    }

    public Nickname getNickname() {
        return nickname;
    }
}
