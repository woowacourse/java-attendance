package util;

import static org.assertj.core.api.Assertions.*;

import domain.CrewName;
import dto.InitialInformation;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FileReaderTest {
    @DisplayName("닉네임과 출석 일시가 기록된 파일을 조회한다.")
    @Test
    void test1() {
        FileReader fileReader = new FileReader();

        InitialInformation initialInformation = fileReader.readAttendanceData();
        Set<CrewName> crewNames = initialInformation.initialInformation().keySet();

        assertThat(crewNames).hasSize(5);
    }
}
