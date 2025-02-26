package domain;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

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
}
