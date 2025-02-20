import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {

    @Test
    void 평일_출석_저장_테스트() throws Exception {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        var name = "플린트";
        var time = "09:59";
        Attend attend = Attend.of(time);

        //when
        attendanceBook.attend(name, attend);

        //then
        Assertions.assertThat(attendanceBook.findByName(name).attends).hasSize(1);
    }

    @Test
    void 주말_출석_저장_시도하면_예외() throws Exception {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        var name = "플린트";
        var date = "14";
        var time = "09:59";
        Attend attend = Attend.of(date, time);

        //when & then
        assertThatThrownBy(() -> attendanceBook.attend(name, attend)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 공휴일_출석_저장_시도하면_예외() throws Exception {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        var name = "플린트";
        var date = "25";
        var time = "09:59";
        Attend attend = Attend.of(date, time);

        //when & then
        assertThatThrownBy(() -> attendanceBook.attend(name, attend)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("운영 시간 전에 시간을 입력했을 경우, 예외를 throw 한다.")
    void test() throws Exception {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        String name = "플린트";
        String day = "13";
        String time = "07:59";
        Attend attend = Attend.of(day, time);

        //when & then
        assertThatThrownBy(() -> attendanceBook.attend(name, attend)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("운영 시간 이후에 시간을 입력했을 경우, 예외를 throw 한다.")
    void test2() throws Exception {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        String name = "플린트";
        String day = "13";
        String time = "23:01";
        Attend attend = Attend.of(day, time);

        //when & then
        assertThatThrownBy(() -> attendanceBook.attend(name, attend)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 존재하지_않는_출석_수정_테스트() throws Exception {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        var name = "플린트";
        var date = "13";
        var time = "10:11";
        Attend attend = Attend.of(date, time);

        //when
        attendanceBook.edit(name, attend);

        //then
        Assertions.assertThat(attendanceBook.findByName(name).attends).contains(attend);
    }

    @Test
    void 존재하는_출석_수정_테스트() throws Exception {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        var name = "플린트";
        Attend beforeAttend = Attend.of("13", "10:00");
        Attend afterAttend = Attend.of("13", "10:10");
        attendanceBook.attend(name, beforeAttend);

        //when
        attendanceBook.edit(name, afterAttend);

        //then
        Assertions.assertThat(attendanceBook.findByName(name).attends).contains(afterAttend);
    }

    @Test
    @DisplayName("닉네임 대상의 어제까지의 출석 정보를 반환하는 기능")
    void shuold_return_Asdf() {
        // given
        String name = "플린트";
        AttendanceBook attendanceBook = new AttendanceBook();
        List<Attend> attendsInitValue = List.of(Attend.of("2", "13:00"), Attend.of("3", "10:07"),
                Attend.of("4", "13:00"), Attend.of("5", "13:00"), Attend.of("6", "13:00"), Attend.of("9", "13:00"),
                Attend.of("10", "13:00"), Attend.of("11", "13:00"), Attend.of("12", "13:00"), Attend.of("13", "13:00"));
        for (Attend attend : attendsInitValue) {
            attendanceBook.attend(name, attend);
        }

        // when
        List<Attend> result = attendanceBook.getAttends(name);

        // than
        List<Attend> expected = new ArrayList<>(attendsInitValue);
        expected.removeLast();
        assertThat(result).containsOnlyElementsOf(expected);
    }

    @Test
    @DisplayName("닉네임 대상의 출석을 현재 날짜 이전까지 출력해야 한다.")
    void test3() {
        //given
        String name = "플린트";
        AttendanceBook attendanceBook = new AttendanceBook();
        List<Attend> expectAttend = List.of(Attend.of("2", "10:00"), Attend.of("3", "10:06"), Attend.of("4", "10:31"));
        List<AttendStatus> expectedStatus = List.of(AttendStatus.ATTEND, AttendStatus.LATE, AttendStatus.ABSENCE);
        List<Attend> attends = new ArrayList<>(expectAttend);
        for (Attend attend : attends) {
            attendanceBook.attend(name, attend);
        }

        //when
        AttendanceResults result = attendanceBook.checkAttendance(name, DateUtil.getAttendUntilDay(4));

        //then
        List<AttendanceResult> expected = List.of(new AttendanceResult(expectAttend.get(0), expectedStatus.get(0)),
                new AttendanceResult(expectAttend.get(1), expectedStatus.get(1)),
                new AttendanceResult(expectAttend.get(2), expectedStatus.get(2)));
        Assertions.assertThat(result.getAttendanceResults()).containsOnlyElementsOf(expected);
    }

    @Test
    @DisplayName("닉네임 대상의 출석을 현재 날짜 이전까지 출력해야 한다.")
    void test4() {
        //given
        String name = "플린트";
        AttendanceBook attendanceBook = new AttendanceBook();
        List<Attend> expectAttend = List.of(Attend.of("2", "10:00"), Attend.of("3", "10:06"), Attend.of("4", "10:31"));
        List<Attend> attends = new ArrayList<>(expectAttend);
        for (Attend attend : attends) {
            attendanceBook.attend(name, attend);
        }
        List<Integer> days = DateUtil.getAttendUntilDay(5);

        //when
        AttendanceResults result = attendanceBook.checkAttendance(name, days);

        //then
        assertAll(
                () -> Assertions.assertThat(result.getAttendanceResults().get(3).attend()).isEqualTo(Attend.fromDay(5)),
                () -> Assertions.assertThat(result.getAttendanceResults().get(3).attendStatus())
                        .isEqualTo(AttendStatus.ABSENCE));
    }

    }
}
