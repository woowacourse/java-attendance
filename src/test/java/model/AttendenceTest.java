package model;

import attendance.model.Attendence;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendenceTest {

    @DisplayName("출석을 생성한다.")
    @Test
    void test1 (){
        Attendence attendence = Attendence.출석;
        Assertions.assertThat(attendence).isNotNull();
    }

    @DisplayName("출석을 생성한다.")
    @Test
    void test2 (){
        Attendence attendence = Attendence.지각;
        Assertions.assertThat(attendence).isNotNull();
    }

    @DisplayName("출석을 생성한다.")
    @Test
    void test3 (){
        Attendence attendence = Attendence.결석;
        Assertions.assertThat(attendence).isNotNull();
    }

    @Test
    void test4() {
            Attendence attendence = Attendence.from(5);
            Assertions.assertThat(attendence).isEqualTo(Attendence.출석);
    }

    @Test
    void test5() {
        Attendence attendence = Attendence.from(6);
        Assertions.assertThat(attendence).isEqualTo(Attendence.지각);
    }

    @Test
    void test6() {
        Attendence attendence = Attendence.from(31);
        Assertions.assertThat(attendence).isEqualTo(Attendence.결석);
    }

}
