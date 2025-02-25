import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.Attend;
import domain.AttendCount;
import domain.AttendStatus;
import domain.AttendanceBook;
import domain.AttendanceResult;
import domain.AttendanceResults;
import domain.Attends;
import domain.Current;
import domain.WarningCrew;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceBookTest {

    private static Stream<Arguments> provideDateAndTime() {
        return Stream.of(
                Arguments.of(LocalDate.of(2024, 12, 1), LocalTime.of(10, 0)),
                Arguments.of(LocalDate.of(2024, 12, 25), LocalTime.of(10, 0))
        );
    }

    private static Stream<Arguments> provideDayAndAttend() {
        return Stream.of(
                Arguments.of(12, Attend.of(LocalDate.of(2024, 12, 12), LocalTime.of(10, 0))),
                Arguments.of(13, Attend.fromDay(13))
        );
    }

    @Test
    @DisplayName("평일 출석 저장 테스트")
    void saveAttendTest() {
        //given
        var name = "플린트";
        var time = LocalTime.of(10, 0);
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.registerName(name);
        Attend attend = Attend.fromTime(time);

        //when
        attendanceBook.attend(name, attend);

        //then
        assertThat(attendanceBook.findByName(name).findByDay(Current.TODAY.getDay())).isEqualTo(attend);
    }

    @Test
    @DisplayName("이름이 존재하지 않는 크루 출석시 예외")
    void notExistNameTestInAttend() {
        //given
        var name = "플린트";
        var time = LocalTime.of(10, 0);
        AttendanceBook attendanceBook = new AttendanceBook();
        Attend attend = Attend.fromTime(time);

        //when & then
        assertThatThrownBy(
                () -> attendanceBook.attend(name, attend))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이름이 존재하지 않는 크루 수정 시 예외")
    void notExistNameTestInEdit() {
        //given
        var name = "플린트";
        var time = LocalTime.of(10, 0);
        AttendanceBook attendanceBook = new AttendanceBook();
        Attend attend = Attend.fromTime(time);

        //when & then
        assertThatThrownBy(
                () -> attendanceBook.edit(name, attend))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("provideDateAndTime")
    @DisplayName("쉬는 날 출석 저장 시도하면 예외")
    void tryAttendHoliday(LocalDate date, LocalTime time) {
        //given
        var name = "플린트";
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.registerName(name);
        Attend attend = Attend.of(date, time);

        //when & then
        assertThatThrownBy(() -> attendanceBook.attend(name, attend)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"07:59", "23:01"})
    @DisplayName("운영 시간 범위 외 시간을 입력했을 경우, 예외를 throw 한다.")
    void tryInputBeforeOpenTime(String time) {
        //given
        String name = "플린트";
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.registerName(name);
        Attend attend = Attend.of(LocalDate.of(2024, 12, 13), LocalTime.parse(time));

        //when & then
        assertThatThrownBy(() -> attendanceBook.attend(name, attend)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("존재하지 않는 출석 수정 테스트")
    void editNotContainedAttend() {
        //given
        var name = "플린트";
        var date = LocalDate.of(2024, 12, 13);
        var time = LocalTime.of(10, 11);
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.registerName(name);
        Attend attend = Attend.of(date, time);

        //when
        attendanceBook.edit(name, attend);

        //then
        assertThat(attendanceBook.findByName(name).findByDay(13)).isEqualTo(attend);
    }

    @Test
    @DisplayName("존재하는 출석 수정 테스트")
    void editContainedAttend() {
        //given
        var name = "플린트";
        Attend beforeAttend = Attend.of(LocalDate.of(2024, 12, 13), LocalTime.of(10, 0));
        Attend afterAttend = Attend.of(LocalDate.of(2024, 12, 13), LocalTime.of(10, 10));
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.registerName(name);
        attendanceBook.attend(name, beforeAttend);

        //when
        attendanceBook.edit(name, afterAttend);

        //then
        assertAll(
                () -> assertThat(attendanceBook.findByName(name).findByDay(13)).isNotEqualTo(beforeAttend),
                () -> assertThat(attendanceBook.findByName(name).findByDay(13)).isEqualTo(afterAttend)
        );
    }

    @Test
    @DisplayName("닉네임 대상의 어제까지의 출석 정보를 반환하는 기능")
    void shuold_return_Asdf() {
        // given
        String name = "플린트";
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.registerName(name);
        List<Attend> attendsInitValue = List.of(
                Attend.of(LocalDate.of(2024, 12, 2), LocalTime.of(13, 0)),
                Attend.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 7)),
                Attend.of(LocalDate.of(2024, 12, 4), LocalTime.of(10, 7)),
                Attend.of(LocalDate.of(2024, 12, 5), LocalTime.of(10, 7)),
                Attend.of(LocalDate.of(2024, 12, 6), LocalTime.of(10, 0)),
                Attend.of(LocalDate.of(2024, 12, 9), LocalTime.of(13, 0)),
                Attend.of(LocalDate.of(2024, 12, 10), LocalTime.of(13, 0)),
                Attend.of(LocalDate.of(2024, 12, 11), LocalTime.of(13, 0)),
                Attend.of(LocalDate.of(2024, 12, 12), LocalTime.of(13, 0)),
                Attend.of(LocalDate.of(2024, 12, 13), LocalTime.of(13, 0))
        );
        for (Attend attend : attendsInitValue) {
            attendanceBook.attend(name, attend);
        }

        // when
        List<Attend> result = attendanceBook.getAttends(name);

        // than
        List<Attend> expected = new ArrayList<>(attendsInitValue);
        expected.removeLast();
        assertThat(result).containsOnlyOnceElementsOf(expected);
    }

    @Test
    @DisplayName("닉네임 대상의 출석을 현재 날짜 이전까지 출력해야 한다.")
    void test3() {
        //given
        String name = "플린트";
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.registerName(name);
        List<Attend> expectAttend = List.of(
                Attend.of(LocalDate.of(2024, 12, 2), LocalTime.of(10, 0)),
                Attend.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0)),
                Attend.of(LocalDate.of(2024, 12, 4), LocalTime.of(10, 0)),
                Attend.of(LocalDate.of(2024, 12, 5), LocalTime.of(10, 6)),
                Attend.of(LocalDate.of(2024, 12, 6), LocalTime.of(10, 6)),
                Attend.of(LocalDate.of(2024, 12, 9), LocalTime.of(10, 6))
        );
        List<Attend> attends = new ArrayList<>(expectAttend);
        for (Attend attend : attends) {
            attendanceBook.attend(name, attend);
        }

        //when
        AttendanceResults result = attendanceBook.checkAttendance(name, Current.TODAY.getAttendUntilDay());

        //then
        List<AttendStatus> expectedStatus = List.of(AttendStatus.ATTEND, AttendStatus.ATTEND, AttendStatus.ATTEND,
                AttendStatus.LATE, AttendStatus.LATE, AttendStatus.LATE, AttendStatus.ABSENCE, AttendStatus.ABSENCE,
                AttendStatus.ABSENCE);
        List<AttendStatus> expected = result.getAttendanceResults()
                .stream()
                .map(AttendanceResult::attendStatus)
                .toList();
        assertThat(expected).containsExactlyInAnyOrderElementsOf(expectedStatus);
    }

    @Test
    @DisplayName("출석부에서 제적 위험자 조회 기능")
    void searchWarningCrew() {
        //given
        String name = "플린트";
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.registerName(name);
        List<Integer> days = Current.TODAY.getAttendUntilDay();
        List<Attend> expectAttend = List.of(
                Attend.of(LocalDate.of(2024, 12, 2), LocalTime.of(10, 0)),
                Attend.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 31)),
                Attend.of(LocalDate.of(2024, 12, 4), LocalTime.of(10, 6))
        );
        for (Attend attend : expectAttend) {
            attendanceBook.attend(name, attend);
        }

        //when
        List<WarningCrew> warningCrews = attendanceBook.checkWarningCrews(days);

        //then
        AttendCount expectedAttendCount = new AttendCount(1, 1, 7);
        WarningCrew expected = new WarningCrew(name, expectedAttendCount);
        assertThat(warningCrews).contains(expected);
    }

    @Test
    @DisplayName("출석부에서 제적 위험자 조회 기능- 맞는 대상만 잘 가져오는지")
    void searchWarningCrewOnlySatisfy() {
        //given
        List<Integer> days = Current.TODAY.getAttendUntilDay();
        String name = "플린트";
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.registerName(name);
        List<Attend> expectAttend = List.of(
                Attend.of(LocalDate.of(2024, 12, 2), LocalTime.of(10, 0)),
                Attend.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 31)),
                Attend.of(LocalDate.of(2024, 12, 4), LocalTime.of(10, 6))
        );
        for (Attend attend : expectAttend) {
            attendanceBook.attend(name, attend);
        }
        String secondName = "후유";
        attendanceBook.registerName(secondName);
        List<Attend> secondAttends = List.of(
                Attend.of(LocalDate.of(2024, 12, 2), LocalTime.of(10, 0)),
                Attend.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0)),
                Attend.of(LocalDate.of(2024, 12, 4), LocalTime.of(10, 0))
        );
        for (Attend attend : secondAttends) {
            attendanceBook.attend(secondName, attend);
        }

        //when
        List<WarningCrew> warningCrews = attendanceBook.checkWarningCrews(days);

        //then
        AttendCount expectedAttendCount = new AttendCount(1, 1, 7);
        WarningCrew expected = new WarningCrew(name, expectedAttendCount);
        assertThat(warningCrews).contains(expected);
    }

    @Test
    @DisplayName("출석부에서 제적 위험자 조회 기능- 대상자가 없을 때")
    void searchWarningCrewEmpty() {
        //given
        List<Integer> days = Current.TODAY.getAttendUntilDay();
        String name = "플린트";
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.registerName(name);
        List<Attend> expectAttend = List.of(
                Attend.of(LocalDate.of(2024, 12, 2), LocalTime.of(10, 0)),
                Attend.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0)),
                Attend.of(LocalDate.of(2024, 12, 4), LocalTime.of(10, 0)),
                Attend.of(LocalDate.of(2024, 12, 5), LocalTime.of(10, 0)),
                Attend.of(LocalDate.of(2024, 12, 6), LocalTime.of(10, 0)),
                Attend.of(LocalDate.of(2024, 12, 9), LocalTime.of(10, 0)),
                Attend.of(LocalDate.of(2024, 12, 10), LocalTime.of(10, 0)),
                Attend.of(LocalDate.of(2024, 12, 11), LocalTime.of(10, 0)),
                Attend.of(LocalDate.of(2024, 12, 12), LocalTime.of(10, 0))
        );
        for (Attend attend : expectAttend) {
            attendanceBook.attend(name, attend);
        }
        String secondName = "후유";
        attendanceBook.registerName(secondName);
        for (Attend attend : expectAttend) {
            attendanceBook.attend(secondName, attend);
        }

        //when
        List<WarningCrew> warningCrews = attendanceBook.checkWarningCrews(days);

        //then
        assertThat(warningCrews).isEmpty();
    }

    @Test
    @DisplayName("크루 중복 등록시 아무런 문제가 발생하지 않는다.")
    void registerSameCrew() {
        // given
        String name = "플린트";
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.registerName(name);
        Attend attend = Attend.of(LocalDate.of(2024, 12, 2), LocalTime.of(10, 0));
        attendanceBook.attend(name, attend);

        // when
        attendanceBook.registerName(name);
        Attends attends = attendanceBook.findByName(name);

        // then
        assertThat(attends.findByDay(2)).isEqualTo(attend);
    }

    @ParameterizedTest
    @MethodSource("provideDayAndAttend")
    @DisplayName("이름과 날짜를 기반으로 검색")
    void findByNameAndDay(int day, Attend attend) {
        //given
        String name = "플린트";
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.registerName(name);
        attendanceBook.attend(name, attend);

        //when
        Attend expected = attendanceBook.findByNameAndDay(name, day);

        //then
        assertThat(expected).isEqualTo(attend);
    }
}
