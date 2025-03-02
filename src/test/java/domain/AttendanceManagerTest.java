package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
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
            AttendanceRecord attendanceRecord = new AttendanceRecord(LocalDate.parse("2024-12-11"),
                    LocalTime.parse(time));
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
            AttendanceRecord attendanceRecord = new AttendanceRecord(LocalDate.parse("2024-12-11"),
                    LocalTime.parse("10:00"));
            AttendanceManager attendanceManager = new AttendanceManager();
            attendanceManager.attend(nickName, attendanceRecord);

            // when
            boolean result = attendanceManager.isAttended(nickName);

            // then
            assertThat(result).isEqualTo(true);
        }
    }
}
