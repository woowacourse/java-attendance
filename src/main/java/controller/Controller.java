package controller;

import domain.Attendance;
import domain.Crew;
import domain.CrewGroup;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import service.CrewLoader;
import service.DayComparator;
import view.InputView;
import view.OutputView;
import view.dto.AlertCrewDTO;
import view.dto.AlertCrewsDTO;
import view.dto.AttendanceLogDTO;
import view.dto.CrewAttendancesDTO;

public class Controller {
    private final InputView inputView;
    private final OutputView outputView;

    public Controller(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        LocalDateTime today = LocalDateTime.of(2024,12,17,10,0);
        CrewLoader crewLoader = new CrewLoader();
        CrewGroup crewGroup = crewLoader.loadCrews(today);

        attendanceCheck(crewGroup, today);
    }

    public void attendanceCheck(CrewGroup crewGroup, LocalDateTime today) {
        if(DayComparator.isHoliday(today)) {
            throw new IllegalArgumentException();
        }
        String rawName = inputView.insertNickname();
        String rawTime = inputView.insertTime();
        Crew crew = crewGroup.searchCrew(rawName);

        String[] spilttedTime = rawTime.split(":");

        LocalDateTime attendanceTime = LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(), Integer.parseInt(spilttedTime[0]), Integer.parseInt(spilttedTime[1]));
        Attendance attendance = crew.addAttendance(attendanceTime);

        outputView.printAttendanceLog(AttendanceLogDTO.from(attendance));
    }
}
