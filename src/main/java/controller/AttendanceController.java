package controller;

import domain.Attendance;
import domain.AttendanceDateTime;
import domain.Attendances;
import domain.Command;
import domain.Crew;
import domain.Crews;
import domain.Nickname;
import java.time.LocalDate;
import java.time.LocalTime;
import util.CrewGenerator;
import util.CsvReader;
import domain.DayOfMonth;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private static final String CSV_PATH = "src/main/resources/attendances.csv";

    public void start() {
        while (true) {
            final AttendanceDateTime fixedAttendanceDateTime = AttendanceDateTime.getDefaultDateTime();
            final String input = InputView.readCommand(fixedAttendanceDateTime);
            final Command command = Command.findByCommandNumber(input);
            final Crews crews = CrewGenerator.generate(CsvReader.readFile(CSV_PATH), fixedAttendanceDateTime.getDate());
            if (command.equals(Command.QUIT)) {
                break;
            }
            command.execute(crews, fixedAttendanceDateTime);
        }
    }

    public static void processCheckAttendees(final Crews crews, final AttendanceDateTime attendanceDateTime) {
        Attendance.validate(attendanceDateTime);
        final Crew crew = findCrewByNickNameInput(crews);
        final String inputTime = InputView.readDateTime();
        final LocalDate fixedDate = attendanceDateTime.getDate();
        crew.validateAttended(attendanceDateTime);
        final AttendanceDateTime newAttendanceDateTime = AttendanceDateTime.ofTimeString(fixedDate, inputTime);
        final Attendance newAttendance = new Attendance(newAttendanceDateTime);
        crew.add(newAttendance);
        OutputView.printAttendance(newAttendance);
    }

    public static void processEditAttendance(final Crews crews) {
        final String inputNickName = InputView.readUpdateNickName();
        final Nickname nickname = new Nickname(inputNickName);
        final Crew crew = crews.findByNickname(nickname);
        final int displayDay = readDisplayName();
        final Attendances attendances = crew.getAttendances();
        final Attendance findAttendance = attendances.findAttendance(displayDay);
        readUpdateDateTime(attendances, findAttendance);
        final Attendance newAttendance = attendances.findAttendance(displayDay);
        OutputView.printUpdateAttendance(findAttendance, newAttendance);
    }

    public static void processAttendanceRecordByCrew(final Crews crews) {
        final Crew crew = findCrewByNickNameInput(crews);
        OutputView.printCrewAttendances(crew);
    }

    private static Crew findCrewByNickNameInput(Crews crews) {
        final String inputNickName = InputView.readNickName();
        final Nickname nickname = new Nickname(inputNickName);
        return crews.findByNickname(nickname);
    }

    private static int readDisplayName() {
        final String oldDayOfMonthInput = InputView.readUpdateDate();
        final int dayOfMonthInput = Integer.parseInt(oldDayOfMonthInput);
        final DayOfMonth dayOfMonth = new DayOfMonth(dayOfMonthInput);
        return dayOfMonth.getDisplayDay();
    }

    private static void readUpdateDateTime(Attendances attendances, Attendance findAttendance) {
        final String inputUpdateTime = InputView.readUpdateDateTime();
        final LocalTime updateTime = LocalTime.parse(inputUpdateTime);
        attendances.updateTime(findAttendance, updateTime);
    }
}
