package model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class CrewGeneratorTest {

    @Test
    @DisplayName("크루 데이터가 출석 Manager 객체로 잘 생성되는 지 테스트")
    void parseCrewAndAttendanceBook() {

        // given
        final AttendanceDateTime todayDateTime = AttendanceDateTime.of("2024-12-14 10:00");
        // 14일 전날까지 유효한 날짜 10개
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
        final AttendanceManager attendanceManager = CrewGenerator.parseCrewAndAttendanceBook(todayDateTime, crewDatas);
        final Map<Crew, AttendanceBook> attendanceBooks = attendanceManager.getAttendanceBooks();

        // then
        attendanceBooks.keySet()
                .forEach(crew -> Assertions.assertThat(attendanceBooks.get(crew).getAttendances().size())
                        .isEqualTo(10));
    }
}