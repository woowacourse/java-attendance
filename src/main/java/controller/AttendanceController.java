package controller;

import domain.Attendance;
import domain.AttendanceStatistics;
import domain.Attendances;
import domain.CrewGroup;
import domain.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import service.CrewLoader;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private static final LocalDate today = LocalDate.of(2024, 12, 14);
    private final InputView inputView;
    private final OutputView outputView;


    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        CrewLoader crewLoader = new CrewLoader();
        CrewGroup crewGroup = crewLoader.load(today);

        showAlertCrews(crewGroup);
//        markAttendance(crewGroup);
//        showCrewAttendanceLog(crewGroup);
    }

    public void markAttendance(CrewGroup crewGroup) {
        String name = inputView.insertName();
        Attendances attendances = crewGroup.getSpecificAttendances(name);

        if (attendances.isExist(today)) {
            throw new IllegalArgumentException("이미 출석했습니다. 수정 기능을 이용하세요");
        }

        String rawTime = inputView.insertTime();
        Time time = new Time(rawTime);

        Attendance attendance = new Attendance(LocalDateTime.of(today, time.convertTime()));
        attendances.addAttendance(attendance);

        outputView.printAttendanceLog(attendance);
    }

    public void showCrewAttendanceLog(CrewGroup crewGroup) {
        String name = inputView.insertName();
        Attendances attendances = crewGroup.getSpecificAttendances(name);
        AttendanceStatistics attendanceStatistics = attendances.makeStatistics();

        outputView.printAllLog(name, attendances, attendanceStatistics);
    }

    public void showAlertCrews(CrewGroup crewGroup) {
        Map<String, Attendances> alertCrews = crewGroup.getAlertCrews();

        Map<String, AttendanceStatistics> alertCrewStatistics = alertCrews.entrySet().stream()
                .collect(Collectors.toMap(Entry::getKey, v -> v.getValue().makeStatistics()));

        alertCrewStatistics = alertCrewStatistics.entrySet().stream()
                .sorted(Map.Entry.<String, AttendanceStatistics>comparingByValue()
                        .thenComparing(Map.Entry::getKey))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        outputView.printAlertCrews(alertCrewStatistics);
    }
}
