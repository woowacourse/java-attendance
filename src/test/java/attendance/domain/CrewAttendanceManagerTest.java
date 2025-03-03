package attendance.domain;

import attendance.util.DateGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("출석 매니저 테스트")
class CrewAttendanceManagerTest {

    private final DateGenerator dateGenerator = new TestDateGenerator();
    private final CrewAttendanceManager attendanceManager = new CrewAttendanceManager(dateGenerator);

    @Test
    @DisplayName("출석 파일을 읽어 출석 매니저를 생성한다")
    void 출석_파일을_읽어_출석_매니저를_생성한다() {
        assertThatNoException()
                .isThrownBy(attendanceManager::initAttendanceFromFile);
    }

    @Test
    @DisplayName("동일한 닉네임의 크루가 없으면 예외가 발생한다")
    void 동일한_닉네임의_크루가_없으면_예외가_발생한다() {
        // given
        String nickname = "ERROR";

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceManager.validateNicknameExists(nickname))
                .withMessage("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    @Test
    @DisplayName("닉네임과 입력 시간으로 크루의 출석을 등록한다")
    void 닉네임과_입력_시간으로_크루의_출석을_등록한다() {
        // given
        LocalDate nowDate = dateGenerator.generate();

        Attendance attendance = Attendance.fromDateTime(LocalDateTime.of(nowDate, LocalTime.MAX));
        Attendances attendances = new Attendances(List.of(attendance));

        String nickname = "비타";
        LocalTime checkTime = LocalTime.of(10, 0);

        attendanceManager.addNewCrew(nickname, attendances);

        // when
        Attendance result = attendanceManager.processAttendanceCheck(nickname, checkTime);

        // then
        assertAll(
                () -> assertThat(result.getDateTime().toLocalTime()).isEqualTo(checkTime),
                () -> assertThat(result.getState()).isEqualTo(AttendanceState.ATTENDANCE)
        );
    }

    @Test
    @DisplayName("닉네임과 입력 일자와 시간으로 크루의 출석을 수정한다")
    void 닉네임과_입력_일자와_시간으로_크루의_출석을_수정한다() {
        // given
        LocalDate nowDate = dateGenerator.generate();
        LocalDateTime baseDateTime = LocalDateTime.of(nowDate, LocalTime.of(18, 0));

        Attendance attendance = Attendance.fromDateTime(baseDateTime);
        Attendances attendances = new Attendances(List.of(attendance));

        String nickname = "비타";
        LocalDateTime updateDateTime = LocalDateTime.of(nowDate, LocalTime.of(10, 0));

        attendanceManager.addNewCrew(nickname, attendances);

        // when
        AttendanceUpdate result = attendanceManager.processAttendanceUpdate(nickname, updateDateTime);

        // then
        assertAll(
                () -> assertThat(result.getBeforeAttendance().getDateTime()).isEqualTo(baseDateTime),
                () -> assertThat(result.getBeforeAttendance().getState()).isEqualTo(AttendanceState.ABSENCE),

                () -> assertThat(result.getAfterAttendance().getDateTime()).isEqualTo(updateDateTime),
                () -> assertThat(result.getAfterAttendance().getState()).isEqualTo(AttendanceState.ATTENDANCE)
        );
    }

    @Test
    @DisplayName("닉네임의 전날 출석 날짜, 시간과 출결 상황을 반환한다")
    void 닉네임의_전날_출석_날짜_시간과_출결_상황을_반환한다() {
        // given
        LocalDate nowDate = dateGenerator.generate();

        Attendances baseRecord = new Attendances(List.of(
                Attendance.fromDateTime(LocalDateTime.of(nowDate, LocalTime.of(13, 0))),
                Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.of(13, 0))),
                Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.of(13, 0)))
        ));

        List<Attendance> exceptedRecord = List.of(
                Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.of(13, 0))),
                Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.of(13, 0)))
        );

        String nickname = "비타";

        attendanceManager.addNewCrew(nickname, baseRecord);

        // when
        AttendanceRecord result = attendanceManager.getAttendanceRecord(nickname);

        // then
        assertAll(
                () -> assertThat(result.getRecord().getAttendances()).containsExactlyElementsOf(exceptedRecord),
                () -> assertThat(result.getNickname()).isEqualTo(nickname)
        );
    }

    @Test
    @DisplayName("정렬된 출결 상황 위험자를 반환한다")
    void 정렬된_출결_상황_위험자를_반환한다() {
        // given
        List<String> nicknames = List.of("비타", "레오", "듀이", "꾹이", "몽이");

        LocalDate nowDate = dateGenerator.generate();
        List<Attendances> attendances = List.of(
                new Attendances(List.of(
                        Attendance.fromDateTime(LocalDateTime.of(nowDate, LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.MAX))
                )),
                new Attendances(List.of(
                        Attendance.fromDateTime(LocalDateTime.of(nowDate, LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.MAX))
                )),
                new Attendances(List.of(
                        Attendance.fromDateTime(LocalDateTime.of(nowDate, LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(5), LocalTime.MIDNIGHT))
                )),
                new Attendances(List.of(
                        Attendance.fromDateTime(LocalDateTime.of(nowDate, LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(5), LocalTime.MAX))
                )),
                new Attendances(List.of(
                        Attendance.fromDateTime(LocalDateTime.of(nowDate, LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(5), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(6), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(7), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(8), LocalTime.MAX))
                ))
        );

        for (int i = 0; i < nicknames.size(); i++) {
            attendanceManager.addNewCrew(nicknames.get(i), attendances.get(i));
        }

        // when
        AttendanceRecords records = attendanceManager.getAttendanceRecords();

        // then
        assertAll(
                () -> assertThat(records.records().get(0).getNickname()).isEqualTo("몽이"),
                () -> assertThat(records.records().get(1).getNickname()).isEqualTo("꾹이"),
                () -> assertThat(records.records().get(2).getNickname()).isEqualTo("듀이"),
                () -> assertThat(records.records().get(3).getNickname()).isEqualTo("레오"),
                () -> assertThat(records.records().get(4).getNickname()).isEqualTo("비타")
        );
    }

    private static class TestDateGenerator implements DateGenerator {

        @Override
        public LocalDate generate() {
            return LocalDate.of(2025, 3, 19);
        }
    }
}
