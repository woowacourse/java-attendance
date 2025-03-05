package attendance.utils;

import attendance.domain.Crew;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AttendanceBookParserTest {

    @Test
    void 출석부_파서_생성_테스트() {
        //given
        List<String> lines = List.of("쿠키,2024-12-13 10:08");

        //when
        AttendanceBookParser attendanceBookParser = new AttendanceBookParser(lines);

        //then
        Assertions.assertThat(attendanceBookParser).isInstanceOf(AttendanceBookParser.class);
    }

    @Test
    void 출석부_파서_원본_출석부_크루_생성_확인() {
        //given
        List<String> lines = List.of("쿠키,2024-12-13 10:08");

        //when
        AttendanceBookParser attendanceBookParser = new AttendanceBookParser(lines);

        //then
        Assertions.assertThat(attendanceBookParser.getOriginalAttendanceBook().keySet())
                .allMatch(crew -> crew.getName().equals("쿠키"));

    }

    @Test
    void 출석부_파서_원본_출석부_시간_생성_확인() {
        //given
        List<String> lines = List.of("쿠키,2024-12-13 10:08");
        LocalDateTime expectedTime = LocalDateTime.of(2024, 12, 13, 10, 8);

        //when
        AttendanceBookParser attendanceBookParser = new AttendanceBookParser(lines);

        Map<Crew, List<LocalDateTime>> originalAttendanceBook = attendanceBookParser.getOriginalAttendanceBook();

        //then
        originalAttendanceBook.forEach((crew, times) ->
                Assertions.assertThat(times).isNotEmpty()
                        .first()
                        .isEqualTo(expectedTime)
        );
    }

}