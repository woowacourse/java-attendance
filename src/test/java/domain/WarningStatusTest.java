package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class WarningStatusTest {
    @ParameterizedTest
    @DisplayName("제적 위험이 없는 상태의 출결 횟수가 주어졌을 때 WarningStatus.CLEAR를 반환한다")
    @CsvSource(value = {"0,0,0", "0,1,0", "0,2,0", "0,3,0", "0,4,0", "0,5,0"
            , "0,0,1", "0,1,1", "0,2,1"})
    void return_CLEAR_WarningStatus_by_clear_attendCount(int attend, int late, int absence) {
        // given
        AttendCount attendCount = new AttendCount(attend, late, absence);

        // when
        WarningStatus warningStatus = WarningStatus.judgeWarningStatus(attendCount);
        
        // then
        Assertions.assertThat(warningStatus).isEqualTo(WarningStatus.CLEAR);
    }

    @ParameterizedTest
    @DisplayName("경고 상태의 출결 횟수가 주어졌을 때 WarningStatus.WARNING를 반환한다")
    @CsvSource(value = {"0,0,2", "0,1,2", "0,2,2"
            , "0,3,1", "0,4,1", "0,5,1"
            , "0,6,0", "0,7,0", "0,8,0",})
    void return_WARNING_WarningStatus_by_warning_attendCount(int attend, int late, int absence) {
        // given
        AttendCount attendCount = new AttendCount(attend, late, absence);

        // when
        WarningStatus warningStatus = WarningStatus.judgeWarningStatus(attendCount);

        // then
        Assertions.assertThat(warningStatus).isEqualTo(WarningStatus.WARNING);
    }

    @ParameterizedTest
    @DisplayName("면담 상태의 출결 횟수가 주어졌을 때 WarningStatus.INTERVIEW를 반환한다")
    @CsvSource(value = {"0,9,0", "0,10,0", "0,11,0", "0,12,0", "0,13,0", "0,14,0", "0,15,0", "0,16,0", "0,17,0"
            , "0,6,1", "0,7,1", "0,8,1", "0,9,1", "0,10,1", "0,11,1", "0,12,1", "0,13,1", "0,14,1"
            , "0,3,2", "0,4,2", "0,5,2", "0,6,2", "0,7,2", "0,8,2", "0,9,2", "0,10,2", "0,11,2"
            , "0,0,3", "0,1,3", "0,2,3", "0,3,3", "0,4,3", "0,5,3", "0,6,3", "0,7,3", "0,8,3"
            , "0,0,4", "0,1,4", "0,2,4", "0,3,4", "0,4,4", "0,5,4"
            , "0,0,5", "0,1,5", "0,2,5"})
    void return_INTERVIEW_WarningStatus_by_interview_attendCount(int attend, int late, int absence) {
        // given
        AttendCount attendCount = new AttendCount(attend, late, absence);

        // when
        WarningStatus warningStatus = WarningStatus.judgeWarningStatus(attendCount);

        // then
        Assertions.assertThat(warningStatus).isEqualTo(WarningStatus.INTERVIEW);
    }

    @ParameterizedTest
    @DisplayName("제적 상태의 출결 횟수가 주어졌을 때 WarningStatus.EXPEL를 반환한다")
    @CsvSource(value = {"0,0,6", "0,18,0"})
    void return_EXPEL_WarningStatus_by_expel_attendCount(int attend, int late, int absence) {
        // given
        AttendCount attendCount = new AttendCount(attend, late, absence);

        // when
        WarningStatus warningStatus = WarningStatus.judgeWarningStatus(attendCount);

        // then
        Assertions.assertThat(warningStatus).isEqualTo(WarningStatus.EXPEL);
    }
}