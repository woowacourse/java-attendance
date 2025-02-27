package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewTest {

    private Crew crew;

    @BeforeEach
    void setUp() {
        crew = new Crew("pobi");
    }

    @Test
    @DisplayName("등교 시간을 입력하면 출석 기록이 저장된다.")
    void crewTest() throws NoSuchFieldException, IllegalAccessException {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        crew.putAttendanceRecord(date, time);

        Field field = Crew.class.getDeclaredField("attendanceRecords");
        field.setAccessible(true);

        @SuppressWarnings("unchecked")
        Map<LocalDate, LocalTime> attendanceRecords = (Map<LocalDate, LocalTime>) field.get(crew);

        assertThat(attendanceRecords)
                .isNotNull()
                .containsEntry(date, time);
    }

    @Test
    @DisplayName("이미 출석한 경우, 다시 출석할 수 없으며 수정 기능을 이용하도록 안내한다.")
    void crewTest2() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        crew.putAttendanceRecord(date, time);

        assertThatThrownBy(() -> crew.putAttendanceRecord(date, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.ATTENDANCE_DATE_DUPLICATED.getMessage());
    }
}
