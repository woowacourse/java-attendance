package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.FileInformationProvider;

class FileInformationProviderTest {
    @Test
    @DisplayName("출석부 기록 전환 후 학생 수 확인 테스트")
    void 출석부_기록_전환_후_학생_수_확인_테스트() throws IOException {
        FileInformationProvider fileProvider = new FileInformationProvider();
        Map<String, List<LocalDateTime>> studentRecord = fileProvider.loadStudentAttendance();
        int expect = 5;
        int result = studentRecord.size();
        assertEquals(expect, result);
    }

    @Test
    @DisplayName("출석부 기록 전환 후 학생 기록 확인 테스트")
    void 출석부_기록_전환_후_학생_기록_확인_테스트() throws IOException {
        FileInformationProvider fileProvider = new FileInformationProvider();
        Map<String, List<LocalDateTime>> studentRecord = fileProvider.loadStudentAttendance();
        List<LocalDateTime> attendanceRecord = studentRecord.get("빙티");
        int expect = 2;
        int result = attendanceRecord.size();
        assertEquals(expect, result);
    }

}