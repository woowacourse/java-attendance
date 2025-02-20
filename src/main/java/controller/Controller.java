package controller;

import domain.Attendance;
import domain.Crew;
import domain.CrewGroup;
import domain.Function;
import domain.Time;
import java.time.LocalDateTime;
import java.util.List;
import service.CrewLoader;
import service.DayComparator;
import view.InputView;
import view.OutputView;
import view.dto.AlertCrewDTO;
import view.dto.AlertCrewsDTO;
import view.dto.AttendanceLogDTO;
import view.dto.ChangeAttendanceLogDTO;
import view.dto.CrewAttendancesDTO;

public class Controller {
    private final InputView inputView;
    private final OutputView outputView;

    public Controller(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        LocalDateTime today = LocalDateTime.of(2024, 12, 17, 10, 0);
        CrewLoader crewLoader = new CrewLoader();
        CrewGroup crewGroup = crewLoader.loadCrews(today);

        while (true) {
            String rawFunction = inputView.insertFunction(today);
            Function function = new Function(rawFunction);

            if (function.equals("Q")) break;
            if (function.equals("1")) {
                attendanceCheck(crewGroup, today);
            }
            if (function.equals("2")) {
                changeAttendance(crewGroup);
            }
            if(function.equals("3")) {
                showCrewAttendance(crewGroup);
            }
            if (function.equals("4")) {
                showAlertCrews(crewGroup);
            }
        }
    }

    private void attendanceCheck(CrewGroup crewGroup, LocalDateTime today) {
        if (DayComparator.isHoliday(today)) {
            throw new IllegalArgumentException();
        }
        String rawName = inputView.insertNickname();
        String rawTime = inputView.insertTime();
        Crew crew = crewGroup.searchCrew(rawName);
        Time time = new Time(rawTime);
        LocalDateTime attendanceTime = LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(),
                time.getHour(), time.getMinute());
        Attendance attendance = crew.addAttendance(attendanceTime);

        outputView.printAttendanceLog(AttendanceLogDTO.from(attendance));
    }

    private void changeAttendance(CrewGroup crewGroup) {
        //1. 크루 닉네임 입력
        String rawName = inputView.insertChangeDateNickname();
        Crew crew = crewGroup.searchCrew(rawName);
        //2. 잇으면 수정하려는 날짜 입력
        int changeDate = inputView.insertChangeDate();
        //3. 평일이거나 미래가 아니면 시간 입력
        String rawTime = inputView.insertChangeTime();
        Time time = new Time(rawTime);
        // 예외

        //4. 출석 수정
        Attendance originalAttendance = crew.getSpecificAttendance(changeDate);
        Attendance copy = new Attendance(originalAttendance.getDate());
        //5. 기존 -> 변경 출력
        Attendance changedAttendance = crew.changeAttendance(changeDate, time);

        outputView.printChangeLog(ChangeAttendanceLogDTO.from(copy, changedAttendance));
    }

    private void showCrewAttendance(CrewGroup crewGroup) {
        String rawName = inputView.insertNickname();
        Crew crew = crewGroup.searchCrew(rawName);

        CrewAttendancesDTO crewAttendancesDTO = CrewAttendancesDTO.from(crew);

        outputView.printAttendancesLog(crewAttendancesDTO);
    }

    private void showAlertCrews(CrewGroup crewGroup) {
        List<AlertCrewDTO> alertCrewDTDs = crewGroup.getAllAttendanceAlertLevel()
                .stream()
                .map(AlertCrewDTO::from)
                .toList();

        AlertCrewsDTO alertCrewsDTO = AlertCrewsDTO.from(alertCrewDTDs);

        outputView.printAlertCrews(alertCrewsDTO);
    }
}
