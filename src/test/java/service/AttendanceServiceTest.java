package service;

import domain.AttendanceHistory;
import domain.AttendanceStorage;
import domain.AttendanceType;
import domain.Crew;
import dto.AttendanceStatusesOfCrewDto;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import view.AttendanceFileReader;

class AttendanceServiceTest {

    public static AttendanceHistory makeHistoryOf(Crew crew, int day) {
        return AttendanceHistory.of(crew, LocalDateTime.of(LocalDate.of(2024, 12, day), LocalTime.now()));
    }

    public static LocalDateTime makeLocalDateTimeOf(int day) {
        return LocalDateTime.of(LocalDate.of(2024, 12, day), LocalTime.now());
    }

    @DisplayName("validateNicknameRegistered() - 등록되지 않은 닉네임이면 예외 발생")
    @Test
    void validateNicknameRegistered1() throws FileNotFoundException {
        // given
        AttendanceStorage attendanceStorage = new AttendanceStorage();
        AttendanceFileReader.applyAttendanceFileTo(attendanceStorage);
        AttendanceService attendanceService = new AttendanceService(attendanceStorage);

        // when & then
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceService.validateNicknameRegistered("히스타"));
    }

    @DisplayName("validateNicknameRegistered() - 등록된 닉네임이면 예외 없음")
    @Test
    void validateNicknameRegistered2() throws FileNotFoundException {
        // given
        AttendanceStorage attendanceStorage = new AttendanceStorage();
        AttendanceFileReader.applyAttendanceFileTo(attendanceStorage);
        AttendanceService attendanceService = new AttendanceService(attendanceStorage);

        // when & then
        Assertions.assertThatNoException().isThrownBy(() -> attendanceService.validateNicknameRegistered("빙티"));
    }

    @DisplayName("validateHistoryNotDuplicated() - 동일한 출석 기록이 존재하면 예외 발생")
    @Test
    void validateHistoryNotDuplicatedTest1() {
        // given
        Crew crew = Crew.from("히스타");
        AttendanceStorage storage = new AttendanceStorage();
        AttendanceHistory history = makeHistoryOf(crew, 1);
        storage.add(history);
        AttendanceService attendanceService = new AttendanceService(storage);

        // when & then
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceService.validateHistoryNotDuplicated(crew, makeLocalDateTimeOf(1)));
    }

    @DisplayName("validateHistoryNotDuplicated() - 다른 Crew의 기록이면 예외 없음")
    @Test
    void validateHistoryNotDuplicatedTest2() {
        // given
        Crew crew = Crew.from("히스타");
        Crew comparedCrew = Crew.from("히로");
        AttendanceStorage storage = new AttendanceStorage();
        AttendanceHistory history = makeHistoryOf(crew, 1);
        storage.add(history);
        AttendanceService attendanceService = new AttendanceService(storage);

        // when & then
        Assertions.assertThatNoException()
                .isThrownBy(() -> attendanceService.validateHistoryNotDuplicated(comparedCrew, makeLocalDateTimeOf(1)));
    }

    @DisplayName("validateIsSchoolDay() - 휴일이면 예외 발생")
    @ParameterizedTest
    @ValueSource(ints = {1, 7, 8, 14, 15, 21, 22, 25, 28, 29})
    void checkRestDayTest(int day) {
        // given
        AttendanceStorage storage = new AttendanceStorage();
        AttendanceService attendanceService = new AttendanceService(storage);

        // when & then
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceService.validateIsSchoolDay(LocalDate.of(2024, 12, day)));
    }

    @DisplayName("validateIsSchoolDay() - 평일이면 예외 없음")
    @ParameterizedTest
    @ValueSource(ints = {2, 9, 16, 23, 30})
    void checkRestDayTest2(int day) {
        // given
        AttendanceStorage storage = new AttendanceStorage();
        AttendanceService attendanceService = new AttendanceService(storage);

        // when & then
        Assertions.assertThatNoException()
                .isThrownBy(() -> attendanceService.validateIsSchoolDay(LocalDate.of(2024, 12, day)));
    }

    @DisplayName("addAttendanceHistory() - 출석 기록 추가 후 중복 검사 시 예외 발생")
    @Test
    void addAttendanceHistoryTest() {
        // given
        AttendanceStorage storage = new AttendanceStorage();
        AttendanceService attendanceService = new AttendanceService(storage);
        Crew crew = Crew.from("히스타");

        // when
        attendanceService.addAttendanceHistory(crew, makeLocalDateTimeOf(1));

        // then
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceService.validateHistoryNotDuplicated(crew, makeLocalDateTimeOf(1)));
    }

    @DisplayName("addAttendanceHistory() - 다른 날짜에 추가하면 중복 검사 예외 없음")
    @Test
    void addAttendanceHistoryTest2() {
        // given
        AttendanceStorage storage = new AttendanceStorage();
        AttendanceService attendanceService = new AttendanceService(storage);
        Crew crew = Crew.from("히스타");

        // when
        attendanceService.addAttendanceHistory(crew, makeLocalDateTimeOf(1));

        // then
        Assertions.assertThatNoException()
                .isThrownBy(() -> attendanceService.validateHistoryNotDuplicated(crew, makeLocalDateTimeOf(2)));
    }

    @DisplayName("replaceAttendanceHistory() - 출석 기록 변경 후 정상 저장 확인")
    @Test
    void replaceAttendanceHistory() {
        // given
        AttendanceStorage storage = new AttendanceStorage();
        AttendanceService attendanceService = new AttendanceService(storage);
        Crew crew = Crew.from("히스타");

        attendanceService.addAttendanceHistory(crew, makeLocalDateTimeOf(2));

        // when
        attendanceService.replaceAttendanceHistory(crew, LocalDateTime.of(2024, 12, 2, 10, 0));

        // then
        AttendanceStatusesOfCrewDto allHistories = attendanceService.getAllHistories(crew, 3);
        int recordedAttendancesCount = allHistories.attendanceStatusDtos().size();
        String actualRecordedHour = allHistories.attendanceStatusDtos().getFirst().hour();

        Assertions.assertThat(recordedAttendancesCount).isEqualTo(1);
        Assertions.assertThat(actualRecordedHour).isEqualTo("10");
    }

    @DisplayName("getAllHistories() - 특정 Crew의 출석 기록 가져오기")
    @Test
    void getAllHistoriesTest() {
        // given
        AttendanceStorage storage = new AttendanceStorage();
        AttendanceService attendanceService = new AttendanceService(storage);
        Crew crew = Crew.from("히스타");

        attendanceService.addAttendanceHistory(crew, makeLocalDateTimeOf(2));

        // when
        AttendanceStatusesOfCrewDto allHistories = attendanceService.getAllHistories(crew, 3);

        // then
        Assertions.assertThat(allHistories.attendanceStatusDtos().getFirst().day()).isEqualTo(2);
    }

    @DisplayName("getAllAttendanceTypeCountOfCrew() - 출석 타입별 개수 확인")
    @Test
    void getAllAttendanceTypeCountOfCrewTest1() {
        // given
        AttendanceStorage storage = new AttendanceStorage();
        Crew crew = Crew.from("히스타");
        storage.add(crew);
        AttendanceService attendanceService = new AttendanceService(storage);
        attendanceService.addAttendanceHistory(crew, LocalDateTime.of(2024, 12, 2, 10, 0));

        // when
        Map<Crew, Map<AttendanceType, Integer>> allAttendanceTypeCountOfCrew = attendanceService.getAllAttendanceTypeCountOfCrew(
                3);

        // then
        int actualAttendanceCount = allAttendanceTypeCountOfCrew.get(crew).get(AttendanceType.ATTENDANCE);
        int actualAbsenceCount = allAttendanceTypeCountOfCrew.get(crew).getOrDefault(AttendanceType.ABSENCE, 0);
        Assertions.assertThat(actualAttendanceCount).isEqualTo(1);
        Assertions.assertThat(actualAbsenceCount).isEqualTo(0);
    }
}
