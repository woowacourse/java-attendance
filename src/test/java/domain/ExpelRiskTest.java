package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ExpelRiskTest {
    
    @Nested
    class 생성_테스트 {
        
        @ParameterizedTest
        @CsvSource({
                "0, 0", "0, 1", "0, 2", "0, 3", "0, 4", "0, 5",
                "1, 0", "1, 1", "1, 2",
        })
        void 정상_생성(int absentCount, int lateCount) {
            //given
            
            //when
            ExpelRisk result = ExpelRisk.of(absentCount, lateCount);
            
            //then
            Assertions.assertThat(result).isEqualTo(ExpelRisk.정상);
        }
        
        @ParameterizedTest
        @CsvSource({
                "0, 6", "0, 7", "0, 8",
                "1, 3", "1, 4", "1, 5",
                "2, 0", "2, 1", "2, 2",
        })
        void 경고_생성(int absentCount, int lateCount) {
            //given
            
            //when
            ExpelRisk result = ExpelRisk.of(absentCount, lateCount);
            
            //then
            Assertions.assertThat(result).isEqualTo(ExpelRisk.경고);
        }
        
        @ParameterizedTest
        @CsvSource({
                "5, 0", "5, 1", "5, 2",
                "4, 0", "4, 1", "4, 2", "4, 3", "4, 4", "4, 5",
                "3, 0", "3, 1", "3, 2", "3, 3", "3, 4", "3, 5", "3, 6", "3, 7", "3, 8",
                "2, 3", "2, 4", "2, 5", "2, 6", "2, 7", "2, 8", "2, 9", "2, 10", "2, 11",
                "1, 6", "1, 7", "1, 8", "1, 9", "1, 10", "1, 11", "1, 12", "1, 13", "1, 14",
                "0, 9", "0, 10", "0, 11", "0, 12", "0, 13", "0, 14", "0, 15", "0, 16", "0, 17",
        })
        void 면담_생성(int absentCount, int lateCount) {
            //given
            
            //when
            ExpelRisk result = ExpelRisk.of(absentCount, lateCount);
            
            //then
            Assertions.assertThat(result).isEqualTo(ExpelRisk.면담);
        }
        
        @ParameterizedTest
        @CsvSource({
                "5, 3", "5, 4", "5, 5",
                "4, 6", "4, 7", "4, 8",
                "3, 9", "3, 10", "3, 11",
                "2, 12", "2, 13", "2, 14",
                "1, 15", "1, 16", "1, 17",
                "0, 18", "0, 19", "0, 20",
        })
        void 제적_생성(int absentCount, int lateCount) {
            //given
            
            //when
            ExpelRisk result = ExpelRisk.of(absentCount, lateCount);
            
            //then
            Assertions.assertThat(result).isEqualTo(ExpelRisk.제적);
        }
    }
}
