package domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import util.loader.FileLoader;

@Nested
public class AttendanceBookTest {

    @Nested
    @DisplayName("크루 생성 테스트")
    class createCrewTest {

        @Test
        @DisplayName("파일(csv)에서 크루별 데이터를 구분할 수 있다.")
        void separateCrew() {
            AttendanceBook attendanceBook = new AttendanceBook();
            attendanceBook.initializeCrewRecords(FileLoader.loadCSV("src/test/resources/attendances.csv"));

            assertThat(attendanceBook.countCrew()).isEqualTo(5);
        }
    }
}
