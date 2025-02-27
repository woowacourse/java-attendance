package domain;

import java.util.SortedSet;
import java.util.TreeSet;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceRecordsTest {


    @DisplayName("출석 ststus의 개수를 파악해서 attendanceStatusCounts 필드에 저장한다")
    @Test
    void check() {
        // given
        final AttendanceStatusCounts expectedAttendanceStatusCounts = new AttendanceStatusCounts(1, 1, 1);
        final SortedSet<AttendanceRecord> attendanceRecordGroup = new TreeSet<>();
        final AttendanceDateTime dateTime1 = AttendanceDateTime.from("2024-12-20 10:00");
        final AttendanceDateTime dateTime2 = AttendanceDateTime.from("2024-12-23 13:09");
        final AttendanceDateTime dateTime3 = AttendanceDateTime.from("2024-12-24 11:09");
        final AttendanceRecord attendanceRecord1 = new AttendanceRecord(dateTime1);
        final AttendanceRecord attendanceRecord2 = new AttendanceRecord(dateTime2);
        final AttendanceRecord attendanceRecord3 = new AttendanceRecord(dateTime3);
        attendanceRecordGroup.add(attendanceRecord1);
        attendanceRecordGroup.add(attendanceRecord2);
        attendanceRecordGroup.add(attendanceRecord3);

        // when
        final AttendanceRecords attendanceRecords = new AttendanceRecords(attendanceRecordGroup);
        final AttendanceStatusCounts attendanceStatusCounts = attendanceRecords.getAttendanceStatusCounts();

        // then
        Assertions.assertThat(attendanceStatusCounts).isEqualTo(expectedAttendanceStatusCounts);
    }
}
