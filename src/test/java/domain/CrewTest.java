package domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewTest {
    @DisplayName("정상 출석")
    @Test
    void test1() {
        Crew crew = new Crew("띠용");

        assertEquals("12월 05일 목요일 09:59 (출석)", crew.addAttendance(LocalDateTime.of(2024, 12, 5, 9, 59)));
    }
    @DisplayName("지각")
    @Test
    void test2() {
        Crew crew = new Crew("띠용");

        assertEquals("12월 05일 목요일 10:06 (지각)", crew.addAttendance(LocalDateTime.of(2024, 12, 5, 10, 6)));
    }
    @DisplayName("결석")
    @Test
    void test3() {
        Crew crew = new Crew("띠용");

        assertEquals("12월 05일 목요일 --:-- (결석)", crew.addAttendance(LocalDateTime.of(2024, 12, 5, 10, 31)));
    }
    @DisplayName("중복 출석 시도")
    @Test
    void test4() {
        Crew crew = new Crew("띠용");
        crew.addAttendance(LocalDateTime.of(2024, 12, 5, 8, 59));
        assertThatThrownBy(() -> crew.addAttendance(LocalDateTime.of(2024, 12, 5, 9, 59)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("출석 수정")
    @Test
    void test5() {
        Crew crew = new Crew("띠용");
        crew.addAttendance(LocalDateTime.of(2024, 12, 5, 9, 55));

        assertEquals("12월 05일 목요일 09:55 (출석) -> 10:06 (지각) 수정 완료!",
                crew.update(LocalDateTime.of(2024, 12, 5, 10, 6)));
    }

    @DisplayName("출석 수정 예외")
    @Test
    void test6() {
        Crew crew = new Crew("띠용");

        assertThatThrownBy(() -> crew.update(LocalDateTime.of(2024, 12, 5, 10, 6)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    // Todo : 테스트 시나리오 다양화

    @DisplayName("특정 크루 출석기록 출력하기")
    @Test
    void test11() {
        Crew crew = new Crew("미미");
        crew.addAttendance(LocalDateTime.of(2024, 12, 2, 13,0));
        crew.addAttendance(LocalDateTime.of(2024, 12, 3, 10,7));
        assertThat(crew.getAttendanceInfo(LocalDate.of(2024, 12, 3))).isEqualTo("12월 02일 월요일 13:00 (출석)\n12월 03일 화요일 10:07 (지각)\n");
    }

    @DisplayName("특정 크루 빈 출석 기록 포함 출력하기")
    @Test
    void test22() {
        Crew crew = new Crew("미미");
        crew.addAttendance(LocalDateTime.of(2024, 12, 2, 13,0));
        crew.addAttendance(LocalDateTime.of(2024, 12, 3, 10,7));
        crew.addAttendance(LocalDateTime.of(2024, 12, 5, 10,7));
        assertThat(crew.getAttendanceInfo(LocalDate.of(2024, 12, 5))).isEqualTo("12월 02일 월요일 13:00 (출석)\n12월 03일 화요일 10:07 (지각)\n12월 04일 수요일 --:-- (결석)\n12월 05일 목요일 10:07 (지각)\n");
    }

    @DisplayName("특정 크루 빈 출석 기록 포함 출력하기2")
    @Test
    void test33() {
        Crew crew = new Crew("미미");
        crew.addAttendance(LocalDateTime.of(2024, 12, 2, 13,0));
        crew.addAttendance(LocalDateTime.of(2024, 12, 3, 9,58));
        crew.addAttendance(LocalDateTime.of(2024, 12, 4, 10,2));
        crew.addAttendance(LocalDateTime.of(2024, 12, 5, 10,6));
        crew.addAttendance(LocalDateTime.of(2024, 12, 6, 10,1));
        crew.addAttendance(LocalDateTime.of(2024, 12, 10, 10,8));
        assertThat(crew.getAttendanceInfo(LocalDate.of(2024, 12, 12))).isEqualTo("12월 02일 월요일 13:00 (출석)\n"
                + "12월 03일 화요일 09:58 (출석)\n"
                + "12월 04일 수요일 10:02 (출석)\n"
                + "12월 05일 목요일 10:06 (지각)\n"
                + "12월 06일 금요일 10:01 (출석)\n"
                + "12월 09일 월요일 --:-- (결석)\n"
                + "12월 10일 화요일 10:08 (지각)\n"
                + "12월 11일 수요일 --:-- (결석)\n"
                + "12월 12일 목요일 --:-- (결석)\n");
    }


    @DisplayName("특정 크루 출석 상태 현황 출력")
    @Test
    public void test44() {
        Crew crew = new Crew("미미");
        crew.addAttendance(LocalDateTime.of(2024, 12, 2, 13,0));
        crew.addAttendance(LocalDateTime.of(2024, 12, 3, 9,58));
        crew.addAttendance(LocalDateTime.of(2024, 12, 4, 10,2));
        crew.addAttendance(LocalDateTime.of(2024, 12, 5, 10,6));
        crew.addAttendance(LocalDateTime.of(2024, 12, 6, 10,1));
        crew.addAttendance(LocalDateTime.of(2024, 12, 10, 10,8));
        assertThat(crew.getFormatedAttendanceStateInfo(LocalDate.of(2024, 12, 13))).isEqualTo("출석: 4회\n"
                + "지각: 2회\n"
                + "결석: 3회\n");
    }

    @DisplayName("특정 크루 출석 상태 현황 출력_면담 대상자 여부 판별")
    @Test
    public void test55() {
        Crew crew = new Crew("미미");
        crew.addAttendance(LocalDateTime.of(2024, 12, 2, 13,0));
        crew.addAttendance(LocalDateTime.of(2024, 12, 3, 9,58));
        crew.addAttendance(LocalDateTime.of(2024, 12, 4, 10,2));
        crew.addAttendance(LocalDateTime.of(2024, 12, 5, 10,6));
        crew.addAttendance(LocalDateTime.of(2024, 12, 6, 10,1));
        crew.addAttendance(LocalDateTime.of(2024, 12, 10, 10,8));
        assertThat(crew.getFormatedWarningStatus(LocalDate.of(2024, 12, 13))).isEqualTo("면담 대상자입니다.");
    }

    @DisplayName("특정 크루 출석 상태 현황 출력_경고 대상자 여부 판별")
    @Test
    public void test56() {
        Crew crew = new Crew("미미");
        crew.addAttendance(LocalDateTime.of(2024, 12, 2, 13,0));
        crew.addAttendance(LocalDateTime.of(2024, 12, 3, 9,58));
        crew.addAttendance(LocalDateTime.of(2024, 12, 4, 10,2));
        crew.addAttendance(LocalDateTime.of(2024, 12, 5, 10,6));
        crew.addAttendance(LocalDateTime.of(2024, 12, 6, 10,1));
        crew.addAttendance(LocalDateTime.of(2024, 12, 9, 9,8));
        crew.addAttendance(LocalDateTime.of(2024, 12, 10, 10,8));
        assertThat(crew.getFormatedWarningStatus(LocalDate.of(2024, 12, 13))).isEqualTo("경고 대상자입니다.");
    }

    @DisplayName("특정 크루 출석 상태 현황 출력_제적 대상자 여부 판별")
    @Test
    public void test57() {
        Crew crew = new Crew("미미");
        crew.addAttendance(LocalDateTime.of(2024, 12, 2, 13,0));
        crew.addAttendance(LocalDateTime.of(2024, 12, 3, 9,58));
        crew.addAttendance(LocalDateTime.of(2024, 12, 4, 10,2));
        assertThat(crew.getFormatedWarningStatus(LocalDate.of(2024, 12, 13))).isEqualTo("제적 대상자입니다.");
    }

    @DisplayName("특정 크루 출석 상태 현황 출력_대상자 X 여부 판별")
    @Test
    public void test58() {
        Crew crew = new Crew("미미");
        crew.addAttendance(LocalDateTime.of(2024, 12, 2, 13,0));
        crew.addAttendance(LocalDateTime.of(2024, 12, 3, 9,58));
        crew.addAttendance(LocalDateTime.of(2024, 12, 4, 10,2));
        crew.addAttendance(LocalDateTime.of(2024, 12, 5, 10,6));
        crew.addAttendance(LocalDateTime.of(2024, 12, 6, 10,1));
        crew.addAttendance(LocalDateTime.of(2024, 12, 10, 10,8));
        assertThat(crew.getFormatedWarningStatus(LocalDate.of(2024, 12, 10))).isEqualTo("");
    }


    @DisplayName("특정 크루 제적 위험 판별")
    @Test
    void test66() {
        Crew crew = new Crew("미미");
        // 1일 -x
        crew.addAttendance(LocalDateTime.of(2024, 12, 2, 13,0)); // 출
        crew.addAttendance(LocalDateTime.of(2024, 12, 3, 9,58));    // 출
        crew.addAttendance(LocalDateTime.of(2024, 12, 4, 10,2));    // 출
        crew.addAttendance(LocalDateTime.of(2024, 12, 5, 10,6));    // 지
        crew.addAttendance(LocalDateTime.of(2024, 12, 6, 10,1));    // 출
        crew.addAttendance(LocalDateTime.of(2024, 12, 10, 10,8));   // 지
        assertThat(crew.printWarningInfo(LocalDate.of(2024, 12, 13)))
                .isEqualTo("미미: 결석 3회, 지각 2회 (면담)");
    }

}
