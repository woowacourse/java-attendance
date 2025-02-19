package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AttendanceCountTest {
    @Test
    void test1(){
        AttendanceCount attendanceCount = new AttendanceCount();
        Attandance attandance = new Attandance(LocalDateTime.of(2024, 12, 2, 13, 0));

        attendanceCount.calculateRecord(attandance);
        assertAll(
                () -> Assertions.assertEquals(1, attendanceCount.getPresent()),
                () -> Assertions.assertEquals(0, attendanceCount.getAbsent()),
                () -> Assertions.assertEquals(0, attendanceCount.getLate())
        );
    }

    @Test
    void test2() {
        AttendanceCount attendanceCount = new AttendanceCount();
        List<Attandance> testAttendances = new ArrayList<>();

        testAttendances.add(new Attandance(LocalDateTime.of(2024, 12, 2, 13, 6)));
        testAttendances.add(new Attandance(LocalDateTime.of(2024, 12, 3, 10,6)));
        testAttendances.add(new Attandance(LocalDateTime.of(2024, 12, 4, 10,  30)));
        testAttendances.add(new Attandance(LocalDateTime.of(2024, 12, 5, 13, 0)));
        testAttendances.add(new Attandance(LocalDateTime.of(2024, 12, 6, 10, 0)));

        for(Attandance attandance : testAttendances){
            attendanceCount.calculateRecord(attandance);
        }

        Assertions.assertEquals(AttendanceAlertLevel.CAUTION, attendanceCount.calculateAttendanceAlertLevel());
    }




}