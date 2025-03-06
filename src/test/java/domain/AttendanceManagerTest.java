package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
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
        AttendanceManager attendanceManager = new AttendanceManager();
        NickName registedNickName = new NickName("후우");
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
        AttendanceManager attendanceManager = new AttendanceManager();
        NickName nickName = new NickName("후우");
        AttendanceRecord attendanceRecord = new AttendanceRecord(Current.getToday(), LocalTime.of(10, 0));

        // when & then
        assertAll(
                () -> assertThatThrownBy(() -> attendanceManager.attend(nickName, attendanceRecord))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> attendanceManager.edit(nickName, attendanceRecord))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> attendanceManager.checkAttendance(nickName, List.of(11)))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @ParameterizedTest
    @DisplayName("휴일 및 주말에는 출석을 등록하면 예외가 발생한다")
    @CsvSource(value = {"1", "7", "8", "14", "15", "21", "22", "25", "28", "29"})
    void should_throw_exception_when_attend_on_holiday_or_weekend(int date) {
        // given
        AttendanceManager attendanceManager = new AttendanceManager();
        NickName nickName = new NickName("후우");
        attendanceManager.register(nickName);
        AttendanceRecord attendanceRecord = new AttendanceRecord(Current.getToday()
                .withDayOfMonth(date), LocalTime.of(10, 0));

        // when & then
        assertAll(
                () -> assertThatThrownBy(() -> attendanceManager.attend(nickName, attendanceRecord))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> attendanceManager.edit(nickName, attendanceRecord))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @ParameterizedTest
    @DisplayName("캠퍼스 운영 시간 외 출석을 등록하면 예외가 발생한다")
    @CsvSource(value = {"07:59", "23:01"})
    void should_throw_exception_when_attend_out_of_campus_operating_time(String time) {
        // given
        AttendanceManager attendanceManager = new AttendanceManager();
        NickName nickName = new NickName("후우");
        attendanceManager.register(nickName);
        AttendanceRecord attendanceRecord = new AttendanceRecord(Current.getToday(), LocalTime.parse(time));

        // when & then
        assertAll(
                () -> assertThatThrownBy(() -> attendanceManager.attend(nickName, attendanceRecord))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> attendanceManager.edit(nickName, attendanceRecord))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Nested
    @DisplayName("출석 등록 테스트")
    class AttendTest {
        @Test
        @DisplayName("닉네임과 등교시간을 토대로 만들어진 출석 객체를 가지고 출석을 기록한다")
        void should_attend_by_nickname_and_attendanceRecord() {
            // given
            AttendanceManager attendanceManager = new AttendanceManager();
            NickName nickName = new NickName("후우");
            attendanceManager.register(nickName);
            AttendanceRecord attendanceRecord = new AttendanceRecord(Current.getToday(), LocalTime.of(10, 0));

            // when
            attendanceManager.attend(nickName, attendanceRecord);

            // then
            assertThat(attendanceManager).isNotEqualTo(new AttendanceManager());
        }

        @Test
        @DisplayName("오늘 출석을 한 경우 예외를 발생시킨다")
        void should_throw_exception_when_attend_twice() {
            // given
            AttendanceManager attendanceManager = new AttendanceManager();
            NickName nickName = new NickName("후우");
            attendanceManager.register(nickName);
            AttendanceRecord attendanceRecord = new AttendanceRecord(Current.getToday(), LocalTime.of(10, 0));
            attendanceManager.attend(nickName, attendanceRecord);

            // when & then
            assertThatThrownBy(() -> attendanceManager.attend(nickName, attendanceRecord))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("출석 수정 테스트")
    class EdieTest {
        @Test
        @DisplayName("닉네임, 수정할 날짜, 등교 시간을 가지고 출석을 수정한다")
        void should_edit_by_nickname_and_attendanceRecord_to_edit() {
            // given
            AttendanceManager attendanceManager = new AttendanceManager();
            NickName nickName = new NickName("후우");
            attendanceManager.register(nickName);
            AttendanceRecord attendanceRecord = new AttendanceRecord(Current.getToday(), LocalTime.of(10, 0));
            attendanceManager.attend(nickName, attendanceRecord);
            AttendanceRecord editAttendanceRecord = new AttendanceRecord(Current.getToday(), LocalTime.of(11, 0));
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
            AttendanceRecord attendanceRecord = new AttendanceRecord(Current.getToday(), LocalTime.of(10, 0));
            attendanceManager.attend(nickName, attendanceRecord);
            AttendanceRecord targetAttendanceRecord = new AttendanceRecord(Current.getToday(), LocalTime.of(18, 0));

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
            AttendanceRecord targetAttendanceRecord = new AttendanceRecord(Current.getToday(), LocalTime.of(18, 0));

            // when
            AttendanceRecord result = attendanceManager.getAttendanceRecordOfSameDate(nickName, targetAttendanceRecord);

            // then
            AttendanceRecord expected = AttendanceRecord.dateOf(Current.getDayOfToday());
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
            AttendanceManager attendanceManager = new AttendanceManager();
            NickName nickName = new NickName("후우");
            attendanceManager.register(nickName);
            AttendanceRecord attendanceRecord1 = new AttendanceRecord(Current.getToday()
                    .withDayOfMonth(9), LocalTime.of(10, 0));
            AttendanceRecord attendanceRecord2 = new AttendanceRecord(Current.getToday()
                    .withDayOfMonth(10), LocalTime.of(10, 0));
            attendanceManager.attend(nickName, attendanceRecord1);
            attendanceManager.attend(nickName, attendanceRecord2);
            // 2,3,4,5,6,9,10
            List<Integer> checkingDates = DateUtil.getAttendAbleDates(Current.getDayOfYesterday());

            // when
            Attendances attendances = attendanceManager.checkAttendance(nickName, checkingDates);

            // then
            assertAll(
                    () -> assertThat(attendances.getAttendances()).hasSize(checkingDates.size()),
                    () -> assertThat(attendances.getAttendances()).contains(
                            AttendanceRecord.dateOf(2),
                            attendanceRecord1,
                            attendanceRecord2
                    )
            );
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
                    new AttendanceRecord(Current.getToday()
                            .withDayOfMonth(2), LocalTime.of(10, 0)),
                    new AttendanceRecord(Current.getToday()
                            .withDayOfMonth(3), LocalTime.of(10, 0)),
                    new AttendanceRecord(Current.getToday()
                            .withDayOfMonth(4), LocalTime.of(10, 0)),
                    new AttendanceRecord(Current.getToday()
                            .withDayOfMonth(5), LocalTime.of(10, 0)),
                    new AttendanceRecord(Current.getToday()
                            .withDayOfMonth(6), LocalTime.of(10, 0)),
                    new AttendanceRecord(Current.getToday()
                            .withDayOfMonth(9), LocalTime.of(10, 0)),
                    new AttendanceRecord(Current.getToday()
                            .withDayOfMonth(10), LocalTime.of(10, 0))
            );
            AttendanceManager attendanceManager = new AttendanceManager();
            NickName expelName = new NickName("제적 학생");
            attendanceManager.register(expelName);
            for (int i = attends.size() - 1; i >= 6; --i) {
                attendanceManager.attend(expelName, attends.get(i));
            }
            NickName interviewName = new NickName("면담 학생");
            attendanceManager.register(interviewName);
            for (int i = attends.size() - 1; i >= 3; --i) {
                attendanceManager.attend(interviewName, attends.get(i));
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
