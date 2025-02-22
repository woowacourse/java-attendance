package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewStatusTest {
    @DisplayName("기능: 크루 출석 상태에 대응하는 문자열 반환")
    @Test
    void returnCrewStatus() {
        assertThat(CrewStatus.EXPEL.toString()).isEqualTo("제적");
        assertThat(CrewStatus.MEETING.toString()).isEqualTo("면담");
        assertThat(CrewStatus.WARNING.toString()).isEqualTo("경고");
    }
}
