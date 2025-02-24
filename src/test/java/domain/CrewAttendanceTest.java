package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class CrewAttendanceTest {

    @Test
    void 출석을_추가한다() {
        // given
        Crew crew = new Crew("홍길동");
        WorkDateTime workDateTime = new WorkDateTime(new WorkDate(2024, 12, 2), new WorkTime(9, 0));
        Attendance attendance = new Attendance(Map.of(workDateTime.getDate(), new WorkTime(null, null)));
        CrewAttendance crewAttendance = new CrewAttendance(crew, attendance);

        // when
        crewAttendance.addAttendance(workDateTime);

        // then
        assertThat(crewAttendance.retrieveAttendance(workDateTime.getDate())).isEqualTo(workDateTime);
    }

    @Test
    void 출석을_수정한다() {
        // given
        Crew crew = new Crew("홍길동");
        WorkDateTime originalWorkDateTime = new WorkDateTime(new WorkDate(2024, 12, 2), new WorkTime(9, 0));
        WorkDateTime updatedWorkDateTime = new WorkDateTime(originalWorkDateTime.getDate(), new WorkTime(10, 5));

        Attendance attendance = new Attendance(Map.of(originalWorkDateTime.getDate(), originalWorkDateTime.getTime()));
        CrewAttendance crewAttendance = new CrewAttendance(crew, attendance);

        // when
        crewAttendance.updateAttendance(updatedWorkDateTime);

        // then
        assertThat(crewAttendance.retrieveAttendance(updatedWorkDateTime.getDate()))
                .isEqualTo(updatedWorkDateTime);
    }

    @Test
    void 출석_수정시_출석이_없으면_예외가_발생한다() {
        // given
        Crew crew = new Crew("홍길동");
        WorkDateTime originalWorkDateTime = new WorkDateTime(new WorkDate(2024, 12, 2), new WorkTime(null, null));
        WorkDateTime updateWorkDateTime = new WorkDateTime(originalWorkDateTime.getDate(), new WorkTime(10, 5));
        Attendance attendance = new Attendance(Map.of(originalWorkDateTime.getDate(), originalWorkDateTime.getTime()));
        CrewAttendance crewAttendance = new CrewAttendance(crew, attendance);

        // when & then
        assertThatThrownBy(() -> crewAttendance.updateAttendance(updateWorkDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 날짜의 출석 정보가 없습니다.");
    }

    @Test
    void 특정_날짜의_출석_정보를_조회한다() {
        // given
        Crew crew = new Crew("홍길동");
        WorkDateTime workDateTime = new WorkDateTime(new WorkDate(2024, 12, 2), new WorkTime(9, 0));
        Attendance attendance = new Attendance(Map.of(workDateTime.getDate(), workDateTime.getTime()));
        CrewAttendance crewAttendance = new CrewAttendance(crew, attendance);

        // when
        WorkDateTime retrievedWorkDateTime = crewAttendance.retrieveAttendance(workDateTime.getDate());

        // then
        assertThat(retrievedWorkDateTime).isEqualTo(workDateTime);
    }

    @Test
    void 출석_정보들을_날짜순으로_조회한다() {
        // given
        Crew crew = new Crew("홍길동");
        WorkDateTime workDateTime1 = new WorkDateTime(new WorkDate(2024, 12, 2), new WorkTime(9, 0));
        WorkDateTime workDateTime2 = new WorkDateTime(new WorkDate(2024, 12, 3), new WorkTime(10, 0));

        Attendance attendance = new Attendance(Map.of(
                workDateTime1.getDate(), workDateTime1.getTime(),
                workDateTime2.getDate(), workDateTime2.getTime()
        ));
        CrewAttendance crewAttendance = new CrewAttendance(crew, attendance);

        // when
        List<WorkDateTime> workDateTimes = crewAttendance.retrieveAttendanceOrderByDate();

        // then
        assertThat(workDateTimes).containsExactly(workDateTime1, workDateTime2);
    }
}
