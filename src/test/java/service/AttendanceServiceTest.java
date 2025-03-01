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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import view.AttendanceFileReader;

class AttendanceServiceTest {
    public static AttendanceHistory makeHistoryOf(Crew crew, int day) {
        return AttendanceHistory.of(Crew.from("히스타"), LocalDateTime.of(LocalDate.of(2024, 12, day), LocalTime.now()));
    }

    public static LocalDateTime makeLocalDateTimeOf(int day) {
        return LocalDateTime.of(LocalDate.of(2024, 12, day), LocalTime.now());
    }

    @Test
    void checkNicknameRegistered1() throws FileNotFoundException {
        // given
        AttendanceStorage attendanceStorage = new AttendanceStorage();
        AttendanceFileReader.applyAttendanceFileTo(attendanceStorage);
        AttendanceService attendanceService = new AttendanceService(attendanceStorage);

        // when
        boolean actual = attendanceService.checkNicknameRegistered("히스타");

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void checkNicknameRegistered2() throws FileNotFoundException {
        // given
        AttendanceStorage attendanceStorage = new AttendanceStorage();
        AttendanceFileReader.applyAttendanceFileTo(attendanceStorage);
        AttendanceService attendanceService = new AttendanceService(attendanceStorage);

        // when
        boolean actual = attendanceService.checkNicknameRegistered("빙티");

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void checkHistoryAlreadyExistsTest1() {
        // given
        Crew crew = Crew.from("히스타");
        AttendanceStorage storage = new AttendanceStorage();
        AttendanceHistory history = makeHistoryOf(crew, 1);
        storage.add(history);
        AttendanceService attendanceService = new AttendanceService(storage);

        // when
        boolean actual = attendanceService.checkHistoryAlreadyExists(crew, makeLocalDateTimeOf(1));

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void checkHistoryAlreadyExistsTest2() {
        // given
        Crew crew = Crew.from("히스타");
        Crew comparedCrew = Crew.from("히로");
        AttendanceStorage storage = new AttendanceStorage();
        AttendanceHistory history = makeHistoryOf(crew, 1);
        storage.add(history);
        AttendanceService attendanceService = new AttendanceService(storage);

        // when
        boolean actual = attendanceService.checkHistoryAlreadyExists(comparedCrew, makeLocalDateTimeOf(1));

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 7, 8, 14, 15, 21, 22, 25, 28, 29})
    void checkRestDayTest(int day) {
        // given
        AttendanceStorage storage = new AttendanceStorage();
        AttendanceService attendanceService = new AttendanceService(storage);

        // when
        boolean actual = attendanceService.checkRestDay(LocalDate.of(2024, 12, day));

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 9, 16, 23, 30})
    void checkRestDayTest2(int day) {
        // given
        AttendanceStorage storage = new AttendanceStorage();
        AttendanceService attendanceService = new AttendanceService(storage);

        // when
        boolean actual = attendanceService.checkRestDay(LocalDate.of(2024, 12, day));

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void addAttendanceHistoryTest() {
        // given
        AttendanceStorage storage = new AttendanceStorage();
        AttendanceService attendanceService = new AttendanceService(storage);
        Crew crew = Crew.from("히스타");

        // when
        attendanceService.addAttendanceHistory(crew, makeLocalDateTimeOf(1));

        // then
        Assertions.assertThat(attendanceService.checkHistoryAlreadyExists(crew, makeLocalDateTimeOf(1))).isTrue();
    }

    @Test
    void addAttendanceHistoryTest2() {
        // given
        AttendanceStorage storage = new AttendanceStorage();
        AttendanceService attendanceService = new AttendanceService(storage);
        Crew crew = Crew.from("히스타");

        // when
        attendanceService.addAttendanceHistory(crew, makeLocalDateTimeOf(1));

        // then
        Assertions.assertThat(attendanceService.checkHistoryAlreadyExists(crew, makeLocalDateTimeOf(2))).isFalse();
    }

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

    @Test
    void getAllHistoriesTest() {
        // given
        AttendanceStorage storage = new AttendanceStorage();
        AttendanceService attendanceService = new AttendanceService(storage);
        Crew crew = Crew.from("히스타");
        // 2부터 시작 이유: 1일이 휴일임.
        attendanceService.addAttendanceHistory(crew, makeLocalDateTimeOf(2));

        // when
        AttendanceStatusesOfCrewDto allHistories = attendanceService.getAllHistories(crew, 3);

        // then
        Assertions.assertThat(allHistories.attendanceStatusDtos().getFirst().day()).isEqualTo(2);
    }

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
