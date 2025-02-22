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
import java.util.List;

public class AttendanceController {

    private final Attendances attendances;
    private final Crews crews;

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
                if ("Q".equals(inputFunction)) {
                    break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void performFunction(String inputFunction) {
        if ("1".equals(inputFunction)) {
            validateAttendanceDate();
            recordAttendance();
        }
        if ("2".equals(inputFunction)) {
            modifyAttendance();
        }
        if ("3".equals(inputFunction)) {
            checkAttendanceRecordOfCrew();
        }
        if ("4".equals(inputFunction)) {
            OutputView.printPenaltyOfCrews(crews.findAll(), attendances);
        }
    }

    private void validateAttendanceDate() {
        if (DateUtil.isWeekend(LocalDate.now()) || Holiday.isHoliday(LocalDate.now())) {
            throw new IllegalArgumentException(
                String.format("%n[ERROR] %s은 등교일이 아닙니다.", LocalDate.now().format(OutputView.DATE_FORMATTER)));
        }
    }

    private void recordAttendance() {
        Crew crew = getCrew();
        LocalTime checkInTime = getCheckInTime();
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), checkInTime);
        Attendance attendance = Attendance.of(dateTime);
        attendances.addAttendance(crew, attendance);
        OutputView.printAttendanceResult(attendance);
    }

    private Crew getCrew() {
        String inputNickName = InputView.readNickName();
        return crews.getByNickName(inputNickName);
    }

    private LocalTime getCheckInTime() {
        String inputCheckInTime = InputView.readCheckInTime();
        return LocalTime.parse(inputCheckInTime);
    }

    private void modifyAttendance() {
        String nickName = InputView.readModifyingNickName();
        Crew crew = crews.getByNickName(nickName);
        LocalDate toBeModified = getModifyingCheckinDate();
        LocalTime toModify = getModifyingCheckinTime();

        Attendance beforeAttendance = attendances.getAttendance(crew, toBeModified);
        attendances.modifyAttendance(crew, toBeModified, toModify);
        Attendance afterAttendance = attendances.getAttendance(crew, toBeModified);

        OutputView.printModifyingResult(beforeAttendance, afterAttendance);
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
        List<Attendance> attendancesOfCrew = attendances.getAttendances(crew, LocalDate.now());
        OutputView.printAttendanceRecordAndPenalty(attendancesOfCrew);
    }
}
