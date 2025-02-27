package attendance.domain;

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

    public int size() {
        return attendanceBook.size();
    }
}
