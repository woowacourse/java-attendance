package controller;

import domain.Attendance;
import domain.AttendanceDateTime;
import domain.Command;
import domain.Crew;
import domain.Crews;
import domain.Nickname;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import util.CrewGenerator;
import util.CsvReader;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private static final String CSV_PATH = "src/main/resources/attendances.csv";

    public void start() {
        while (true) {
            LocalDateTime fixedDateTime = AttendanceDateTime.getDefaultDateTime();
            final String input = InputView.readCommand(fixedDateTime);
            Command command = Command.findByCommandNumber(input);
            Crews crews = CrewGenerator.generate(CsvReader.readFile(CSV_PATH), fixedDateTime.toLocalDate());
            if (command.equals(Command.QUIT)) {
                break;
            }
            command.execute(crews, fixedDateTime);
        }
    }

    public static void processCheckAttendees(final Crews crews, final LocalDateTime fixDateTime) {
        Crew crew = findCrew(crews);
        String inputTime = InputView.readDateTime();

        LocalDate fixedDate = AttendanceDateTime.getDate(fixDateTime);
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.ofTimeString(fixedDate, inputTime);
        LocalDateTime dateTime = attendanceDateTime.getDateTime();
        crew.validateAttended(dateTime);
        final Attendance attendance = new Attendance(dateTime);
        crew.add(attendance);
        OutputView.printAttendance(attendance);
    }

    public static void processEditAttendance(final Crews crews) {
        String inputNickName = InputView.readUpdateNickName();
        Nickname nickname = new Nickname(inputNickName);
        Crew crew = crews.findByNickname(nickname);

        String inputUpdateDate = InputView.readUpdateDate();
        LocalDate updateDate = AttendanceDateTime.parsedLocalDateByDateOfMonth(Integer.parseInt(inputUpdateDate));

        String inputUpdateTime = InputView.readUpdateDateTime();
        final LocalTime updateTime = LocalTime.parse(inputUpdateTime);
        AttendanceDateTime updateDateTime = AttendanceDateTime.of(updateDate, updateTime);

        Attendance oldAttendance = crew.getAttendance(updateDate);
        Attendance newAttendance = new Attendance(updateDateTime.getDateTime());
        crew.updateAttendance(oldAttendance, newAttendance);
        OutputView.printUpdateAttendance(oldAttendance, newAttendance);
    }

    public static void processAttendanceRecordByCrew(final Crews crews) {
        Crew crew = findCrew(crews);
        OutputView.printCrewAttendances(crew);
    }

    private static Crew findCrew(Crews crews) {
        String inputNickName = InputView.readNickName();
        Nickname nickname = new Nickname(inputNickName);
        Crew crew = crews.findByNickname(nickname);
        return crew;
    }
}
