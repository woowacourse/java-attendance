package config;

import domain.Attendance;
import domain.AttendanceSheet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import policy.AbsentPolicy;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReadFileTest {
    ReadFile<Attendance, AttendanceSheet> readFile;

    @BeforeEach
    void setUp() {
        readFile = new AttendanceSheetFactory(new AbsentPolicy());
    }

    @Test
    @DisplayName("지정된 위치의 파일이 아니면 예외가 발생한다")
    public void validateFileReaderPolicyTest() {
        assertThatThrownBy(() -> readFile.loadFile(Paths.get("attendances.csv")))
                .isInstanceOf(RuntimeException.class);
    }
}
