package controller;

import static view.UserCommandType.INITIAL;
import static view.UserCommandType.QUIT;
import static view.UserCommandType.getCommand;
import static view.UserCommandType.validateInput;

import controller.commands.Command;
import domain.Attendance;
import domain.AttendanceStatistics;
import domain.Attendances;
import domain.CrewGroup;
import domain.DayOfMonth;
import domain.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import service.CrewLoader;
import view.InputView;
import view.OutputView;
import view.UserCommandType;

public class AttendanceController {
    private static final LocalDate today = LocalDate.of(2024, 12, 16);
    private static final LocalTime startTime = LocalTime.of(8, 0);
    private static final LocalTime endTime = LocalTime.of(23, 0);
    private static final LocalTime INITIAL_TIME = LocalTime.of(10, 0);

    private final Map<UserCommandType, Command> commands = new HashMap<>();
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        CrewLoader crewLoader = new CrewLoader();
        CrewGroup crewGroup = crewLoader.load(today);
        UserCommandType userCommandType = INITIAL;
        try {
            while (userCommandType != QUIT) {
                String userInput = inputView.insertCommandType(today);
                validateInput(userInput);
                userCommandType = getCommand(userInput);
                executeCommand(userCommandType, crewGroup);
            }
        } catch (Exception e) {
            outputView.printExceptionLog(e);
        }
    }

    public void register(UserCommandType userCommandType, Command command) {
        commands.put(userCommandType, command);
    }

    public void executeCommand(UserCommandType userCommandType, CrewGroup crewGroup) {
        Command command = commands.get(userCommandType);
        command.execute(crewGroup);
    }

    public void markAttendance(CrewGroup crewGroup) {
        Attendance attendanceValidator = new Attendance(LocalDateTime.of(today, INITIAL_TIME));
        attendanceValidator.validateHoliday();

        String name = inputView.insertName();
        Attendances attendances = crewGroup.getSpecificAttendances(name);
        if (attendances.isExist(today)) {
            outputView.printUseChange();
            return;
        }

        String rawTime = inputView.insertTime();
        Time time = new Time(rawTime);
        time.validateTime(startTime, endTime);

        Attendance attendance = new Attendance(LocalDateTime.of(today, time.convertTime()));
        attendances.addAttendance(attendance);

        outputView.printAttendanceLog(attendance);
    }

    public void changeAttendance(CrewGroup crewGroup) {
        String name = inputView.insertChangeName();
        crewGroup.validateCrewName(name);
        Attendances attendances = crewGroup.getSpecificAttendances(name);

        int changeDay = inputView.insertChangeDayOfMonth();
        DayOfMonth dayOfMonth = new DayOfMonth(changeDay);
        Attendance originalAttendance = attendances.getSpecificAttendance(dayOfMonth, today);

        String rawTime = inputView.insertChangeTime();
        Time time = new Time(rawTime);
        time.validateTime(startTime, endTime);

        Attendance changeAttendance = attendances.changeAttendance(dayOfMonth, today, time.convertTime());
        outputView.printChangeLog(originalAttendance, changeAttendance);
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
