package domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.FileReaderUtil;

@DisplayName("출석 기록부 생성 테스트")
class AttendanceSheetsFactoryTest {

    @Test
    @DisplayName("출석 기록부를 생성할 수 있다.")
    void createAttendanceSheetTest() {
        //given
        AttendanceSheetsFactory attendanceSheetsFactory = new AttendanceSheetsFactory(new FileReaderUtil());

        //when-then
        assertDoesNotThrow(attendanceSheetsFactory::create);
    }
}
