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
import util.DayOfMonth;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private static final String CSV_PATH = "src/main/resources/attendances.csv";

    public void start() {
        while (true) {
            AttendanceDateTime fixedAttendanceDateTime = AttendanceDateTime.getDefaultDateTime();
            final String input = InputView.readCommand(fixedAttendanceDateTime);
            Command command = Command.findByCommandNumber(input);
            Crews crews = CrewGenerator.generate(CsvReader.readFile(CSV_PATH), fixedAttendanceDateTime.getDate());
            if (command.equals(Command.QUIT)) {
                break;
            }
            command.execute(crews, fixedAttendanceDateTime);
        }
    }

    public static void processCheckAttendees(final Crews crews, final AttendanceDateTime attendanceDateTime) {
        Attendance.validate(attendanceDateTime);
        Crew crew = findCrewByNickNameInput(crews);
        String inputTime = InputView.readDateTime();
        LocalDate fixedDate = attendanceDateTime.getDate();
        crew.validateAttended(attendanceDateTime);
        AttendanceDateTime newAttendanceDateTime = AttendanceDateTime.ofTimeString(fixedDate, inputTime);
        final Attendance newAttendance = new Attendance(newAttendanceDateTime);
        crew.add(newAttendance);
        OutputView.printAttendance(newAttendance);
    }

    public static void processEditAttendance(final Crews crews) {
        String inputNickName = InputView.readUpdateNickName();
        Nickname nickname = new Nickname(inputNickName);
        Crew crew = crews.findByNickname(nickname);

        String oldDayOfMonthInput = InputView.readUpdateDate();
        int dayOfMonthInput = Integer.parseInt(oldDayOfMonthInput);
        DayOfMonth dayOfMonth = new DayOfMonth(dayOfMonthInput);
        int displayDay = dayOfMonth.getDisplayDay();

        Attendances attendances = crew.getAttendances();
        Attendance findAttendance = attendances.findAttendance(displayDay);

        String inputUpdateTime = InputView.readUpdateDateTime();
        final LocalTime updateTime = LocalTime.parse(inputUpdateTime);

        attendances.updateTime(findAttendance, updateTime);
        Attendance newAttendance = attendances.findAttendance(displayDay);

        OutputView.printUpdateAttendance(findAttendance, newAttendance);
    }

    public static void processAttendanceRecordByCrew(final Crews crews) {
        Crew crew = findCrewByNickNameInput(crews);
        OutputView.printCrewAttendances(crew);
    }

    private static Crew findCrewByNickNameInput(Crews crews) {
        String inputNickName = InputView.readNickName();
        Nickname nickname = new Nickname(inputNickName);
        Crew crew = crews.findByNickname(nickname);
        return crew;
    }
}
