package controller.menu;

import controller.dto.AttendanceResultDtoConverter;
import controller.dto.CrewDtoConverter;
import domain.attendance.AttendanceBook;
import domain.attendance.AttendanceResult;
import domain.crew.Crew;
import dto.AttendanceResultDto;
import dto.CrewDto;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import view.OutputView;

public class ExpulsionRiskController implements AttendanceMenuController {

    private final AttendanceBook attendanceBook;

    public ExpulsionRiskController(AttendanceBook attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    @Override
    public void run(LocalDate runDate) {
        List<Map.Entry<Crew, AttendanceResult>> expulsionRiskCrews = attendanceBook.findExpulsionRiskCrews(runDate);
        List<Map.Entry<CrewDto, AttendanceResultDto>> expulsionRiskCrewsDtos = new ArrayList<>();
        for (Map.Entry<Crew, AttendanceResult> entry : expulsionRiskCrews) {
            CrewDto crewDto = CrewDtoConverter.toDto(entry.getKey());
            AttendanceResultDto attendanceResultDto = AttendanceResultDtoConverter.toDto(entry.getValue());
            expulsionRiskCrewsDtos.add(new AbstractMap.SimpleEntry<>(crewDto, attendanceResultDto));
        }
        OutputView.printExpulsionRiskCrews(expulsionRiskCrewsDtos);
    }
}
