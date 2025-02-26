package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceSheetsTest {

    private static AttendanceSheets attendanceSheets;

    @BeforeAll
    static void beforeAll() {
        List<AttendanceSheet> testSheets = new ArrayList<>();
        testSheets.add(new AttendanceSheet("율무", AttendanceDateTime.from(LocalDateTime.of(2024, 12, 2, 13, 9))));
        testSheets.add(new AttendanceSheet("강산", AttendanceDateTime.from(LocalDateTime.of(2024, 12, 2, 13, 0))));
        testSheets.add(new AttendanceSheet("링크", AttendanceDateTime.from(LocalDateTime.of(2024, 12, 2, 13, 31))));
        testSheets.add(new AttendanceSheet("링크", AttendanceDateTime.from(LocalDateTime.of(2024, 12, 3, 10, 0))));
        testSheets.add(new AttendanceSheet("율무", AttendanceDateTime.from(LocalDateTime.of(2024, 12, 3, 10, 9))));
        testSheets.add(new AttendanceSheet("강산", AttendanceDateTime.from(LocalDateTime.of(2024, 12, 4, 10, 0))));
        testSheets.add(new AttendanceSheet("링크", AttendanceDateTime.from(LocalDateTime.of(2024, 12, 4, 9, 59))));
        testSheets.add(new AttendanceSheet("율무", AttendanceDateTime.from(LocalDateTime.of(2024, 12, 6, 10, 3))));

        attendanceSheets = new AttendanceSheets(testSheets);
    }

    @ParameterizedTest
    @DisplayName("닉네임을 통해 출석 기록을 가져올 수 있다.")
    @ValueSource(strings = {"율무", "강산", "링크"})
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
                .containsExactlyInAnyOrder("율무", "강산", "링크");
    }

    @ParameterizedTest
    @DisplayName("닉네임을 입력하지 않을 시 예외를 발생한다.")
    @NullAndEmptySource
    void null_and_empty(String nickname) {
        // when
        // then
        Assertions.assertThatThrownBy(() -> attendanceSheets.findAttendanceByNickname(nickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    @ParameterizedTest
    @DisplayName("없는 닉네임 입력 시 예외를 발생한다.")
    @ValueSource(strings = {"벡터", "제프", "에드"})
    void not_contains_nickname(String nickname) {
        // when
        // then
        Assertions.assertThatThrownBy(() -> attendanceSheets.findAttendanceByNickname(nickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 닉네임은 출석 기록이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("출석 기록을 추가할 수 있다.")
    void add_attendance_sheet() {
        // given
        AttendanceSheet attendanceSheet = new AttendanceSheet("율무",
                AttendanceDateTime.from(LocalDateTime.of(2024, 12, 9, 10, 3)));

        // when
        // then
        Assertions.assertThatCode(() -> attendanceSheets.add(attendanceSheet))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("중복된 출석 기록은 예외를 발생한다.")
    void add_duplicate_attendance_sheet() {
        // given
        AttendanceSheet attendanceSheet = new AttendanceSheet("율무",
                AttendanceDateTime.from(LocalDateTime.of(2024, 12, 6, 10, 3)));

        // when
        // then
        Assertions.assertThatThrownBy(() -> attendanceSheets.add(attendanceSheet))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이미 출석했습니다. 수정 기능을 이용하세요.");
    }

    @Test
    @DisplayName("없는 출석 기록은 예외를 발생한다.")
    void add_none_exist_attendance_sheet() {
        // given
        String nickname = "율무";
        int day = 13;

        // when
        // then
        Assertions.assertThatThrownBy(() -> attendanceSheets.findAttendanceSheetByNicknameAndDay(nickname, day))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출석 기록이 없는 날짜입니다.");
    }
}
