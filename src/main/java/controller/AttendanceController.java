package controller;

import domain.Attendance;
import domain.Command;
import domain.Crew;
import domain.Crews;
import domain.Nickname;
import error.CustomIllegalArgumentException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import util.Constants;
import util.CrewGenerator;
import util.CsvReader;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private static final String CSV_PATH = "src/main/resources/attendances.csv";

    public void start() {
        while (true) {
            LocalDateTime fixedDateTime = LocalDateTime.of(2024, Constants.FIXED_MONTH, 16, 0, 0, 0, 0);
            final String input = InputView.readCommand(fixedDateTime);
            Command command = Command.findByCommandNumber(input);
            Crews crews = CrewGenerator.generate(CsvReader.readFile(CSV_PATH),
                    fixedDateTime.toLocalDate());

            if (command.equals(Command.QUIT)) {
                break;
            }

            command.execute(crews, fixedDateTime);
        }
    }

    public static void processCheckAttendees(final Crews crews, final LocalDateTime fixDateTime) {
        String inputNickName = InputView.readNickName();
        Nickname nickname = new Nickname(inputNickName);
        Crew crew = crews.findByNickname(nickname);
        String inputTime = InputView.readDateTime();
        final LocalDate localDate = fixDateTime.toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        final LocalTime parsedInputTime = LocalTime.parse(inputTime, formatter);
        if (crew.isAttended(LocalDateTime.of(localDate, parsedInputTime))) {
            throw new CustomIllegalArgumentException("이미 출석했습니다. 다음에는 수정기능을 이용해주세요.");
        }
        final Attendance attendance = new Attendance(LocalDateTime.of(localDate, parsedInputTime));
        crew.add(attendance);
        OutputView.printAttendance(attendance);
    }

    public static void processEditAttendance(final Crews crews) {
        String inputNickName = InputView.readUpdateNickName();
        Nickname nickname = new Nickname(inputNickName);
        Crew crew = crews.findByNickname(nickname);
        String inputUpdateDate = InputView.readUpdateDate();
        String inputUpdateTime = InputView.readUpdateDateTime();

        final LocalDate localDate = LocalDate.of(2024, Constants.FIXED_MONTH, Integer.parseInt(inputUpdateDate));
        final LocalTime oldTime = LocalTime.parse(inputUpdateTime);
        LocalDateTime oldDateTime = LocalDateTime.of(localDate, oldTime);

        Attendance oldAttendance = crew.getAttendance(oldDateTime);
        Attendance newAttendance = new Attendance(oldDateTime);
        crew.updateAttendance(oldAttendance, newAttendance);
        OutputView.printUpdateAttendance(oldAttendance, newAttendance);
    }

    public static void processAttendanceRecordByCrew(final Crews crews) {
        String inputNickName = InputView.readNickName();
        Nickname nickname = new Nickname(inputNickName);
        Crew crew = crews.findByNickname(nickname);

        OutputView.printCrewAttendances(crew);
    }
}
