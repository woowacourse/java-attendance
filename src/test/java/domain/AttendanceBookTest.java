package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.FormatUtil;
import util.TimeMachine;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class AttendanceBookTest {

    private AttendanceBook attendanceBook;

    @BeforeEach
    void setUp() {
        TimeMachine.timeTravelAt(5);

        List<String> attendanceData = List.of(
                "강산" + FormatUtil.ATTENDANCE_DATA_DELIMITER + "2024-12-02 09:00",
                "강산" + FormatUtil.ATTENDANCE_DATA_DELIMITER + "2024-12-03 09:00",
                "강산" + FormatUtil.ATTENDANCE_DATA_DELIMITER + "2024-12-04 09:00",
                "고양이" + FormatUtil.ATTENDANCE_DATA_DELIMITER + "2024-12-02 09:10",
                "고양이" + FormatUtil.ATTENDANCE_DATA_DELIMITER + "2024-12-03 09:10",
                "고양이" + FormatUtil.ATTENDANCE_DATA_DELIMITER + "2024-12-04 09:10",
                "결석이" + FormatUtil.ATTENDANCE_DATA_DELIMITER + "2024-12-02 12:00",
                "결석이" + FormatUtil.ATTENDANCE_DATA_DELIMITER + "2024-12-03 12:00",
                "결석이" + FormatUtil.ATTENDANCE_DATA_DELIMITER + "2024-12-04 12:00"
        );

        attendanceBook = AttendanceBook.initialize(attendanceData);
    }

    @Test
    @DisplayName("출석 데이터를 이용해 출석부를 초기화할 수 있다")
    void initialize() {
        // given
        // when
        // then
        assertAll(
                () -> assertThat(attendanceBook.findAllByNickname("강산")).isNotNull(),
                () -> assertThat(attendanceBook.findAllByNickname("고양이")).isNotNull()
        );
    }

    @Test
    @DisplayName("닉네임을 이용해 출석 기록을 조회할 수 있다")
    void findAllByNickname() {
        // given
        // when
        Attendances attendance = attendanceBook.findAllByNickname("강산");

        // then
        assertThat(attendance).isNotNull();
    }

    @Test
    @DisplayName("존재하지 않는 닉네임을 조회하면 예외가 발생한다")
    void findAllByNickname_ShouldThrowException_WhenNicknameNotFound() {
        // given
        // when
        // then
        assertThatThrownBy(() -> attendanceBook.findAllByNickname("없음이"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 닉네임으로 출석된 기록이 없습니다.");
    }

    @Test
    @DisplayName("제적 후보자를 조회할 수 있다")
    void findExpulsionCandidates() {
        // given
        // when
        ExpulsionCandidates candidates = attendanceBook.findExpulsionCandidates();

        // then
        assertThat(candidates).isNotNull();
        assertThat(candidates.attendanceStatistics().size()).isEqualTo(1);
        assertThat(candidates.attendanceStatistics().getFirst().nickname()).isEqualTo("결석이");
        assertThat(candidates.attendanceStatistics().getFirst().absentCount()).isEqualTo(2);
    }
}
