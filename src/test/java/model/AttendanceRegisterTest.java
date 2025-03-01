package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.model.AttendanceDate;
import attendance.model.AttendanceRecord;
import attendance.model.AttendanceRegister;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class AttendanceRegisterTest {

    @Test
    void 특정_크루의_출석기록을_조회한다() {
        // given
        AttendanceRegister attendanceRegister = new AttendanceRegister();
        attendanceRegister.addNewCrew("한스");
        AttendanceRecord attendanceRecord = attendanceRegister.findAttendanceRecordByName("한스");
        AttendanceDate attendanceDate = new AttendanceDate(2024, 12, 10);
        LocalTime attendanceTime = LocalTime.of(10, 5);
        attendanceRecord.attend(attendanceDate, attendanceTime);

        // when
        AttendanceRecord foundAttendanceRecord = attendanceRegister.findAttendanceRecordByName("한스");

        // then
        assertThat(foundAttendanceRecord.attendanceDateTimes().size()).isEqualTo(1);
    }

    @Test
    void 특정_크루의_출석_기록이_존재하지_않으면_예외가_발생한다() {
        // given
        AttendanceRegister attendanceRegister = new AttendanceRegister();
        attendanceRegister.addNewCrew("한스");
        AttendanceRecord attendanceRecord = attendanceRegister.findAttendanceRecordByName("한스");
        AttendanceDate attendanceDate = new AttendanceDate(2024, 12, 10);
        LocalTime attendanceTime = LocalTime.of(10, 5);
        attendanceRecord.attend(attendanceDate, attendanceTime);

        // when & then
        assertThatThrownBy(() -> attendanceRegister.findAttendanceRecordByName("빙티"));
    }

    @Test
    void 출석부에_새로운_크루를_추가한다() {
        // given
        AttendanceRegister attendanceRegister = new AttendanceRegister();

        // when
        attendanceRegister.addNewCrew("한스");

        // then
        assertThatCode(() -> attendanceRegister.findAttendanceRecordByName("한스"));
    }
}
