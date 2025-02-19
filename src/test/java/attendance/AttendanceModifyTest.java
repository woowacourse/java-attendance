package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceStatus;
import attendance.exception.AttendanceArgumentException;
import attendance.repository.AttendanceFileRepository;
import attendance.service.AttendanceManagerService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceModifyTest
{

    private static Stream<Arguments> attendanceTest() {
        return Stream.of(
                Arguments.arguments(
                        "/testModify.csv",
                        "투다",
                        LocalDate.of(2024,12,13),
                        LocalTime.of(10,31),
                        "-> 10:31 (결석) 수정 완료!"
                ),
                Arguments.arguments(
                        "/testModify.csv",
                        "체체",
                        LocalDate.of(2024,12,13),
                        LocalTime.of(10,15),
                        "-> 10:15 (지각) 수정 완료!"
                ),
                Arguments.arguments(
                        "/testModify.csv",
                        "꾹이",
                        LocalDate.of(2024,12,13),
                        LocalTime.of(10,15),
                        "-> 10:15 (지각) 수정 완료!"
                ),
                Arguments.arguments(
                        "/testModify.csv",
                        "비타",
                        LocalDate.of(2024,12,13),
                        LocalTime.of(10,4),
                        "-> 10:04 (출석) 수정 완료!"
                ),
                Arguments.arguments(
                        "/testModify.csv",
                        "듀이",
                        LocalDate.of(2024,12,13),
                        LocalTime.of(10,41),
                        "-> 10:41 (결석) 수정 완료!"

                )
        );
    }

    @ParameterizedTest
    @MethodSource("attendanceTest")
    @DisplayName("출석 데이터 테스트")
    void testAttendances(String src,String name,LocalDate modifyDate,LocalTime afterModifyTime,String formattedResult){
        AttendanceManagerService attendanceManagerService = new AttendanceManagerService(new AttendanceManager(),new AttendanceFileRepository(src));
        assertThat(
                attendanceManagerService.attendanceModify(name,modifyDate,afterModifyTime)
        ).contains(formattedResult);
    }
//
//    @Test
//    @DisplayName("주말 테스트")
//    void testWeekend(){
//        assertThatThrownBy(() -> new AttendanceManagerService(new AttendanceManager(),new AttendanceFileRepository("testWeekend.csv")))
//                .isInstanceOf(AttendanceArgumentException.class)
//                .hasMessageContaining("12월 14일 토요일은 등교일이 아닙니다.");
//    }
//
//    @Test
//    @DisplayName("등교 시간 테스트")
//    void testSchoolStartTime(){
//        assertThatThrownBy(() -> new AttendanceManagerService(new AttendanceManager(),new AttendanceFileRepository("testSchoolTime.csv")))
//                .isInstanceOf(AttendanceArgumentException.class)
//                .hasMessageContaining("등교시간에만 출석 가능합니다.");
//    }
//
//    @Test
//    @DisplayName("유효하지 않은 날짜 테스트")
//    void testInvalidDate(){
//        assertThatThrownBy(() -> new AttendanceManagerService(new AttendanceManager(),new AttendanceFileRepository("testInvalidDate.csv")))
//                .isInstanceOf(AttendanceArgumentException.class)
//                .hasMessageContaining("유효하지 않은 날짜 양식입니다.");
//    }
//
//    @Test
//    @DisplayName("출석 중복 테스트")
//    void duplicateAttendanceTest() {
//        assertThatThrownBy(() -> new AttendanceManagerService(new AttendanceManager(),new AttendanceFileRepository("testDuplicateNickname.csv")))
//                .isInstanceOf(AttendanceArgumentException.class)
//                .hasMessageContaining("이미 출석되었습니다.");
//    }
//
//    @Test
//    @DisplayName("닉네임이 유효하지 않으면 예외가 발생한다")
//    void testNicknameInvalid() {
//        assertThatThrownBy(() -> new AttendanceManagerService(new AttendanceManager(),new AttendanceFileRepository("testInvalidNickname.csv")))
//                .isInstanceOf(AttendanceArgumentException.class)
//                .hasMessageContaining("닉네임은 공백일 수 없습니다.");
//    }
}
