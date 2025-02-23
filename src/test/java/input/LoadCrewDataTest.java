package input;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.AttendanceRegister;
import attendance.model.CrewDataLoader;
import global.BaseTest;
import java.util.HashMap;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoadCrewDataTest extends BaseTest {

    private CrewDataLoader loader;
    private AttendanceRegister register;

    @BeforeEach
    void setUp() {
        register = new AttendanceRegister(new HashMap<>());
        loader = new CrewDataLoader(register);
        loader.load("attendances.csv");
    }

    @Test
    void 빙티의_출석정보가_저장되었는지_확인한다() {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(register.findAttendanceHistoryByCrewName("빙티").computeAttendanceCount()).isEqualTo(3);
        softly.assertThat(register.findAttendanceHistoryByCrewName("빙티").computeAbsenceCount()).isEqualTo(4);
        softly.assertThat(register.findAttendanceHistoryByCrewName("빙티").computeLateCount()).isEqualTo(4);
        softly.assertAll();
    }

    @Test
    void 모든_인원의_출석_정보가_저장되었는지_확인한다() {
        assertThat(register.entryStream().count()).isEqualTo(5);
    }
}
