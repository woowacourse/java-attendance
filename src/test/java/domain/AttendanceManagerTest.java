package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Current;
import util.DateUtil;

public class AttendanceManagerTest {
    @Test
    @DisplayName("등록되지 않은 닉네임을 입력하면 예외가 발생한다")
    void should_throw_exception_when_not_registered_nickname() {
        // given
        NickName nickName = new NickName("후우");
        AttendanceRecord attendanceRecord = AttendanceRecord.of("11", "10:00");
        AttendanceManager attendanceManager = new AttendanceManager();

        // when & then
        assertAll(() -> assertThatThrownBy(() -> {
            attendanceManager.attend(nickName, attendanceRecord);
        }).isInstanceOf(IllegalArgumentException.class), () -> assertThatThrownBy(() -> {
            attendanceManager.isAttended(nickName, attendanceRecord);
        }).isInstanceOf(IllegalArgumentException.class), () -> assertThatThrownBy(() -> {
            attendanceManager.edit(nickName, attendanceRecord);
        }).isInstanceOf(IllegalArgumentException.class), () -> assertThatThrownBy(() -> {
            attendanceManager.checkAttendance(nickName, List.of(11));
        }).isInstanceOf(IllegalArgumentException.class));
    }

    @ParameterizedTest
    @DisplayName("휴일 및 주말에는 출석을 등록하면 예외가 발생한다")
    @CsvSource(value = {"1", "7", "8", "14", "15", "21", "22", "25", "28", "29"})
    void should_throw_exception_when_attend_on_holiday_or_weekend(String date) {
        // given
        NickName nickName = new NickName("후우");
        AttendanceRecord attendanceRecord = AttendanceRecord.of(date, "10:00");
        AttendanceManager attendanceManager = new AttendanceManager();
        attendanceManager.register(nickName);

        // when & then
        assertAll(() -> assertThatThrownBy(() -> {
            attendanceManager.attend(nickName, attendanceRecord);
        }).isInstanceOf(IllegalArgumentException.class), () -> assertThatThrownBy(() -> {
            attendanceManager.isAttended(nickName, attendanceRecord);
        }).isInstanceOf(IllegalArgumentException.class), () -> assertThatThrownBy(() -> {
            attendanceManager.edit(nickName, attendanceRecord);
        }).isInstanceOf(IllegalArgumentException.class));
    }

    @ParameterizedTest
    @DisplayName("캠퍼스 운영 시간 외 출석을 등록하면 예외가 발생한다")
    @CsvSource(value = {"07:59", "23:01"})
    void should_throw_exception_when_attend_out_of_campus_operating_time(String time) {
        // given
        NickName nickName = new NickName("후우");
        AttendanceRecord attendanceRecord = AttendanceRecord.of("10", time);
        AttendanceManager attendanceManager = new AttendanceManager();
        attendanceManager.register(nickName);

        // when & then
        assertAll(() -> assertThatThrownBy(() -> {
            attendanceManager.attend(nickName, attendanceRecord);
        }).isInstanceOf(IllegalArgumentException.class), () -> assertThatThrownBy(() -> {
            attendanceManager.isAttended(nickName, attendanceRecord);
        }).isInstanceOf(IllegalArgumentException.class), () -> assertThatThrownBy(() -> {
            attendanceManager.edit(nickName, attendanceRecord);
        }).isInstanceOf(IllegalArgumentException.class));
    }

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
            attendanceManager.register(nickName);

            // when
            attendanceManager.attend(nickName, attendanceRecord);

            // then
            assertThat(attendanceManager).isNotEqualTo(new AttendanceManager());
        }

        @ParameterizedTest
        @DisplayName("이름, 확인할 출석 기록으로 출석 기록과 동일한 날짜의 출석 기록이 존재하는지 확인할 수 있다")
        @CsvSource(value = {"11, 11, true", "10, 11, false"})
        void should_return_true_when_same_date_attended_attendanceRecord(String attendedDate, String checkDate,
                                                                         boolean expected) {
            // given
            NickName nickName = new NickName("후우");
            AttendanceManager attendanceManager = new AttendanceManager();
            attendanceManager.register(nickName);
            AttendanceRecord attendedAttendanceRecord = AttendanceRecord.of(attendedDate, "10:00");
            attendanceManager.attend(nickName, attendedAttendanceRecord);
            AttendanceRecord checkAttendanceRecord = AttendanceRecord.of(checkDate, "10:00");

            // when
            boolean result = attendanceManager.isAttended(nickName, checkAttendanceRecord);

            // then
            assertThat(result).isEqualTo(expected);
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
            attendanceManager.register(nickName);
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

    @Nested
    @DisplayName("크루별 출석 기록 확인 테스트")
    class CheckAttendanceTest {
        @Test
        @DisplayName("닉네임으로 전날 까지의 출석 기록을 확인한다")
        void should_return_attendances_by_nickname_and_dates() {
            // given
            NickName nickName = new NickName("후우");
            AttendanceManager attendanceManager = new AttendanceManager();
            attendanceManager.register(nickName);
            AttendanceRecord attendanceRecord1 = AttendanceRecord.of("9", "10:00");
            AttendanceRecord attendanceRecord2 = AttendanceRecord.of("10", "10:00");
            attendanceManager.attend(nickName, attendanceRecord1);
            attendanceManager.attend(nickName, attendanceRecord2);
            List<Integer> checkingDates = DateUtil.getAttendAbleDates(Current.getDayOfYesterday());

            // when
            Attendances attendances = attendanceManager.checkAttendance(nickName, checkingDates);

            // then
            assertThat(attendances).isNotEqualTo(new Attendances());
        }
    }
}
