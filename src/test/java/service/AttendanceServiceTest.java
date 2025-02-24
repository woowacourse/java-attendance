package service;

import domain.CrewGroup;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import presentation.InputParser;
import presentation.view.AttendanceFileInputView;

class AttendanceServiceTest {
    @DisplayName("크루원 정보를 초기화한다.")
    @Test
    void test() {
        // given
        AttendanceFileInputView fileInputView = new AttendanceFileInputView();
        Map<String, List<String>> attendanceFileInfo = fileInputView.getAttendanceFileInput();
        CrewService attendanceService = new CrewService();
        Map<String, List<LocalDateTime>> map = InputParser.getFileAttendanceInfo(attendanceFileInfo);

        // when
        CrewGroup crewGroup = attendanceService.createCrewGroup(map);

        //then
        Assertions.assertThat(crewGroup.findCrew("빙봉").getName()).isEqualTo("빙봉");
    }
}