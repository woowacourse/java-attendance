package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("출석 매니저 테스트")
class CrewAttendanceManagerTest {

    private final CrewAttendanceManager attendanceManager = new CrewAttendanceManager();

    @Test
    @DisplayName("닉네임과 입력 시간으로 크루의 출석을 등록한다")
    void 닉네임과_입력_시간으로_크루의_출석을_등록한다() {
        // given
        LocalDate nowDate = LocalDate.now();

        Attendance attendance = Attendance.fromDateTime(LocalDateTime.of(nowDate, LocalTime.MAX));
        Attendances attendances = new Attendances(List.of(attendance));

        String nickname = "비타";
        LocalDateTime checkDateTime = LocalDateTime.of(nowDate, LocalTime.of(10, 0));

        attendanceManager.addNewCrew(nickname, attendances);

        // when
        Attendance result = attendanceManager.processAttendanceCheck(nickname, checkDateTime);

        // then
        assertAll(
                () -> assertThat(result.getDateTime()).isEqualTo(checkDateTime),
                () -> assertThat(result.getState()).isEqualTo(AttendanceState.ATTENDANCE)
        );
    }

    @Test
    @DisplayName("닉네임과 입력 일자와 시간으로 크루의 출석을 수정한다")
    void 닉네임과_입력_일자와_시간으로_크루의_출석을_수정한다() {
        // given
        LocalDate nowDate = LocalDate.now();
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
                () -> assertThat(result.beforeAttendance().getDateTime()).isEqualTo(baseDateTime),
                () -> assertThat(result.beforeAttendance().getState()).isEqualTo(AttendanceState.ABSENCE),

                () -> assertThat(result.afterAttendance().getDateTime()).isEqualTo(updateDateTime),
                () -> assertThat(result.afterAttendance().getState()).isEqualTo(AttendanceState.ATTENDANCE)
        );
    }

    @Test
    @DisplayName("닉네임의 전날 출석 날짜, 시간과 출결 상황을 반환한다")
    void 닉네임의_전날_출석_날짜_시간과_출결_상황을_반환한다() {
        // given
        LocalDate nowDate = LocalDate.now();

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
        LocalDate nowDate = LocalDate.now();

        List<String> nicknames = List.of("비타", "레오", "듀이", "꾹이", "몽이");

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
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(3), LocalTime.MIDNIGHT))
                )),
                new Attendances(List.of(
                        Attendance.fromDateTime(LocalDateTime.of(nowDate, LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(3), LocalTime.MAX))
                )),
                new Attendances(List.of(
                        Attendance.fromDateTime(LocalDateTime.of(nowDate, LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(3), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(4), LocalTime.MAX)),
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
                () -> assertThat(records.getRecords().get(0).getNickname()).isEqualTo("몽이"),
                () -> assertThat(records.getRecords().get(1).getNickname()).isEqualTo("꾹이"),
                () -> assertThat(records.getRecords().get(2).getNickname()).isEqualTo("듀이"),
                () -> assertThat(records.getRecords().get(3).getNickname()).isEqualTo("레오"),
                () -> assertThat(records.getRecords().get(4).getNickname()).isEqualTo("비타")
        );
    }
}
