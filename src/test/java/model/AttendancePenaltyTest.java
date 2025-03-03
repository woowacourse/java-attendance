package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendancePenaltyTest {

    @Test
    @DisplayName("결석 횟수에 따른 면담 값 가져오기 테스트")
    void 결석_횟수에_따른_면담_상태_가져오기_테스트(){
        int count = 5;
        AttendancePenalty expect = AttendancePenalty.COUNSELING;
        AttendancePenalty result = AttendancePenalty.findPenaltyByAbsentCount(count);
        assertEquals(expect, result);
    }

    @Test
    @DisplayName("결석 횟수에 따른 제적 패널티 값 가져오기 테스트")
    void 결석_횟수에_따른_제적_상태_가져오기_테스트(){
        int count = 6;
        AttendancePenalty expect = AttendancePenalty.EXPULSION;
        AttendancePenalty result = AttendancePenalty.findPenaltyByAbsentCount(count);
        assertEquals(expect, result);
    }


}