import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class AttendanceRecordTest {

    @Test
    void 출석하면_출석_시간을_추가한다() {
        String time = "09:59";

        AttendanceRecord attendanceRecord = new AttendanceRecord();
        LocalDateTime attendanceTime = attendanceRecord.attend(time);

        assertThat(attendanceTime).isEqualTo(LocalDateTime.of(
                Today.TODAY,
                LocalTime.of(
                        Integer.parseInt(time.split(":")[0]),
                        Integer.parseInt(time.split(":")[1])
                )));
    }

    @Test
    void 해당_날짜의_출석_지각_결석_여부를_판단한다() {
        // 00:00 ~ 10:05 출석
        // 10:06 ~ 10:30 지각
        // 10:31 ~ 23:59 결석
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        LocalDateTime attendanceTime = attendanceRecord.attend("09:59");

//        String status = attendanceRecord.getAttendanceStatus(attendanceTime.getDayOfMonth());
//        assertThat(status).isEqualTo("출석");
    }

    @Test
    void 해당_날짜의_출석_시간을_확인한다() {
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        LocalDateTime attendanceTime = attendanceRecord.attend("09:59");

        LocalDateTime targetAttendanceTime = attendanceRecord.findAttendanceTimeByDay(attendanceTime.getDayOfMonth());
        assertThat(targetAttendanceTime).isEqualTo(attendanceTime);
    }


    class AttendanceRecord {

        private List<LocalDateTime> attendanceTimes;

        public AttendanceRecord() {
            this.attendanceTimes = new ArrayList<>();
        }

        public LocalDateTime attend(String time) {
            LocalDateTime attendanceTime = LocalDateTime.of(
                    Today.TODAY,
                    LocalTime.of(
                            Integer.parseInt(time.split(":")[0]),
                            Integer.parseInt(time.split(":")[1])
                    ));
            attendanceTimes.add(attendanceTime);
            return attendanceTime;
        }

        public LocalDateTime findAttendanceTimeByDay(int dayOfMonth) {
            return attendanceTimes.stream()
                    .filter(time -> time.getDayOfMonth() == dayOfMonth)
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException("해당 날짜의 출석 시간이 없습니다."));
        }
    }
}
