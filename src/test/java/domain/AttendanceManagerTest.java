package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AttendanceManagerTest {
    @Nested
    @DisplayName("출석 등록 테스트")
    class AttendTest {
        @Test
        @DisplayName("닉네임과 등교시간을 토대로 만들어진 출석 객체를 가지고 출석을 기록한다")
        void should_attend_by_nickname_and_attendanceRecord() {
            // given
            NickName nickName = new NickName("후우");
            String time = "10:00";
            AttendanceRecord attendanceRecord = AttendanceRecord.timeOf(time);
            AttendanceManager attendanceManager = new AttendanceManager();

            // when
            attendanceManager.attend(nickName, attendanceRecord);

            // then
            assertThat(attendanceManager).isNotEqualTo(new AttendanceManager());
        }

        @Test
        @DisplayName("이미 출석한 경우를 알 수 있다")
        void should_return_true_when_already_attended() {
            // given
            NickName nickName = new NickName("후우");
            AttendanceRecord attendanceRecord = AttendanceRecord.of("11", "10:00");
            AttendanceManager attendanceManager = new AttendanceManager();
            attendanceManager.attend(nickName, attendanceRecord);

            // when
            boolean result = attendanceManager.isAttended(nickName);

            // then
            assertThat(result).isEqualTo(true);
        }
    }

    @Nested
    @DisplayName("출석 수정 테스트")
    class EdieTest {
        @Test
        @DisplayName("닉네임, 수정할 날짜, 등교 시간을 가지고 출석을 수정한다")
        void should_edit_by_nickname_and_attendanceRecord_to_edit() {
            // given
            NickName nickName = new NickName("후우");
            AttendanceManager attendanceManager = new AttendanceManager();
            AttendanceRecord attendanceRecord = AttendanceRecord.of("11", "10:00");
            attendanceManager.attend(nickName, attendanceRecord);
            AttendanceRecord editAttendanceRecord = AttendanceRecord.of("11", "11:00");
            int prevHash = attendanceManager.hashCode();

            // when
            attendanceManager.edit(nickName, editAttendanceRecord);

            // then
            assertThat(attendanceManager.hashCode()).isNotEqualTo(prevHash);
        }
    }
}
