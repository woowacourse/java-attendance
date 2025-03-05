package attendance.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceDate;
import attendance.domain.AttendanceRecord;
import attendance.domain.AttendanceTime;
import attendance.domain.Crew;
import attendance.domain.Day;
import attendance.domain.Month;
import java.util.HashMap;
import java.util.List;
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
                Map.of(new AttendanceDate(
                        2024, new Month(12), new Day(2)),
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

        @Test
        void 패널티크루들을_정렬하여_반환한다() {
            // given
            Crew crewWithMostAbsences = new Crew("크루원1");
            Crew crewWithFewerAbsences = new Crew("크루원2");
            Crew crewWithSameAbsences = new Crew("크루원3");
            Crew crewWithoutPenalty = new Crew("크루원4");

            AttendanceBook attendanceBookWithMostAbsences = new AttendanceBook(
                crewWithMostAbsences, new AttendanceRecord(Map.of(
                new AttendanceDate(2024, new Month(12), new Day(2)),
                new AttendanceTime(13, 31),
                new AttendanceDate(2024, new Month(12), new Day(3)),
                new AttendanceTime(10, 31))));

            AttendanceBook attendanceBookWithFewerAbsences = new AttendanceBook(
                crewWithFewerAbsences, new AttendanceRecord(Map.of(
                new AttendanceDate(2024, new Month(12), new Day(2)),
                new AttendanceTime(13, 0))));

            AttendanceBook attendanceBookWithSameAbsences = new AttendanceBook(
                crewWithSameAbsences, new AttendanceRecord(Map.of(
                new AttendanceDate(2024, new Month(12), new Day(2)),
                new AttendanceTime(13, 31),
                new AttendanceDate(2024, new Month(12), new Day(3)),
                new AttendanceTime(10, 31))));

            AttendanceBook attendanceBookWithoutPenalty = new AttendanceBook(
                crewWithoutPenalty, new AttendanceRecord(Map.of(
                new AttendanceDate(2024, new Month(12), new Day(2)),
                new AttendanceTime(13, 0),
                new AttendanceDate(2024, new Month(12), new Day(3)),
                new AttendanceTime(10, 0))));

            AttendanceBookRepository attendanceBookRepository = new AttendanceBookRepository(
                Map.of(
                    crewWithMostAbsences.getNickname(),
                    attendanceBookWithMostAbsences,
                    crewWithFewerAbsences.getNickname(),
                    attendanceBookWithFewerAbsences,
                    crewWithSameAbsences.getNickname(),
                    attendanceBookWithSameAbsences,
                    crewWithoutPenalty.getNickname(),
                    attendanceBookWithoutPenalty
                ));

            // when
            List<AttendanceBook> attendanceBooks = attendanceBookRepository
                .findAllPenaltyCrewUntilDateOrderByAbsenceCountAndCrewNickname(
                    new AttendanceDate(2024, new Month(12), new Day(5)));

            // then
            assertThat(attendanceBooks).containsExactly(
                attendanceBookWithMostAbsences,
                attendanceBookWithSameAbsences,
                attendanceBookWithFewerAbsences
            );
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
