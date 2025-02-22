package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

@DisplayName("출석 추가, 수정, 목록 확인 테스트")
class AttendanceSheetsTest {
    AttendanceSheets attendanceSheets;

    @BeforeEach
    void setUp() {
        attendanceSheets = new AttendanceSheets(
                new ArrayList<>(List.of(
                        new AttendanceSheet("링크",
                                AttendanceDateTime.from(LocalDateTime.of(2024, 12, 11, 10, 10))),
                        new AttendanceSheet("링크",
                                AttendanceDateTime.from(LocalDateTime.of(2024, 12, 12, 10, 3))),
                        new AttendanceSheet("우택호",
                                AttendanceDateTime.from(LocalDateTime.of(2024, 12, 12, 10, 10)))
                )
        ));
    }

    @Nested
    @DisplayName("출석 확인 추가 테스트")
    class addAttendanceTest{
        @Test
        @DisplayName("출석 확인을 추가할 수 있다.")
        void addTest() {
            //given
            AttendanceSheet attendanceSheet = new AttendanceSheet("링크",
                    AttendanceDateTime.from(LocalDateTime.of(2024, 12, 13, 10, 10)));

            //when-then
            assertDoesNotThrow(() -> attendanceSheets.add(attendanceSheet));
        }

        @Test
        @DisplayName("이미 출석했다면 예외가 발생한다.")
        void already_attendance_then_throwException() {
            //given
            AttendanceSheet attendanceSheet = new AttendanceSheet("링크",
                    AttendanceDateTime.from(LocalDateTime.of(2024, 12, 12, 10, 15)));

            //when-then
            assertThatThrownBy(() -> attendanceSheets.add(attendanceSheet))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("닉네임을 찾을 수 없으면 예외가 발생한다.")
        void nickname_notFound_then_throwException() {
            //given
            AttendanceSheet attendanceSheet = new AttendanceSheet("젤다",
                    AttendanceDateTime.from(LocalDateTime.of(2024, 12, 12, 10, 15)));

            //when-then
            assertThatThrownBy(() -> attendanceSheets.add(attendanceSheet))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }


    @Test
    @DisplayName("닉네임을 이용하여 출럭 리스트를 확인할 수 있다.")
    void findAttendanceByNicknameTest() {
        assertThat(attendanceSheets.findAttendanceByNickname("링크").size()).isEqualTo(2);
    }

    @Test
    @DisplayName("닉네임 리스트를 확인할 수 있다.")
    void findAllNamesTest() {
        assertThat(attendanceSheets.findAllNames().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("닉네임을 이용하여 출석 상태의 갯수를 확인할 수 있다.")
    void getStateCountTest() {
        assertThat(attendanceSheets.getStateCount("링크", AttendanceState.LATE)).isEqualTo(1);
    }
}
