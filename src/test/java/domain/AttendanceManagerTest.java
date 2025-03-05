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
    @ParameterizedTest
    @DisplayName("닉네임이 등록되었는지 확인한다")
    @CsvSource(value = {"후우, true", "마후우, false"})
    void should_return_true_when_registered_nickname(NickName nickName, boolean expected) {
        // given
        NickName registedNickName = new NickName("후우");
        AttendanceManager attendanceManager = new AttendanceManager();
        attendanceManager.register(registedNickName);

        // when
        boolean result = attendanceManager.isRegistered(nickName);

        // then
        assertThat(result).isEqualTo(expected);
    }

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

        @Test
        @DisplayName("출석 기록과 동일한 날짜의 출석 기록을 가져온다")
        void should_return_attendanceRecord_of_same_date() {
            // given
            AttendanceManager attendanceManager = new AttendanceManager();
            NickName nickName = new NickName("후우");
            attendanceManager.register(nickName);
            AttendanceRecord attendanceRecord = AttendanceRecord.of("2", "10:00");
            attendanceManager.attend(nickName, attendanceRecord);

            AttendanceRecord targetAttendanceRecord = AttendanceRecord.of("2", "18:00");

            // when
            AttendanceRecord result = attendanceManager.getAttendanceRecordOfSameDate(nickName, targetAttendanceRecord);

            // then
            assertThat(result).isEqualTo(attendanceRecord);
        }

        @Test
        @DisplayName("출석 기록과 동일한 날짜의 출석 기록이 없다면 생성해 가져온다")
        void should_create_and_return_attendanceRecord_of_same_date() {
            // given
            AttendanceManager attendanceManager = new AttendanceManager();
            NickName nickName = new NickName("후우");
            attendanceManager.register(nickName);
            AttendanceRecord targetAttendanceRecord = AttendanceRecord.of("2", "18:00");

            // when
            AttendanceRecord result = attendanceManager.getAttendanceRecordOfSameDate(nickName, targetAttendanceRecord);

            // then
            AttendanceRecord expected = AttendanceRecord.dateOf(2);
            assertThat(result).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("크루별 출석 기록 확인 테스트")
    class CheckAttendanceTest {
        @Test
        @DisplayName("닉네임과 주어진 날짜들로 해당 날짜들의 출석 기록을 확인한다")
        void should_return_attendances_by_nickname_and_dates() {
            // given
            NickName nickName = new NickName("후우");
            AttendanceManager attendanceManager = new AttendanceManager();
            attendanceManager.register(nickName);
            AttendanceRecord attendanceRecord1 = AttendanceRecord.of("9", "10:00");
            AttendanceRecord attendanceRecord2 = AttendanceRecord.of("10", "10:00");
            attendanceManager.attend(nickName, attendanceRecord1);
            attendanceManager.attend(nickName, attendanceRecord2);
            // 2,3,4,5,6,9,10
            List<Integer> checkingDates = DateUtil.getAttendAbleDates(Current.getDayOfYesterday());

            // when
            Attendances attendances = attendanceManager.checkAttendance(nickName, checkingDates);

            // then
            assertAll(() -> assertThat(attendances.getAttendances()).hasSize(checkingDates.size()),
                    () -> assertThat(attendances.getAttendances()).contains(AttendanceRecord.dateOf(2),
                            attendanceRecord1, attendanceRecord2));
        }
    }

    @Nested
    @DisplayName("제적 위험자 확인 테스트")
    class FindWarningCrewTest {
        @Test
        @DisplayName("주어진 날짜들로 제적 위험자를 확인한다")
        void should_return_warningCrews_by_dates() {
            // given
            List<AttendanceRecord> attends = List.of(
                    AttendanceRecord.of("2", "10:00"),
                    AttendanceRecord.of("3", "10:00"),
                    AttendanceRecord.of("4", "10:00"),
                    AttendanceRecord.of("5", "10:00"),
                    AttendanceRecord.of("6", "10:00"),
                    AttendanceRecord.of("9", "10:00"),
                    AttendanceRecord.of("10", "10:00"));
            AttendanceManager attendanceManager = new AttendanceManager();
            NickName expelName = new NickName("제적 학생");
            attendanceManager.register(expelName);
            for (int i = attends.size() - 1; i >= 6; --i) {
                attendanceManager.attend(expelName, attends.get(i));
            }
            NickName interviewName = new NickName("면담 학생");
            attendanceManager.register(interviewName);
            for (int i = attends.size() - 1; i >= 3; --i) {
                attendanceManager.attend(expelName, attends.get(i));
            }
            NickName warningName = new NickName("경고 학생");
            attendanceManager.register(warningName);
            for (int i = attends.size() - 1; i >= 2; --i) {
                attendanceManager.attend(warningName, attends.get(i));
            }
            NickName clearName = new NickName("성실 학생");
            attendanceManager.register(clearName);
            for (int i = attends.size() - 1; i >= 0; --i) {
                attendanceManager.attend(clearName, attends.get(i));
            }
            // 2,3,4,5,6,9,10
            List<Integer> checkingDates = DateUtil.getAttendAbleDates(11);

            // when
            WarningCrews warningCrews = attendanceManager.findWarningCrews(checkingDates);

            // then
            assertThat(warningCrews.getWarningCrews()).hasSize(3);
        }
    }
}
