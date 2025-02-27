package attendance.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import attendance.domain.Attendance;
import attendance.domain.Crew;
import attendance.domain.CrewAttendances;
import attendance.domain.Crews;
import attendance.view.InputView;
import attendance.view.ResultView;

public class AttendanceManagementController {

    private static final DateTimeFormatter ATTENDANCE_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final InputView inputView;
    private final ResultView resultView;
    private final LocalDateTime today;

    public AttendanceManagementController(
            final InputView inputView, final ResultView resultView, final LocalDateTime today
    ) {
        this.inputView = inputView;
        this.resultView = resultView;
        this.today = today;
    }

    public void run() {
        Map<String, List<LocalDateTime>> nicknameAttendanceDateTimes = readNicknameAttendanceDateTimes();
        Crews crews = new Crews(nicknameAttendanceDateTimes.keySet()
                .stream()
                .toList());
        CrewAttendances crewAttendances = new CrewAttendances(changeKeyToCrew(nicknameAttendanceDateTimes));

        try {
            String operationCommand = inputView.readOperationCommand(today);
            if (operationCommand.equals("1")) {
                Crew crew = crews.findCrewByNickname(inputView.readAttendanceConfirmNickname());
                LocalTime attendanceTime = LocalTime.parse(inputView.readAttendanceConfirmTime());
                LocalDate todayDate = today.toLocalDate();
                crewAttendances.addAttendance(crew, new Attendance(todayDate.atTime(attendanceTime)));
                Attendance todayAttendance = crewAttendances.findCrewAttendanceByLocalDate(crew, todayDate);
                resultView.printAttendanceConfirmResult(LocalDateTime.of(todayAttendance.getAttendanceLocalDate(),
                        todayAttendance.getAttendanceLocalTime()), todayAttendance.calculateStatus().getText());
            }
        } catch (IllegalArgumentException e) {
            resultView.printErrorMessage(e.getMessage());
        }

    }

    private Map<String, List<LocalDateTime>> readNicknameAttendanceDateTimes() {
        List<String> linesWithoutFirstLine = FileLinesReader.readLinesWithoutFirstLine(
                "src/main/resources/", "attendances.csv");
        Map<String, List<LocalDateTime>> nicknameAttendanceDateTimes = new HashMap<>();
        linesWithoutFirstLine.forEach(line -> addAttendanceDateTimeEachNickname(line, nicknameAttendanceDateTimes));
        return nicknameAttendanceDateTimes;
    }

    private void addAttendanceDateTimeEachNickname(
            final String line, final Map<String, List<LocalDateTime>> nicknameAttendanceDateTimes
    ) {
        List<String> nicknameAndAttendanceDateTimes = Arrays.stream(line.split(",")).toList();
        String nickname = nicknameAndAttendanceDateTimes.getFirst();
        LocalDateTime attendanceDateTime = LocalDateTime.parse(nicknameAndAttendanceDateTimes.getLast(),
                ATTENDANCE_DATE_TIME_FORMATTER);
        nicknameAttendanceDateTimes.computeIfAbsent(nickname, value -> new ArrayList<>()).add(attendanceDateTime);
    }

    private Map<Crew, List<LocalDateTime>> changeKeyToCrew(
            final Map<String, List<LocalDateTime>> nicknameAttendanceDateTimes
    ) {
        return nicknameAttendanceDateTimes.keySet()
                .stream()
                .collect(Collectors.toMap(
                        Crew::new,
                        nicknameAttendanceDateTimes::get,
                        (a, b) -> b
                ));
    }

}
