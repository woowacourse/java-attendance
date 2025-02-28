package attendance.utils;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.Attendance;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceReaderTest {

    @DisplayName("출석 기록을 파싱한다.")
    @Test
    void 출석_기록을_파싱한다() {

        // given
        List<String> contents = new ArrayList<>();
        contents.add("name,time");
        contents.add("이든,2025-02-03 10:06");

        // when
        Set<Attendance> attendances = AttendanceReader.getAttendancesOnFile(contents);

        // then
        assertThat(attendances.size()).isEqualTo(1);
    }

    @DisplayName("크루 이름을 파싱한다.")
    @Test
    void 크루_이름을_파싱한다() {

        // given
        List<String> contents = new ArrayList<>();
        contents.add("name,time");
        contents.add("이든,2025-02-03 10:06");

        // when
        Set<String> crewNames = AttendanceReader.getCrewNamesOnFile(contents);

        // then
        assertThat(crewNames.size()).isEqualTo(1);
        assertThat(crewNames.contains("이든")).isTrue();
    }


}
