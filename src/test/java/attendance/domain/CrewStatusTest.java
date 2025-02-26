package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewStatusTest {
    @DisplayName("기능: 제적 상태일 때 '제적' 문자열 반환")
    @Test
    void returnExpelStatus() {
        assertThat(CrewStatus.EXPEL.toString()).isEqualTo("제적");
    }

    @DisplayName("기능: 면담 상태일 때 '면담' 문자열 반환")
    @Test
    void returnMeetingStatus() {
        assertThat(CrewStatus.MEETING.toString()).isEqualTo("면담");
    }

    @DisplayName("기능: 경고 상태일 때 '경고' 문자열 반환")
    @Test
    void returnWarningStatus() {
        assertThat(CrewStatus.WARNING.toString()).isEqualTo("경고");
    }
}
