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

    private static String CSV_PATH = "src/main/resources/attendances.csv";

    public void start() {
        LocalDateTime fixDateTime = LocalDateTime.of(2024, Constants.MONTH, 25, 0, 0, 0, 0);
        final String input = InputView.readCommand(fixDateTime);
        Command command = Command.findByCommandNumber(input);
        Crews crews = CrewGenerator.generate(CsvReader.readFile(CSV_PATH),
                fixDateTime.toLocalDate());

        while (true) {
            if (command.equals(Command.QUIT)) {
                break;
            }
            if (command.equals(Command.CHECK_ATTENDEES)) {
                processCheckAttendees(crews, fixDateTime);
                continue;
            }
            if (command.equals(Command.EDIT_ATTENDANCE)) {
                processEditAttendance(crews);
                continue;
            }
            if (command.equals(Command.CHECK_THE_ATTENDANCE_RECORD_BY_CREW)) {
                processAttendanceRecordByCrew(crews);
                continue;
            }
            if (command.equals(Command.CONFIRMATION_OF_THOSE_AT_RISK_OF_EXPULSION)) {
                OutputView.printAllExpulsion(crews);
            }
        }
    }

    private static void processCheckAttendees(final Crews crews, final LocalDateTime fixDateTime) {
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

    private static void processEditAttendance(final Crews crews) {
        String inputNickName = InputView.readUpdateNickName();
        Nickname nickname = new Nickname(inputNickName);
        Crew crew = crews.findByNickname(nickname);
        String inputUpdateDate = InputView.readUpdateDate();
        String inputUpdateTime = InputView.readUpdateDateTime();

        final LocalDate localDate = LocalDate.of(2024, Constants.MONTH, Integer.parseInt(inputUpdateDate));
        final LocalTime oldTime = LocalTime.parse(inputUpdateTime);
        LocalDateTime oldDateTime = LocalDateTime.of(localDate, oldTime);

        Attendance oldAttendance = crew.getAttendance(oldDateTime);
        Attendance newAttendance = new Attendance(oldDateTime);
        crew.updateAttendance(oldAttendance, newAttendance);
        OutputView.printUpdateAttendance(oldAttendance, newAttendance);
    }

    private static void processAttendanceRecordByCrew(final Crews crews) {
        String inputNickName = InputView.readNickName();
        Nickname nickname = new Nickname(inputNickName);
        Crew crew = crews.findByNickname(nickname);

        OutputView.printCrewAttendances(crew);
    }
}
