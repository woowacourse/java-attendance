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
import attendance.domain.AttendanceStatus;
import attendance.domain.Attendances;
import attendance.domain.Crew;
import attendance.domain.CrewAttendances;
import attendance.domain.Crews;
import attendance.view.InputView;
import attendance.view.OperationCommand;
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
        CrewAttendances crewAttendances = new CrewAttendances(
                changeKeyToCrew(nicknameAttendanceDateTimes), today.toLocalDate().minusDays(1L));

        startAttendanceManagementSystem(crews, crewAttendances);
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

    private void startAttendanceManagementSystem(final Crews crews, final CrewAttendances crewAttendances) {
        while (true) {
            try {
                OperationCommand operationCommand = inputView.readOperationCommand(today);
                if (operationCommand.equals(OperationCommand.QUIT)) {
                    return;
                }
                if (operationCommand.equals(OperationCommand.ATTENDANCE_CONFIRMATION)) {
                    runAttendanceConfirmOperation(crews, crewAttendances);
                }
                if (operationCommand.equals(OperationCommand.ATTENDANCE_MODIFICATION)) {
                    runAttendanceModificationOperation(crews, crewAttendances);
                }
                if (operationCommand.equals(OperationCommand.CREW_ATTENDANCES_INQUIRY)) {
                    runCrewAttendancesInquiryOperation(crews, crewAttendances);
                }
                if (operationCommand.equals(OperationCommand.PENALTY_CREWS_INQUIRY)) {
                    runPenaltyCrewsInquiryOperation(crews, crewAttendances);
                }
            } catch (IllegalArgumentException e) {
                resultView.printErrorMessage(e.getMessage());
            }
        }

    }

    private void runAttendanceConfirmOperation(final Crews crews, final CrewAttendances crewAttendances) {
        Crew crew = crews.findCrewByNickname(inputView.readAttendanceConfirmNickname());
        LocalTime attendanceTime = LocalTime.parse(inputView.readAttendanceConfirmTime());
        LocalDate todayDate = today.toLocalDate();
        crewAttendances.addAttendance(crew, new Attendance(todayDate.atTime(attendanceTime)));
        Attendance todayAttendance = crewAttendances.findCrewAttendanceByLocalDate(crew, todayDate);
        resultView.printAttendanceConfirmResult(LocalDateTime.of(todayAttendance.getAttendanceLocalDate(),
                todayAttendance.getAttendanceLocalTime()), todayAttendance.calculateStatus().getText());
    }

    private void runAttendanceModificationOperation(final Crews crews, final CrewAttendances crewAttendances) {
        Crew crew = crews.findCrewByNickname(inputView.readAttendanceModificationCrewNickname());
        LocalDate modificationDate = LocalDate.of(today.getYear(), today.getMonth(),
                inputView.readAttendanceModificationDate());
        LocalTime modificationTime = LocalTime.parse(inputView.readAttendanceModificationTime());
        boolean hasAttendanceRecord = crewAttendances.hasCrewAttendanceByLocalDate(crew, modificationDate);
        Attendance originAttendance = crewAttendances.findCrewAttendanceByLocalDate(crew, modificationDate);
        crewAttendances.modifyCrewAttendanceByModificationDateTime(
                crew, LocalDateTime.of(modificationDate, modificationTime), today.toLocalDate()
        );
        Attendance modificationAttendance = crewAttendances.findCrewAttendanceByLocalDate(crew, modificationDate);
        printAttendanceModificationResult(hasAttendanceRecord, originAttendance, modificationAttendance);
    }

    private void printAttendanceModificationResult(
            final boolean hasAttendanceRecord, final Attendance originAttendance,
            final Attendance modificationAttendance
    ) {
        LocalDateTime originAttendanceDateTime = LocalDateTime.of(originAttendance.getAttendanceLocalDate(),
                originAttendance.getAttendanceLocalTime());
        resultView.printOriginAttendanceRecord(hasAttendanceRecord, originAttendanceDateTime,
                originAttendance.calculateStatus().getText());
        resultView.printModificationAttendanceRecord(
                modificationAttendance.getAttendanceLocalTime(), modificationAttendance.calculateStatus().getText()
        );
    }

    private void runCrewAttendancesInquiryOperation(final Crews crews, final CrewAttendances crewAttendances) {
        Crew crew = crews.findCrewByNickname(inputView.readAttendanceConfirmNickname());
        LocalDate yesterday = today.toLocalDate().minusDays(1L);
        Attendances attendancesUntilYesterday = crewAttendances.findAllCrewAttendanceUntilStandardDate(crew, yesterday);
        List<LocalDateTime> attendanceTimes = mapToLocalDateTimes(attendancesUntilYesterday.getAttendances());
        List<Boolean> attendanceExistences = attendancesUntilYesterday.getAttendances()
                .stream()
                .map(Attendance::isHasRecord)
                .toList();
        List<String> attendanceStatuses = getAttendanceStatuses(attendancesUntilYesterday);
        resultView.printCrewAttendancesUntilYesterday(crew.getNickname(), attendanceTimes, attendanceExistences,
                attendanceStatuses);
        resultView.printAttendanceStatusCount(attendancesUntilYesterday.calculateAttendanceCount(yesterday),
                attendancesUntilYesterday.calculateLateCount(yesterday),
                attendancesUntilYesterday.calculateAttendanceCount(yesterday));
        resultView.printExpulsionStatus(
                attendancesUntilYesterday.findExpulsionStatusUntilStandardDate(yesterday).getText());

    }

    private List<LocalDateTime> mapToLocalDateTimes(final List<Attendance> crewAttendancesUntilYesterday) {
        return crewAttendancesUntilYesterday
                .stream()
                .map(attendance -> LocalDateTime.of(attendance.getAttendanceLocalDate(),
                        attendance.getAttendanceLocalTime())
                ).toList();
    }

    private List<String> getAttendanceStatuses(final Attendances attendancesUntilYesterday) {
        return attendancesUntilYesterday.getAttendances().stream()
                .map(Attendance::calculateStatus)
                .map(AttendanceStatus::getText)
                .toList();
    }

    private void runPenaltyCrewsInquiryOperation(final Crews crews, final CrewAttendances crewAttendances) {
        LocalDate yesterday = today.toLocalDate().minusDays(1L);
        List<Crew> penaltyCrews = crewAttendances.findPenaltyCrewsSortedByRisk(crews.findAllCrew(), yesterday);
        resultView.printPenaltyCrews();
        penaltyCrews.forEach(penaltyCrew -> resultView.printPenaltyCrewInformation(penaltyCrew.getNickname(),
                crewAttendances.calculateAbsentCount(penaltyCrew, yesterday),
                crewAttendances.calculateLateCount(penaltyCrew, yesterday),
                crewAttendances.calculateExpulsionStatus(penaltyCrew, yesterday).getText()
        ));
    }

}
