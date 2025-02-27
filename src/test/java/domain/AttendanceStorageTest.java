package domain;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceStorageTest {
    @Test
    void containsSameNicknameTest1() {
        // given
        AttendanceStorage attendanceStorage = new AttendanceStorage();
        attendanceStorage.add(Crew.from("히스타"));

        // when
        boolean actual = attendanceStorage.containsSameNickname("히스타");

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void containsSameNicknameTest2() {
        // given
        AttendanceStorage attendanceStorage = new AttendanceStorage();
        attendanceStorage.add(Crew.from("히스타"));

        // when
        boolean actual = attendanceStorage.containsSameNickname("히로");

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void containsSameHistoryOfTest1() {
        // given
        Crew crew = Crew.from("히스타");
        AttendanceHistory attendanceHistory = AttendanceHistory.of(crew, LocalDateTime.of(2025, 2, 26, 0, 0));
        LocalDateTime comparedDateTime = LocalDateTime.of(2025, 2, 26, 0, 0);
        AttendanceStorage storage = new AttendanceStorage();
        storage.add(crew);
        storage.add(attendanceHistory);

        // when
        boolean actualResult = storage.containsSameHistoryOf(crew, comparedDateTime);

        // then
        Assertions.assertThat(actualResult).isTrue();
    }

    @Test
    void containsSameHistoryOfTest2() {
        // given
        Crew crew = Crew.from("히스타");
        AttendanceHistory attendanceHistory = AttendanceHistory.of(crew, LocalDateTime.of(2025, 2, 26, 0, 0));
        LocalDateTime comparedDateTime = LocalDateTime.of(2025, 2, 27, 0, 0);
        AttendanceStorage storage = new AttendanceStorage();
        storage.add(crew);
        storage.add(attendanceHistory);

        // when
        boolean actualResult = storage.containsSameHistoryOf(crew, comparedDateTime);

        // then
        Assertions.assertThat(actualResult).isFalse();
    }

    @Test
    void replaceTest1() {
        Crew crew = Crew.from("히스타");
        AttendanceHistory attendanceHistory = AttendanceHistory.of(crew, LocalDateTime.of(2025, 2, 26, 0, 0));
        AttendanceStorage storage = new AttendanceStorage();
        storage.add(crew);
        storage.add(attendanceHistory);

        AttendanceHistory newAttendanceHistory = AttendanceHistory.of(crew, LocalDateTime.of(2025, 2, 26, 1, 0));
        storage.replace(newAttendanceHistory);

        Assertions.assertThat(storage.getAttendanceHistory(0).getDateTime().getLocalDateTime().getHour() == 1).isTrue();
    }

    @Test
    void replaceTest2() {
        Crew crew = Crew.from("히스타");
        AttendanceHistory attendanceHistory = AttendanceHistory.of(crew, LocalDateTime.of(2025, 2, 26, 0, 0));
        AttendanceStorage storage = new AttendanceStorage();
        storage.add(crew);
        storage.add(attendanceHistory);

        AttendanceHistory newAttendanceHistory = AttendanceHistory.of(crew, LocalDateTime.of(2025, 2, 27, 1, 0));
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> storage.replace(newAttendanceHistory));
    }

    @Test
    void indexOfSameDateAndCrewTest1() {
        Crew crew = Crew.from("히스타");
        AttendanceHistory attendanceHistory = AttendanceHistory.of(crew, LocalDateTime.of(2025, 2, 26, 0, 0));
        AttendanceHistory comparedAttendanceHistory = AttendanceHistory.of(crew, LocalDateTime.of(2025, 2, 26, 1, 30));
        AttendanceStorage storage = new AttendanceStorage();
        storage.add(crew);
        storage.add(attendanceHistory);

        int expected = storage.indexOfSameDateAndCrew(comparedAttendanceHistory);
        Assertions.assertThat(expected).isEqualTo(0);
    }

    @Test
    void indexOfSameDateAndCrewTest2() {
        Crew crew = Crew.from("히스타");
        AttendanceHistory attendanceHistory = AttendanceHistory.of(crew, LocalDateTime.of(2025, 2, 26, 0, 0));
        AttendanceHistory comparedAttendanceHistory = AttendanceHistory.of(crew, LocalDateTime.of(2025, 2, 27, 1, 30));
        AttendanceStorage storage = new AttendanceStorage();
        storage.add(crew);
        storage.add(attendanceHistory);

        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> storage.indexOfSameDateAndCrew(comparedAttendanceHistory));
    }

    @Test
    void indexOfSameDateAndCrewTest3() {
        Crew crew = Crew.from("히스타");
        AttendanceHistory attendanceHistory = AttendanceHistory.of(crew, LocalDateTime.of(2025, 2, 26, 0, 0));
        AttendanceHistory comparedAttendanceHistory = AttendanceHistory.of(Crew.from("히로"), LocalDateTime.of(2025, 2, 26, 0, 0));
        AttendanceStorage storage = new AttendanceStorage();
        storage.add(crew);
        storage.add(attendanceHistory);

        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> storage.indexOfSameDateAndCrew(comparedAttendanceHistory));
    }
}
