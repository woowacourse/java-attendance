package domain;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceStorageTest {

    @DisplayName("containsSameNickname() - 동일한 닉네임이 존재하면 True")
    @Test
    void containsSameNicknameTest1() {
        // given
        AttendanceStorage attendanceStorage = new AttendanceStorage();
        attendanceStorage.add(Crew.from("히스타"));

        // when
        boolean result = attendanceStorage.containsSameNickname("히스타");

        // then
        Assertions.assertThat(result).isTrue();
    }

    @DisplayName("containsSameNickname() - 존재하지 않는 닉네임이면 False")
    @Test
    void containsSameNicknameTest2() {
        // given
        AttendanceStorage attendanceStorage = new AttendanceStorage();
        attendanceStorage.add(Crew.from("히스타"));

        // when
        boolean result = attendanceStorage.containsSameNickname("히로");

        // then
        Assertions.assertThat(result).isFalse();
    }

    @DisplayName("containsSameHistoryOf() - 동일한 날짜와 Crew가 존재하면 True")
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
        boolean result = storage.containsSameHistoryOf(crew, comparedDateTime);

        // then
        Assertions.assertThat(result).isTrue();
    }

    @DisplayName("containsSameHistoryOf() - 다른 날짜이면 False")
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
        boolean result = storage.containsSameHistoryOf(crew, comparedDateTime);

        // then
        Assertions.assertThat(result).isFalse();
    }

    @DisplayName("replace() - 동일한 Crew와 날짜의 기록을 교체")
    @Test
    void replaceTest1() {
        // given
        Crew crew = Crew.from("히스타");
        AttendanceHistory attendanceHistory = AttendanceHistory.of(crew, LocalDateTime.of(2025, 2, 26, 0, 0));
        AttendanceStorage storage = new AttendanceStorage();
        storage.add(crew);
        storage.add(attendanceHistory);

        AttendanceHistory newAttendanceHistory = AttendanceHistory.of(crew, LocalDateTime.of(2025, 2, 26, 1, 0));

        // when
        storage.replace(newAttendanceHistory);

        // then
        int actualHour = storage.getAttendanceHistory(0).getAttendanceDateTime().getLocalDateTime().getHour();
        Assertions.assertThat(actualHour).isEqualTo(1);
    }

    @DisplayName("replace() - 존재하지 않는 기록 교체 시 예외 발생")
    @Test
    void replaceTest2() {
        // given
        Crew crew = Crew.from("히스타");
        AttendanceHistory attendanceHistory = AttendanceHistory.of(crew, LocalDateTime.of(2025, 2, 26, 0, 0));
        AttendanceStorage storage = new AttendanceStorage();
        storage.add(crew);
        storage.add(attendanceHistory);

        AttendanceHistory newAttendanceHistory = AttendanceHistory.of(crew, LocalDateTime.of(2025, 2, 27, 1, 0));

        // when & then
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> storage.replace(newAttendanceHistory));
    }

    @DisplayName("indexOfSameDateAndCrew() - 동일한 Crew와 날짜의 기록이 존재하면 인덱스 반환")
    @Test
    void indexOfSameDateAndCrewTest1() {
        // given
        Crew crew = Crew.from("히스타");
        AttendanceHistory attendanceHistory = AttendanceHistory.of(crew, LocalDateTime.of(2025, 2, 26, 0, 0));
        AttendanceHistory comparedAttendanceHistory = AttendanceHistory.of(crew, LocalDateTime.of(2025, 2, 26, 1, 30));
        AttendanceStorage storage = new AttendanceStorage();
        storage.add(crew);
        storage.add(attendanceHistory);

        // when
        int index = storage.indexOfSameDateAndCrew(comparedAttendanceHistory);

        // then
        Assertions.assertThat(index).isEqualTo(0);
    }

    @DisplayName("indexOfSameDateAndCrew() - 다른 날짜이면 예외 발생")
    @Test
    void indexOfSameDateAndCrewTest2() {
        // given
        Crew crew = Crew.from("히스타");
        AttendanceHistory attendanceHistory = AttendanceHistory.of(crew, LocalDateTime.of(2025, 2, 26, 0, 0));
        AttendanceHistory comparedAttendanceHistory = AttendanceHistory.of(crew, LocalDateTime.of(2025, 2, 27, 1, 30));
        AttendanceStorage storage = new AttendanceStorage();
        storage.add(crew);
        storage.add(attendanceHistory);

        // when & then
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> storage.indexOfSameDateAndCrew(comparedAttendanceHistory));
    }

    @DisplayName("indexOfSameDateAndCrew() - 다른 Crew이면 예외 발생")
    @Test
    void indexOfSameDateAndCrewTest3() {
        // given
        Crew crew = Crew.from("히스타");
        AttendanceHistory attendanceHistory = AttendanceHistory.of(crew, LocalDateTime.of(2025, 2, 26, 0, 0));
        AttendanceHistory comparedAttendanceHistory = AttendanceHistory.of(Crew.from("히로"), LocalDateTime.of(2025, 2, 26, 0, 0));
        AttendanceStorage storage = new AttendanceStorage();
        storage.add(crew);
        storage.add(attendanceHistory);

        // when & then
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> storage.indexOfSameDateAndCrew(comparedAttendanceHistory));
    }

    @DisplayName("getAllHistoriesOf() - Crew의 모든 출석 기록 반환")
    @Test
    void getAllHistoriesFromTest1() {
        // given
        Crew crew = Crew.from("히스타");
        AttendanceHistory attendanceHistory = AttendanceHistory.of(crew, LocalDateTime.of(2025, 2, 26, 0, 0));
        AttendanceStorage storage = new AttendanceStorage();
        storage.add(crew);
        storage.add(attendanceHistory);

        // when
        int resultSize = storage.getAllHistoriesOf(crew, 30).size();

        // then
        Assertions.assertThat(resultSize).isEqualTo(1);
    }

    @DisplayName("getAllHistoriesOf() - 존재하지 않는 Crew의 기록 반환 시 빈 리스트")
    @Test
    void getAllHistoriesFromTest2() {
        // given
        Crew crew = Crew.from("히스타");
        AttendanceHistory attendanceHistory = AttendanceHistory.of(crew, LocalDateTime.of(2025, 2, 26, 0, 0));
        AttendanceStorage storage = new AttendanceStorage();
        storage.add(crew);
        storage.add(attendanceHistory);

        // when
        int resultSize = storage.getAllHistoriesOf(Crew.from("히로"), 30).size();

        // then
        Assertions.assertThat(resultSize).isEqualTo(0);
    }
}
