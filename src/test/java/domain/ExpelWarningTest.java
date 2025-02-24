package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ExpelWarningTest {
    
    @Nested
    class 제적_위험도_계산 {
        
        @ParameterizedTest
        @CsvSource({
                "0, 6", "1, 6", "2, 6",
                "3, 5", "4, 5", "5, 5",
                "6, 4", "7, 4", "8, 4",
        })
        void _3번_지각을_1결석으로_계산하여_결석횟수가_5회_초과면_제적이다(int lateCount, int absentCount) {
            //given
            
            //when
            var result = ExpelWarning.of(lateCount, absentCount);
            
            //then
            Assertions.assertThat(result).isEqualTo(ExpelWarning.제적);
        }
        
        @ParameterizedTest
        @CsvSource({
                "0, 5", "1, 5", "2, 5",
                "3, 4", "4, 4", "5, 4",
                "6, 3", "7, 3", "8, 3",
        })
        void _3번_지각을_1결석으로_계산하여_결석횟수가_5회_이하_3회_이상이면_면담이다(int lateCount, int absentCount) {
            //given
            
            //when
            var result = ExpelWarning.of(lateCount, absentCount);
            
            //then
            Assertions.assertThat(result).isEqualTo(ExpelWarning.면담);
        }
        
        @ParameterizedTest
        @CsvSource({
                "0, 2", "1, 2", "2, 2",
                "3, 1", "4, 1", "5, 1",
                "6, 0", "7, 0", "8, 0"
        })
        void _3번_지각을_1결석으로_계산하여_결석횟수가_2회이면_경고이다(int lateCount, int absentCount) {
            //given
            
            //when
            var result = ExpelWarning.of(lateCount, absentCount);
            
            //then
            Assertions.assertThat(result).isEqualTo(ExpelWarning.경고);
        }
        
        @ParameterizedTest
        @CsvSource({
                "0, 1", "1, 1", "2, 1",
                "3, 0", "4, 0", "5, 0"
        })
        void _3번_지각을_1결석으로_계산하여_결석횟수가_1회_이하면_정상이다(int lateCount, int absentCount) {
            //given
            
            //when
            var result = ExpelWarning.of(lateCount, absentCount);
            
            //then
            Assertions.assertThat(result).isEqualTo(ExpelWarning.정상);
        }
    }
    
}
