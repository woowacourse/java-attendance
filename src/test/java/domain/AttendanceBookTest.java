package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {

    private AttendanceBook attendanceBook;

    @BeforeEach
    void setUp() {
        attendanceBook = AttendanceBook.create();
    }

    @Test
    @DisplayName("존재하지 않는 크루이므로 예외가 발생한다.")
    void test1() {
        //should
        assertThatIllegalArgumentException().isThrownBy(() -> attendanceBook.getAttendancePaperByCrewName("윌슨"));
    }

    
    @Test
    @DisplayName("제적 위험자를 확인 한다.")
    void test2() {
        //given
        //when
        final List<AttendancePaper> sortedPenaltyAttendancePapers = attendanceBook.getSortedPenaltyAttendancePapers();
        final AttendancePaper first = sortedPenaltyAttendancePapers.getFirst();
        //then
        assertThat(first.getCrewName()).isEqualTo("빙티");
        
    }
}
