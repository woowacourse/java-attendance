package attendance.repository;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceDate;
import attendance.domain.AttendanceRecord;
import attendance.domain.AttendanceTime;
import attendance.domain.Crew;
import attendance.domain.Day;
import attendance.domain.Month;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AttendanceBookLoaderTest {

    private static final String TEST_FILE_PATH = "src/test/resources/attendances-test.csv";

    @Nested
    class ValidCases {

        @Test
        void 파일에서_출석부를_읽어온다() {
            // given
            AttendanceBookLoader attendanceBookLoader = new AttendanceBookLoader(
                TEST_FILE_PATH);

            Crew crewMurphy = new Crew("머피");
            AttendanceRecord murphyAttendanceRecord = new AttendanceRecord(
                Map.of(
                    new AttendanceDate(2024, new Month(12), new Day(2)),
                    new AttendanceTime(13, 0),
                    new AttendanceDate(2024, new Month(12), new Day(3)),
                    new AttendanceTime(10, 0)
                ));

            Crew crewJamie = new Crew("제이미");
            AttendanceRecord jamieAttendanceRecord = new AttendanceRecord(
                Map.of(
                    new AttendanceDate(2024, new Month(12), new Day(2)),
                    new AttendanceTime(13, 0),
                    new AttendanceDate(2024, new Month(12), new Day(3)),
                    new AttendanceTime(10, 0)
                ));

            AttendanceBook murphyAttendanceBook = new AttendanceBook(crewMurphy,
                murphyAttendanceRecord);
            AttendanceBook jamieAttendanceBook = new AttendanceBook(crewJamie,
                jamieAttendanceRecord);

            // when
            Map<String, AttendanceBook> attendanceBooks = attendanceBookLoader.loadAttendanceBooks();

            // then
            assertThat(attendanceBooks)
                .containsEntry(crewJamie.getNickname(), jamieAttendanceBook)
                .containsEntry(crewMurphy.getNickname(), murphyAttendanceBook);
        }
    }
}
