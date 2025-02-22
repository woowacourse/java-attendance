package attendance.model;

import attendance.TestUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class WarningTest {

    @Test
    void 크루가_2번에서_4번결석한_경우_경고를_받는다() {
        //given
        Crew crew = new Crew("빙티");
        crew.attend(TestUtil.creatAttendanceDetail(2024, 12, 3, 13, 0));
        crew.attend(TestUtil.creatAttendanceDetail(2024, 12, 4, 13, 7));

        //when
        AttendanceWarning warning = AttendanceWarning.from(crew);

        //then
        Assertions.assertThat(warning).isEqualTo(AttendanceWarning.WARNING);
    }

    @Test
    void 크루가_3번에서_5번_결석한_경우_면답를_받는다() {
        //given
        Crew crew = new Crew("빙티");
        crew.attend(TestUtil.creatAttendanceDetail(2024, 12, 3, 17, 2));
        crew.attend(TestUtil.creatAttendanceDetail(2024, 12, 4, 17, 2));
        crew.attend(TestUtil.creatAttendanceDetail(2024, 12, 5, 17, 2));

        //when
        AttendanceWarning warning = AttendanceWarning.from(crew);

        //then
        Assertions.assertThat(warning).isEqualTo(AttendanceWarning.NEED_MEETING);
    }

    @Test
    void 크루가_6이상_결석할_경우_제적이다() {
        //given
        Crew crew = new Crew("빙티");
        crew.attend(TestUtil.creatAttendanceDetail(2024, 12, 3, 17, 2));
        crew.attend(TestUtil.creatAttendanceDetail(2024, 12, 4, 17, 2));
        crew.attend(TestUtil.creatAttendanceDetail(2024, 12, 5, 17, 2));
        crew.attend(TestUtil.creatAttendanceDetail(2024, 12, 6, 17, 2));
        crew.attend(TestUtil.creatAttendanceDetail(2024, 12, 10, 17, 2));
        crew.attend(TestUtil.creatAttendanceDetail(2024, 12, 11, 17, 2));

        //when
        AttendanceWarning warning = AttendanceWarning.from(crew);

        //then
        Assertions.assertThat(warning).isEqualTo(AttendanceWarning.OUT);
    }

    @Test
    void 크루가_2번_미만_결석한_경우_해당없음이다() {
        //given
        Crew crew = new Crew("빙티");
        crew.attend(TestUtil.creatAttendanceDetail(2024, 12, 3, 17, 2));

        //when
        AttendanceWarning warning = AttendanceWarning.from(crew);

        //then
        Assertions.assertThat(warning).isEqualTo(AttendanceWarning.NONE);
    }
}
