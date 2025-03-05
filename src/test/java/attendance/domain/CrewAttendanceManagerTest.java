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
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("출석 매니저 테스트")
class CrewAttendanceManagerTest {

    private static final String TEST_ATTENDANCE_FILE = "testAttendances.csv";
    private final DateGenerator dateGenerator = new TestDateGenerator();
    private final CrewAttendanceManager attendanceManager = new CrewAttendanceManager(dateGenerator, TEST_ATTENDANCE_FILE);

    @Test
    @DisplayName("동일한 닉네임의 크루가 없으면 예외가 발생한다")
    void throwExceptionIfNicknameNotExists() {
        // given
        String nickname = "ERROR";

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceManager.validateNicknameExists(nickname))
                .withMessage("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    @Test
    @DisplayName("닉네임과 입력 시간으로 크루의 출석을 등록한다")
    void registerCrewAttendanceByNicknameAndTime() {
        // given
        String nickname = "비타";
        LocalDate nowDate = dateGenerator.generate();
        addCrewAttendance(nickname, LocalDateTime.of(nowDate, LocalTime.MAX));

        LocalTime checkTime = LocalTime.of(10, 0);

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
    void updateCrewAttendanceByNicknameAndDateTime() {
        // given
        String nickname = "비타";
        LocalDate nowDate = dateGenerator.generate();

        LocalDateTime initDateTime = LocalDateTime.of(nowDate, LocalTime.of(18, 0));
        LocalDateTime updateDateTime = LocalDateTime.of(nowDate, LocalTime.of(10, 0));

        addCrewAttendance(nickname, initDateTime);

        // when
        AttendanceUpdate result = attendanceManager.processAttendanceUpdate(nickname, updateDateTime);

        // then
        assertAll(
                () -> assertThat(result.getBeforeAttendance().getDateTime()).isEqualTo(initDateTime),
                () -> assertThat(result.getBeforeAttendance().getState()).isEqualTo(AttendanceState.ABSENCE),

                () -> assertThat(result.getAfterAttendance().getDateTime()).isEqualTo(updateDateTime),
                () -> assertThat(result.getAfterAttendance().getState()).isEqualTo(AttendanceState.ATTENDANCE)
        );
    }

    @Test
    @DisplayName("닉네임의 전날 출석 날짜, 시간과 출결 상황을 반환한다")
    void returnPreviousDayAttendanceStatusByNickname() {
        // given
        String nickname = "비타";
        LocalDate nowDate = dateGenerator.generate();

        Attendances initAttendances = createInitAttendances(nowDate);
        Attendances exceptedAttendances = createExceptedAttendances(nowDate);

        attendanceManager.addNewCrew(nickname, initAttendances);

        // when
        AttendanceRecord result = attendanceManager.getAttendanceRecord(nickname);

        // then
        assertThat(result.getRecord().getAttendances())
                .isEqualTo(exceptedAttendances.getAttendances());
    }

    @Test
    @DisplayName("정렬된 출결 상황 위험자를 반환한다")
    void returnSortedAttendanceRiskList() {
        // given
        List<String> nicknames = List.of("비타", "레오", "듀이", "꾹이", "몽이");
        List<String> excepted = List.of("몽이", "꾹이", "듀이", "레오", "비타");
        addCrewAttendances(nicknames);

        // when
        List<AttendanceRecord> records = attendanceManager.getAttendanceRecords().getRecords();

        List<String> result = records.stream()
                .map(AttendanceRecord::getNickname)
                .toList();

        // then
        assertThat(result).containsExactlyElementsOf(excepted);
    }

    private void addCrewAttendance(final String nickname, final LocalDateTime baseDateTime) {
        Attendance attendance = Attendance.createFromDateTime(baseDateTime);
        Attendances attendances = new Attendances(List.of(attendance));
        attendanceManager.addNewCrew(nickname, attendances);
    }

    private Attendances createInitAttendances(final LocalDate nowDate) {
        return new Attendances(List.of(
                Attendance.createFromDateTime(LocalDateTime.of(nowDate, LocalTime.of(13, 0))),
                Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.of(13, 0))),
                Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.of(13, 0)))
        ));
    }

    private Attendances createExceptedAttendances(final LocalDate nowDate) {
        return new Attendances(List.of(
                Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.of(13, 0))),
                Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.of(13, 0)))
        ));
    }

    private void addCrewAttendances(final List<String> nicknames) {
        List<Attendances> attendances = createAttendances();
        for (int index = 0; index < nicknames.size(); index++) {
            attendanceManager.addNewCrew(nicknames.get(index), attendances.get(index));
        }
    }

    private List<Attendances> createAttendances() {
        LocalDate nowDate = dateGenerator.generate();
        return List.of(
                new Attendances(List.of( // 결석 2회 비타
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.MAX)),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.MAX))
                )),
                new Attendances(List.of( // 결석 2회 레오
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.MAX)),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.MAX))
                )),
                new Attendances(List.of( // 결석 2회 지각 1회 듀이
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.MAX)),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.MAX)),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(5), LocalTime.MIDNIGHT))
                )),
                new Attendances(List.of( // 결석 3회 꾹이
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.MAX)),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.MAX)),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(5), LocalTime.MAX))
                )),
                new Attendances(List.of( // 결석 6회 몽이
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.MAX)),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.MAX)),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(5), LocalTime.MAX)),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(6), LocalTime.MAX)),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(7), LocalTime.MAX)),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(8), LocalTime.MAX))
                ))
        );
    }

    private static class TestDateGenerator implements DateGenerator {

        @Override
        public LocalDate generate() {
            return LocalDate.of(2025, 3, 19);
        }
    }
}
