import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.Attend;
import domain.AttendanceBook;
import domain.Current;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {

    @Test
    @DisplayName("대상 닉네임이 존재하지 않으면 예외를 던진다")
    void throwExceptionNotExistName() {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        String name = "플린트";
        Attend attend = new Attend(Current.TODAY.getDate());

        //when & then
        Assertions.assertThatThrownBy(() -> attendanceBook.addAttend(name, attend));
    }

    @Test
    @DisplayName("출석부에 닉네임을 등록한다")
    void registerName() {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        String name = "플린트";

        //when & then
        assertDoesNotThrow(() -> attendanceBook.register(name));
    }

    @Test
    @DisplayName("대상 닉네임의 출석을 추가한다")
    void addAttendUsingName() {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        String name = "플린트";
        Attend attend = new Attend(Current.TODAY.getDate());
        attendanceBook.register(name);

        //when & then
        assertDoesNotThrow(() -> attendanceBook.addAttend(name, attend));
    }
}
