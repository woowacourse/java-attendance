package attendance.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceDate;
import attendance.domain.AttendanceRecord;
import attendance.domain.AttendanceTime;
import attendance.domain.Crew;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AttendanceBookRepositoryTest {

    @Nested
    class ValidCases {

        @Test
        void 크루의이름으로_출석부를_반환한다() {
            // given
            Crew crew = new Crew("크루원");
            AttendanceRecord attendanceRecord = new AttendanceRecord(
                Map.of(new AttendanceDate(2024, 12, 2),
                    new AttendanceTime(13, 0)));
            AttendanceBook attendanceBook = new AttendanceBook(crew,
                attendanceRecord);
            Map<String, AttendanceBook> attendanceBooks = Map.of(
                crew.getNickname(), attendanceBook);

            AttendanceBookRepository attendanceBookRepository = new AttendanceBookRepository(
                attendanceBooks);
            
            // when
            AttendanceBook targetAttendanceBook = attendanceBookRepository
                .findByCrewNickname(crew.getNickname())
                .get();

            // then
            assertThat(targetAttendanceBook).isEqualTo(attendanceBook);
        }
    }

    @Nested
    class InvalidCases {

        @Test
        void 출석부_목록은_출석부들을_가지고_있어야_한다() {
            // when & then
            assertThatThrownBy(() -> new AttendanceBookRepository(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석부 목록은 출석부들을 가지고 있어야 합니다.");
        }

        @Test
        void 출석부_목록은_크루의_닉네임과_출석부를_가지고_있어야_한다() {
            // given
            Map<String, AttendanceBook> attendanceBooks = new HashMap<>();
            attendanceBooks.put(null, null);

            // when & then
            assertThatThrownBy(
                () -> new AttendanceBookRepository(attendanceBooks))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석부 목록은 크루의 닉네임과 출석부를 가지고 있어야 합니다.");
        }
    }
}
