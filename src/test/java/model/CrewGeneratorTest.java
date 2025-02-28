package model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;

class CrewGeneratorTest {

    @Test
    @DisplayName("크루 데이터가 출석 Manager 객체로 잘 생성되는 지 테스트")
    void parseCrewAndAttendanceBook() {

        // given
        // 쿠키 2, 빙봉 3, 빙티 2, 이든 2, 짱수 1
        final List<String[]> crewDatas = List.of(
                new String[]{"쿠키", "2024-12-13 10:08"},
                new String[]{"빙봉", "2024-12-13 10:07"},
                new String[]{"빙티", "2024-12-13 10:07"},
                new String[]{"이든", "2024-12-13 10:07"},
                new String[]{"빙봉", "2024-12-12 11:11"},
                new String[]{"이든", "2024-12-12 10:06"},
                new String[]{"짱수", "2024-12-12 10:00"},
                new String[]{"빙봉", "2024-12-11 10:02"},
                new String[]{"쿠키", "2024-12-11 10:02"},
                new String[]{"빙티", "2024-12-10 10:08"}
        );

        // when
        final AttendanceManager attendanceManager = CrewGenerator.parseCrewAndAttendanceBook(crewDatas);
        final Map<Crew, AttendanceBook> attendanceBooks = attendanceManager.getAttendanceBooks();

        // then
        assertAll(
                () -> Assertions.assertThat(attendanceBooks).isNotEmpty(),
                () -> Assertions.assertThat(attendanceBooks.get(Crew.of("쿠키")).getAttendances().size()).isEqualTo(2),
                () -> Assertions.assertThat(attendanceBooks.get(Crew.of("빙봉")).getAttendances().size()).isEqualTo(3),
                () -> Assertions.assertThat(attendanceBooks.get(Crew.of("빙티")).getAttendances().size()).isEqualTo(2),
                () -> Assertions.assertThat(attendanceBooks.get(Crew.of("이든")).getAttendances().size()).isEqualTo(2),
                () -> Assertions.assertThat(attendanceBooks.get(Crew.of("짱수")).getAttendances().size()).isEqualTo(1)
        );
    }
}