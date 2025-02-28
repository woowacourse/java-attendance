package domain;

import java.time.LocalDateTime;

public class Coach {

    private final AttendanceBook attendanceBook;

    public Coach(AttendanceBook attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    public DailyRecord attendCrew(String name, LocalDateTime dateTime) {
        // TODO: 변환된 시간 반환
        return null;
    }

    private void validateOperatingTime() {

    }

    private void validateHoliday() {

    }
}