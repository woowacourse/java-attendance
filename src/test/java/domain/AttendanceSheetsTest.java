package domain;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import util.FileReaderUtil;

class AttendanceSheetsTest {

    private static AttendanceSheets attendanceSheets;

    @BeforeAll
    static void beforeAll() {
        AttendanceSheetsFactory attendanceSheetsFactory = new AttendanceSheetsFactory(new FileReaderUtil());
        attendanceSheets = attendanceSheetsFactory.create();
    }

    @ParameterizedTest
    @DisplayName("닉네임을 통해 출석 기록을 가져올 수 있다.")
    @ValueSource(strings = {"쿠키", "빙봉", "빙티", "이든", "짱수"})
    void attendance_sheets_find_by_Nickname(String nickname) {
        // when
        List<AttendanceSheet> foundByNickname = attendanceSheets.findAttendanceByNickname(nickname);

        // then
        Assertions.assertThat(foundByNickname)
                .extracting("nickname")
                .containsOnly(nickname);
    }

    @Test
    @DisplayName("모든 닉네임을 가져올 수 있다.")
    void find_all_names() {
        // when
        List<String> allNames = attendanceSheets.findAllNames();

        // then
        Assertions.assertThat(allNames)
                .containsExactlyInAnyOrder("쿠키", "빙봉", "빙티", "이든", "짱수");
    }

    @ParameterizedTest
    @DisplayName("닉네임을 입력하지 않을 시 예외를 발생한다.")
    @NullAndEmptySource
    void null_and_empty(String nickname) {
        // when
        // then
        Assertions.assertThatThrownBy(() -> attendanceSheets.findAttendanceByNickname(nickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 닉네임은 존재하지 않습니다.");
    }

    @ParameterizedTest
    @DisplayName("없는 닉네임 입력 시 예외를 발생한다.")
    @ValueSource(strings = {"율무", "링크", "강산"})
    void not_contains_nickname(String nickname) {
        // when
        // then
        Assertions.assertThatThrownBy(() -> attendanceSheets.findAttendanceByNickname(nickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 닉네임은 출석 기록이 존재하지 않습니다.");
    }
}
