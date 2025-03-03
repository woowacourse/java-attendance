package attendance.util;

import attendance.domain.AttendanceBook;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AttendanceReaderTest {

    @Test
    void 파일_내용으로_출석_기록을_초기화한다() {

        // given
        final AttendanceBook attendanceBook = new AttendanceBook();

        // when
        AttendanceReader.initAttendances(attendanceBook, new TestReaderImpl());

        // then
        Assertions.assertAll(() -> {
            Assertions.assertEquals(attendanceBook.getAttendancesByName("이든").size(), 3);
            Assertions.assertEquals(attendanceBook.getAttendancesByName("빙봉").size(), 2);
        });
    }

    static class TestReaderImpl implements Reader {

        @Override
        public List<String> getContents(final String filePath) {

            return new ArrayList<>(Arrays.asList(
                    "nickname,datetime",
                    "이든,2025-02-14 13:01",
                    "이든,2025-02-14 13:02",
                    "이든,2025-02-14 13:06",
                    "빙봉,2025-02-14 13:06",
                    "빙봉,2025-02-14 13:06"
            ));
        }
    }
}
