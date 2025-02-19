package controller;

import domain.Attendance;
import domain.Crew;
import domain.CrewGroup;
import java.time.LocalDateTime;
import service.CrewLoader;
import service.DayComparator;
import view.InputView;
import view.OutputView;
import view.dto.AttendanceLogDTO;
import view.dto.ChangeAttendanceLogDTO;

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

        changeAttendance(crewGroup, today);
    }

    public void attendanceCheck(CrewGroup crewGroup, LocalDateTime today) {
        if (DayComparator.isHoliday(today)) {
            throw new IllegalArgumentException();
        }
        String rawName = inputView.insertNickname();
        String rawTime = inputView.insertTime();
        Crew crew = crewGroup.searchCrew(rawName);

        String[] spilttedTime = rawTime.split(":");

        LocalDateTime attendanceTime = LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(),
                Integer.parseInt(spilttedTime[0]), Integer.parseInt(spilttedTime[1]));
        Attendance attendance = crew.addAttendance(attendanceTime);

        outputView.printAttendanceLog(AttendanceLogDTO.from(attendance));
    }

    public void changeAttendance(CrewGroup crewGroup, LocalDateTime today) {
        //1. 크루 닉네임 입력
        String rawName = inputView.insertChangeDateNickname();
        Crew crew = crewGroup.searchCrew(rawName);
        //2. 잇으면 수정하려는 날짜 입력
        int changeDate = inputView.insertChangeDate();
        //3. 평일이거나 미래가 아니면 시간 입력
        String rawTime = inputView.insertChangeTime();
        //4. 출석 수정
        Attendance originalAttendance = crew.getSpecificAttendance(changeDate);
        Attendance copy = new Attendance(originalAttendance.getDate());
        //5. 기존 -> 변경 출력
        Attendance changedAttendance = crew.changeAttendance(changeDate, rawTime);

        outputView.printChangeLog(ChangeAttendanceLogDTO.from(copy, changedAttendance));
    }
}
