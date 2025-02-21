package attendance.controller;

import attendance.constant.Holiday;
import attendance.domain.Attendance;
import attendance.domain.Attendances;
import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.file.AttendanceFileReader;
import attendance.file.AttendanceFileReader.FileContents;
import attendance.util.DateUtil;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class AttendanceController {

    private Attendances attendances;
    private Crews crews;

    public AttendanceController() throws IOException {
        String path = "src/main/resources/attendances.csv";
        FileContents fileContents = AttendanceFileReader.read(path);
        attendances = fileContents.attendances();
        crews = fileContents.crews();
    }

    public void run() {
        while (true) {
            String inputFunction = InputView.readFunction();
            try {
                performFunction(inputFunction);
                if (inputFunction.equals("Q")) {
                    break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void performFunction(String inputFunction) {
        if (inputFunction.equals("1")) {
            validateAttendanceDate();
            recordAttendance();
        }
        if (inputFunction.equals("2")) {
            modifyAttendance();
        }
        if (inputFunction.equals("3")) {
            checkAttendanceRecordOfCrew();
        }
        if (inputFunction.equals("4")) {
            OutputView.printPenaltyOfCrews(crews.getCrews(), attendances);
        }
    }

    private void validateAttendanceDate() {
        if (DateUtil.isWeekend(LocalDate.now()) || Holiday.isHoliday(LocalDate.now())) {
            throw new IllegalArgumentException(String.format("%n[ERROR] %s은 등교일이 아닙니다.", LocalDate.now().format(
                DateTimeFormatter.ofPattern(OutputView.DATE_FORMATTER, Locale.KOREAN))));
        }
    }

    private void recordAttendance() {
        Crew crew = getCrew();
        LocalTime checkInTime = getCheckInTime();
        LocalDateTime attendanceDateTime = LocalDateTime.of(LocalDate.now(), checkInTime);
        Attendance attendance = Attendance.of(attendanceDateTime);
        attendances.addAttendance(crew, attendance);
        OutputView.printAttendanceResult(attendance);
    }

    private Crew getCrew() {
        String inputNickName = InputView.readNickName();
        return crews.getCrew(inputNickName);
    }

    private LocalTime getCheckInTime() {
        String inputCheckInTime = InputView.readCheckInTime();
        return LocalTime.parse(inputCheckInTime);
    }

    private void modifyAttendance() {
        String nickName = InputView.readModifyingNickName();
        Crew crew = crews.getCrew(nickName);
        LocalDate modifyingCheckinDate = getModifyingCheckinDate();
        LocalTime modifyingCheckinTime = getModifyingCheckinTime();

        Attendance attendance = attendances.getAttendance(crew, modifyingCheckinDate);

        Attendance previousAttendance = Attendance.of(attendance.getAttendanceDateTime());
        attendance.modify(LocalDateTime.of(modifyingCheckinDate, modifyingCheckinTime));
        OutputView.printModifyingResult(previousAttendance, attendance);
    }

    private LocalTime getModifyingCheckinTime() {
        String inputModifyingCheckinTime = InputView.readModifyingCheckinTime();
        return LocalTime.parse(inputModifyingCheckinTime);
    }

    private LocalDate getModifyingCheckinDate() {
        String inputModifyingCheckinDate = InputView.readModifyingCheckinDate();
        return LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), Integer.parseInt(inputModifyingCheckinDate));
    }

    private void checkAttendanceRecordOfCrew() {
        Crew crew = getCrew();
        List<Attendance> attendancesOfCrew = attendances.getByCrew(crew, LocalDate.now());
        OutputView.printAttendanceRecordAndPenalty(attendancesOfCrew);
    }
}
