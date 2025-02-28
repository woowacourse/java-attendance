package domain.attendance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static domain.attendance.StudentStatus.*;
import static domain.attendance.StudentStatus.DISMISSAL;
import static org.assertj.core.api.Assertions.*;

class StudentStatusTest {
    @Nested
    class StudentStatusCalcTest{
        @DisplayName("5번 초과인 경우 제적")
        @ParameterizedTest
        @ValueSource(ints = {6,7,8,9,10,11,12})
        void isDismissal(int absenceCount){
            assertThat(calcStudentStatus(absenceCount)).isEqualTo(DISMISSAL);
        }

        @DisplayName("3번 이상인 경우 면담")
        @ParameterizedTest
        @ValueSource(ints = {3,4,5})
        void isCounseled(int absenceCount){
            assertThat(calcStudentStatus(absenceCount)).isEqualTo(COUNSELED);
        }

        @DisplayName("2번 이상인 경우 경고")
        @ParameterizedTest
        @ValueSource(ints = {2})
        void isWarning(int absenceCount){
            assertThat(calcStudentStatus(absenceCount)).isEqualTo(WARNING);
        }

        @DisplayName("그 아래인 경우 아무것도 아님")
        @ParameterizedTest
        @ValueSource(ints = {0,1})
        void isNone(int absenceCount){
            assertThat(calcStudentStatus(absenceCount)).isEqualTo(NONE);
        }
    }
}
