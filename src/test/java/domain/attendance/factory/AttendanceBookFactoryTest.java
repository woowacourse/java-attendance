package domain.attendance.factory;

import static org.assertj.core.api.Assertions.assertThatCode;

import domain.dto.AttendanceRecordDto;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceBookFactoryTest {

    @Test
    @DisplayName("Dto의 리스트로부터 AttendanceBook 반환")
    void createAttendanceTest() {
        // given
        List<AttendanceRecordDto> records = List.of(
                new AttendanceRecordDto("차니", LocalDateTime.of(2024, 12, 2, 13, 0)),
                new AttendanceRecordDto("포비", LocalDateTime.of(2024, 12, 2, 13, 6))
        );

        // when
        assertThatCode(() -> AttendanceBookFactory.createAttendanceBook(records))
                .doesNotThrowAnyException();
    }
}
