package service;

import domain.CrewGroup;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import presentation.InputParser;
import presentation.view.FileInputView;

class AttendanceServiceTest {
    @DisplayName("크루원 정보를 초기화한다.")
    @Test
    void test() {
        // given
        FileInputView fileInputView = new FileInputView();
        Map<String, List<String>> attendanceFileInfo = fileInputView.getFileInput();
        AttendanceService attendanceService = new AttendanceService();
        Map<String, List<LocalDateTime>> map = InputParser.getFileAttendanceInfo(attendanceFileInfo);

        // when
        CrewGroup crewGroup = attendanceService.createCrewGroup(map);

        //then
        Assertions.assertThat(crewGroup.findCrew("빙봉").getName()).isEqualTo("빙봉");
    }

}