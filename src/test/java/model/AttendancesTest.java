package model;

import static constant.PathConstant.ATTENDANCE_FILE_PATH;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.FileParser;

class AttendancesTest {

    @Test
    @DisplayName("")
    void test() {
        // given
        List<String> lines = FileParser.readLines(ATTENDANCE_FILE_PATH.getPath());

        // when
        Attendances attendances = Attendances.from(lines);

        // then
        int expected = 16;
        assertThat(attendances.getAttendances()).hasSize(expected);
    }
}