package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.FileReaderUtil;

class AttendanceSheetsFactoryTest {

    @Test
    @DisplayName("출석 기록부 생성 테스트")
    void createAttendanceSheetTest() {
        //given
        AttendanceSheetsFactory attendanceSheetsFactory = new AttendanceSheetsFactory(new FileReaderUtil());

        //when-then
        assertDoesNotThrow(attendanceSheetsFactory::create);
    }
}
