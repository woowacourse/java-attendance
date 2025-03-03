package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("캠퍼스 운영 시간 테스트")
class CampusOperatingTimeTest {

    @ParameterizedTest(name = "{index} : {1}")
    @MethodSource("getNotInOperationTime")
    void 캠퍼스_운영시간_이내가_아니면_true를_반환한다(LocalTime inputTime, String message) {
        assertThat(CampusOperatingTime.notInOperation(inputTime)).isTrue();
    }

    static Stream<Arguments> getNotInOperationTime() {
        return Stream.of(
                Arguments.of(CampusOperatingTime.OPEN.getTime().minusNanos(1), "운영 시작 시간 1 나노초 전"),
                Arguments.of(CampusOperatingTime.OPEN.getTime().minusMinutes(1), "운영 시작 시간 1분 전"),
                Arguments.of(CampusOperatingTime.OPEN.getTime().minusHours(5), "운영 시작 시간 5시간 전"),
                Arguments.of(CampusOperatingTime.CLOSE.getTime().plusNanos(1), "운영 종료 시간 1 나노초 후"),
                Arguments.of(CampusOperatingTime.CLOSE.getTime().plusMinutes(1), "운영 종료 시간 1분 후"),
                Arguments.of(CampusOperatingTime.CLOSE.getTime().plusHours(5), "운영 종료 시간 5시간 후")
        );
    }

    @ParameterizedTest(name = "{index} : {1}")
    @MethodSource("getInOperationTime")
    void 캠퍼스_운영시간_이내라면_false를_반환한다(LocalTime inputTime, String message) {
        assertThat(CampusOperatingTime.notInOperation(inputTime)).isFalse();
    }

    static Stream<Arguments> getInOperationTime() {
        return Stream.of(
                Arguments.of(CampusOperatingTime.OPEN.getTime(), "운영 시작 시간"),
                Arguments.of(CampusOperatingTime.OPEN.getTime().plusNanos(1), "운영 시작 시간 1 나노초 후"),
                Arguments.of(CampusOperatingTime.OPEN.getTime().plusMinutes(1), "운영 시작 시간 1분 후"),
                Arguments.of(CampusOperatingTime.OPEN.getTime().plusHours(5), "운영 시작 시간 5시간 후"),
                Arguments.of(CampusOperatingTime.CLOSE.getTime(), "운영 종료 시간"),
                Arguments.of(CampusOperatingTime.CLOSE.getTime().minusNanos(1), "운영 종료 시간 1 나노초 전"),
                Arguments.of(CampusOperatingTime.CLOSE.getTime().minusMinutes(1), "운영 종료 시간 1분 전"),
                Arguments.of(CampusOperatingTime.CLOSE.getTime().minusHours(5), "운영 종료 시간 5시간 전")
        );
    }
}
