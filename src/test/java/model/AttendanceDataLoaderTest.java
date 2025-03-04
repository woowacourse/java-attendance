package model;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.AttendanceRegister;
import attendance.model.CrewDataLoader;
import org.junit.jupiter.api.Test;

class AttendanceDataLoaderTest {

    @Test
    void 크루_데이터가_저장되는지_확인한다() {
        // given
        AttendanceRegister attendanceRegister = new AttendanceRegister();
        CrewDataLoader crewDataLoader = new CrewDataLoader(attendanceRegister);

        // when
        crewDataLoader.load("attendances.csv");

        // then
        assertThat(attendanceRegister.entryStream().count()).isEqualTo(5);
    }
}
