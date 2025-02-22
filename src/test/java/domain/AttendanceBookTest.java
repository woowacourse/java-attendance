package domain;

import static org.junit.jupiter.api.Assertions.*;

import dto.AttendanceCount;
import dto.AttendanceData;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map.Entry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceBookTest {

    @DisplayName("크루별 출석 상태를 확인할 수 있다.")
    @Test
    public void test1() {
        String name = "미미";
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.enter(name);
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 2, 13, 0));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 3, 9,58));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 4, 10,2));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 5, 10,6));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 6, 10,1));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 10, 10,8));

        AttendanceData attendanceData = attendanceBook.getAttendanceData(name, LocalDate.of(2024, 12, 12));
        AttendanceCount attendanceCount = Status.getCount(attendanceData);

        assertEquals(4, attendanceCount.attendanceCount());
    }

    @DisplayName("크루별 지각 상태를 확인할 수 있다.")
    @Test
    public void test2() {
        String name = "미미";
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.enter(name);
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 2, 13, 0));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 3, 9,58));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 4, 10,2));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 5, 10,6));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 6, 10,1));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 10, 10,8));

        AttendanceData attendanceData = attendanceBook.getAttendanceData(name, LocalDate.of(2024, 12, 12));
        AttendanceCount attendanceCount = Status.getCount(attendanceData);

        assertEquals(2, attendanceCount.lateCount());
    }

    @DisplayName("크루별 결석 상태를 확인할 수 있다.")
    @Test
    public void test3() {
        String name = "미미";
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.enter(name);
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 2, 13, 0));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 3, 9,58));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 4, 10,2));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 5, 10,6));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 6, 10,1));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 10, 10,8));

        AttendanceData attendanceData = attendanceBook.getAttendanceData(name, LocalDate.of(2024, 12, 12));
        AttendanceCount attendanceCount = Status.getCount(attendanceData);

        assertEquals(3, attendanceCount.absentCount());
    }

    @DisplayName("경고 대상자를 판별할 수 있다.")
    @Test
    public void test56() {
        String name = "미미";
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.enter(name);
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 2, 13, 0));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 3, 9,58));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 4, 10,2));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 5, 10,6));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 6, 10,1));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 9, 9,8));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 10, 10,8));

        AttendanceData attendanceData = attendanceBook.getAttendanceData(name, LocalDate.of(2024, 12, 12));
        AttendanceCount attendanceCount = Status.getCount(attendanceData);

        assertEquals(Penalty.경고, Penalty.from(attendanceCount.absentCount()));
    }

    @DisplayName("면담 대상자를 판별할 수 있다.")
    @Test
    public void test55() {
        String name = "미미";
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.enter(name);
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 2, 13, 0));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 3, 9,58));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 4, 10,2));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 5, 10,6));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 6, 10,1));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 10, 10,8));

        AttendanceData attendanceData = attendanceBook.getAttendanceData(name, LocalDate.of(2024, 12, 12));
        AttendanceCount attendanceCount = Status.getCount(attendanceData);

        assertEquals(Penalty.면담, Penalty.from(attendanceCount.absentCount()));
    }

    @DisplayName("제적 대상자를 판별할 수 있다.")
    @Test
    public void test57() {
        String name = "미미";
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.enter(name);
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 2, 13, 0));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 3, 9,58));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 4, 10,2));
        AttendanceData attendanceData = attendanceBook.getAttendanceData(name, LocalDate.of(2024, 12, 12));
        AttendanceCount attendanceCount = Status.getCount(attendanceData);

        assertEquals(Penalty.제적, Penalty.from(attendanceCount.absentCount()));
    }

    @DisplayName("(경고, 면담, 제적) 비대상자를 판별할 수 있다.")
    @Test
    public void test58() {
        String name = "미미";
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.enter(name);
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 2, 13, 0));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 3, 9,58));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 4, 10,2));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 5, 10,6));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 6, 10,1));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 9, 9,55));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 10, 10,8));
        attendanceBook.add(name, LocalDateTime.of(2024, 12, 11, 10,8));

        AttendanceData attendanceData = attendanceBook.getAttendanceData(name, LocalDate.of(2024, 12, 12));
        AttendanceCount attendanceCount = Status.getCount(attendanceData);

        assertEquals(Penalty.NONE, Penalty.from(attendanceCount.absentCount()));
    }

    @DisplayName("제적 위험자는 정렬하여 출력할 수 있다.")
    @Test
    void test8() {
        AttendanceBook attendanceBook = new AttendanceBook();
        String name1 = "빙티";
        attendanceBook.enter(name1);
        attendanceBook.add(name1, LocalDateTime.of(2024, 12, 2, 13, 0));
        attendanceBook.add(name1, LocalDateTime.of(2024, 12, 3, 9,58));
        attendanceBook.add(name1, LocalDateTime.of(2024, 12, 4, 10,2));
        attendanceBook.add(name1, LocalDateTime.of(2024, 12, 5, 10,6));
        attendanceBook.add(name1, LocalDateTime.of(2024, 12, 6, 10,1));
        attendanceBook.add(name1, LocalDateTime.of(2024, 12, 10, 10,8));
        String name2 = "이든";
        attendanceBook.enter(name2);
        attendanceBook.add(name2, LocalDateTime.of(2024, 12, 3, 10,7));
        attendanceBook.add(name2, LocalDateTime.of(2024, 12, 4, 10,8));
        attendanceBook.add(name2, LocalDateTime.of(2024, 12, 5, 10,29));
        attendanceBook.add(name2, LocalDateTime.of(2024, 12, 6, 10,6));
        attendanceBook.add(name2, LocalDateTime.of(2024, 12, 9, 10,31));
        attendanceBook.add(name2, LocalDateTime.of(2024, 12, 10, 10,2));
        attendanceBook.add(name2, LocalDateTime.of(2024, 12, 11, 10,1));

        List<Entry<String, AttendanceHistory>> sortedAttendanceBook = attendanceBook.getSorted();

        assertEquals(name2, sortedAttendanceBook.get(0).getKey());
        assertEquals(name1, sortedAttendanceBook.get(1).getKey());
    }
}
