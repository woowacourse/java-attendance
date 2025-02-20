package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AttendanceSystemTest {


    @Nested
    @DisplayName("성공 테스트")
    class SuccessCases {

        @Test
        @DisplayName("출석 시스템을 생성한다.")
        void test1() {
            //given
            final List<String> data = List.of("쿠키,2024-12-13 10:08", "빙봉,2024-12-13 10:07");

            //when
            final AttendanceSystem attendanceSystem = AttendanceSystem.of(data, LocalDate.of(2024, 12, 14));
            final List<Crew> crews = attendanceSystem.getCrews();

            //then
            assertThat(crews).hasSize(2);
        }

        @Test
        @DisplayName("출석 시스템에 등록된 크루원인지 닉네임으로 검사한다.")
        void test2() {
            //given
            final List<String> data = List.of("쿠키,2024-12-13 10:08", "빙봉,2024-12-13 10:07");
            final AttendanceSystem attendanceSystem = AttendanceSystem.of(data, LocalDate.of(2024, 12, 14));
            final String expectedCrewName = "쿠키";

            //when
            final Crew crew = attendanceSystem.findCrewByName(expectedCrewName);

            //then
            assertThat(crew.getName().getName()).isEqualTo(expectedCrewName);
        }

        @Test
        @DisplayName("출석을 생성한다.")
        void attendance() {
            //given


            //when

            //then

        }

    }


    @Nested
    @DisplayName("실패 테스트")
    class FailCases {

    }
}
