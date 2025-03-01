package attendance.domain;

import attendance.util.ErrorMessage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AttendanceBook {

    private final Map<Crew, Attendances> attendanceBook;

    public AttendanceBook(Map<Crew, Attendances> attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    public void add(Crew crew, Attendance attendance) {
        if (attendanceBook.containsKey(crew)) {
            attendanceBook.get(crew).add(attendance);
            return;
        }

        attendanceBook.put(crew, new Attendances(new ArrayList<>(List.of(attendance))));
    }

    public Attendance findAttendanceByCrew(Crew crew, LocalDate inputDate) {
        return attendanceBook.get(crew).findByDate(inputDate);
    }

    public void update(Crew crew, Attendance oldAttendance, Attendance newAttendance) {
        Attendances attendances = attendanceBook.get(crew);
        attendances.update(oldAttendance, newAttendance);
    }

    public void validateCrew(Crew crew) {
        if (!attendanceBook.containsKey(crew)) {
            throw new IllegalArgumentException(ErrorMessage.CREW_NICKNAME_NOT_EXIST_ERROR.getMessage());
        }
    }
}
