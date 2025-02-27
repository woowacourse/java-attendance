package attendance.domain;

import static attendance.fixture.TestFixture.makeAbsent;
import static attendance.fixture.TestFixture.makeAttendance;
import static attendance.fixture.TestFixture.makeDefaultTime;
import static attendance.fixture.TestFixture.makeLate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewInitializerTest {

    @Test
    @DisplayName("크루 기록을 초기화한다")
    void initialize() {
        // Given
        CrewInitializer crewInitializer = makeCrewInitializer();

        Map<String, List<LocalDateTime>> histories = new HashMap<>(Map.of(
                "빙티", List.of(makeAttendance(2), makeAttendance(3)),
                "이든", List.of(makeAttendance(2), makeLate(3)),
                "쿠키", List.of(makeAttendance(2)),
                "빙봉", List.of(makeAbsent(2), makeAttendance(3)),
                "짱수", List.of()
        ));
        CrewHistory bingtyCrewHistory = makeBingtyCrewHistory();
        CrewHistory edenCrewHistory = makeEdenCrewHistory();
        CrewHistory cookieCrewHistory = makeCookieCrewHistory();
        CrewHistory bingbongCrewHistory = makeBingbongCrewHistory();
        CrewHistory zzangsuCrewHistory = makeZzangsuCrewHistory();

        // When
        CrewHistories crewHistories = crewInitializer.initialize(histories);

        // Then
        assertAll(
                () -> assertThat(crewHistories.findCrewByNickname("빙티").getAttendance()).isEqualTo(
                        bingtyCrewHistory.getAttendance()),
                () -> assertThat(crewHistories.findCrewByNickname("이든").getAttendance()).isEqualTo(
                        edenCrewHistory.getAttendance()),
                () -> assertThat(crewHistories.findCrewByNickname("쿠키").getAttendance()).isEqualTo(
                        cookieCrewHistory.getAttendance()),
                () -> assertThat(crewHistories.findCrewByNickname("빙봉").getAttendance()).isEqualTo(
                        bingbongCrewHistory.getAttendance()),
                () -> assertThat(crewHistories.findCrewByNickname("짱수").getAttendance()).isEqualTo(
                        zzangsuCrewHistory.getAttendance())
        );
    }

    private CrewInitializer makeCrewInitializer() {
        Campus campus = new Campus();
        Clock clock = Clock.fixed(
                LocalDateTime.of(2024, 12, 4, 10, 0).toInstant(ZoneOffset.UTC),
                ZoneId.of("Asia/Tokyo"));
        return new CrewInitializer(campus, clock);
    }

    private CrewHistory makeBingtyCrewHistory() {
        LocalDateTime attendance1 = makeAttendance(2);
        LocalDateTime attendance2 = makeAttendance(3);
        return new CrewHistory(Map.of(LocalDate.from(attendance1), attendance1,
                LocalDate.from(attendance2), attendance2));
    }

    private CrewHistory makeEdenCrewHistory() {
        LocalDateTime attendance1 = makeAttendance(2);
        LocalDateTime attendance2 = makeLate(3);
        return new CrewHistory(Map.of(LocalDate.from(attendance1), attendance1,
                LocalDate.from(attendance2), attendance2));
    }

    private CrewHistory makeCookieCrewHistory() {
        LocalDateTime attendance1 = makeAttendance(2);
        LocalDateTime attendance2 = makeDefaultTime(3);
        return new CrewHistory(Map.of(LocalDate.from(attendance1), attendance1,
                LocalDate.from(attendance2), attendance2));
    }

    private CrewHistory makeBingbongCrewHistory() {
        LocalDateTime attendance1 = makeAbsent(2);
        LocalDateTime attendance2 = makeAttendance(3);
        return new CrewHistory(Map.of(LocalDate.from(attendance1), attendance1,
                LocalDate.from(attendance2), attendance2));
    }

    private CrewHistory makeZzangsuCrewHistory() {
        LocalDateTime attendance1 = makeDefaultTime(2);
        LocalDateTime attendance2 = makeDefaultTime(3);
        return new CrewHistory(Map.of(LocalDate.from(attendance1), attendance1,
                LocalDate.from(attendance2), attendance2));
    }
}
