import domain.FixedDateProvider;
import exception.AppException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AttendanceApplicationTest {

    FixedDateProvider weekdayProvider = FixedDateProvider.of(LocalDate.of(2024, 12, 13));
    FixedDateProvider weekendProvider = FixedDateProvider.of(LocalDate.of(2024, 12, 14));

    @Test
    @DisplayName("등교일이 아닌 경우 예외 발샹")
    void weekendCheckInDateException() {
        //given
        InputStream in = new ByteArrayInputStream("1\n".getBytes());
        System.setIn(in);

        // when, then
        assertThatThrownBy(() -> runWeekend("1"))
                .isInstanceOf(AppException.class)
                .hasMessageContaining(AppException.PREFIX);
    }

    @Test
    @DisplayName("등록되지 않은 닉네임 입력할 경우 예외 발생")
    void noNameInAttendanceBookException() {
        //given
        InputStream in = new ByteArrayInputStream("1\n루피\n".getBytes());
        System.setIn(in);
        //when
        //then
        assertThatThrownBy(() -> runWeekday("1", "루피"))
                .isInstanceOf(AppException.class)
                .hasMessageContaining(AppException.PREFIX);
    }

    @Test
    @DisplayName("출석을 정상적으로 완료")
    void checkInTest() {
        // given:
        InputStream in = new ByteArrayInputStream("1\n짱수\n10:00\nQ\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // when
        runWeekday("1", "짱수", "10:00");

        // then
        String expectedOutput = "12월 13일 금요일 10:00 (출석)";
        assertThat(outContent.toString()).contains(expectedOutput);
    }

    @Test
    @DisplayName("수정을 정상적으로 완료")
    void modifyTest() {
        // given:
        InputStream in = new ByteArrayInputStream("2\n빙티\n3\n09:58\nQ\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // when
        runWeekday("2", "빙티", "3", "09:58");

        // then
        String expectedOutput = "12월 03일 화요일 10:07 (지각) -> 09:58 (출석) 수정 완료!";
        assertThat(outContent.toString()).contains(expectedOutput);
    }

    @Test
    @DisplayName("크루별 출석 기록을 정상적으로 확인")
    void viewCrewHistoryTest() {
        // given:
        InputStream in = new ByteArrayInputStream("3\n빙티\nQ\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // when
        runWeekday("3", "빙티");

        // then
        String expectedOutput = "12월 02일 월요일 13:00 (출석)\n" +
                "12월 03일 화요일 10:07 (지각)\n" +
                "12월 04일 수요일 10:02 (출석)\n" +
                "12월 05일 목요일 10:06 (지각)\n" +
                "12월 06일 금요일 10:01 (출석)\n" +
                "12월 09일 월요일 --:-- (결석)\n" +
                "12월 10일 화요일 10:08 (지각)\n" +
                "12월 11일 수요일 --:-- (결석)\n" +
                "12월 12일 목요일 --:-- (결석)";
        assertThat(outContent.toString()).contains(expectedOutput);
    }

    @Test
    @DisplayName("크루별 출결 횟수를 정상적으로 확인")
    void viewAttendanceStatusTest() {
        // given:
        InputStream in = new ByteArrayInputStream("3\n빙티\nQ\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // when
        runWeekday("3", "빙티");

        // then
        String expectedOutput = "출석: 3회\n" +
                "지각: 3회\n" +
                "결석: 3회";
        assertThat(outContent.toString()).contains(expectedOutput);
    }

    @Test
    @DisplayName("크루별 제적 위험 상태를 정상적으로 확인")
    void viewCrewPenaltyStatusTest() {
        // given:
        InputStream in = new ByteArrayInputStream("3\n빙티\nQ\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // when
        runWeekday("3", "빙티");

        // then
        String expectedOutput = "면담 대상자입니다.";
        assertThat(outContent.toString()).contains(expectedOutput);
    }

    @Test
    @DisplayName("제적위험자들을 정상적으로 확인")
    void viewDangerCrewsTest() {
        // given:
        InputStream in = new ByteArrayInputStream("4\nQ\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // when
        runWeekday("4");

        // then
        String expectedOutput = "제적 위험자 조회 결과\n" +
                "- 빙티: 결석 3회, 지각 3회 (면담)\n" +
                "- 이든: 결석 2회, 지각 4회 (면담)\n" +
                "- 빙봉: 결석 1회, 지각 5회 (경고)\n" +
                "- 쿠키: 결석 2회, 지각 2회 (경고)";
        assertThat(outContent.toString()).contains(expectedOutput);
    }


    private void runWeekday(String... args) {
        AttendanceApplication.main(args, weekdayProvider);
    }

    private void runWeekend(String... args) {
        AttendanceApplication.main(args, weekendProvider);
    }
}