package controller;

import domain.Attendance;
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

    public void start() {
        LocalDateTime fixDateTime = LocalDateTime.of(2024, 12, 14, 0, 0, 0, 0);
        final String input = InputView.readCommand(fixDateTime);
        Command command = Command.findByCommandNumber(input);
        Crews crews = CrewGenerator.generate(CsvReader.readFile("src/main/resources/attendances.csv"),
                fixDateTime.toLocalDate());

        if (command.equals(Command.CHECK_ATTENDEES)) {
            String inputNickName = InputView.readNickName();
            Nickname nickname = new Nickname(inputNickName);
            Crew crew = crews.findByNickname(nickname);
            String inputDateTime = InputView.readDateTime();
            final LocalDate localDate = fixDateTime.toLocalDate();
            final LocalTime dateTime = LocalTime.parse(inputDateTime);
            if (crew.isAttended(LocalDateTime.of(localDate, dateTime))) {
                throw new IllegalArgumentException("이미 출석했습니다. 다음에는 수정기능을 이용해주세요.");
            }
            final Attendance attendance = new Attendance(LocalDateTime.of(localDate, dateTime));
            crew.add(attendance);
            OutputView.printAttendance(attendance);

        } else if (command.equals(Command.EDIT_ATTENDANCE)) {
            String inputNickName = InputView.readUpdateNickName();
            Nickname nickname = new Nickname(inputNickName);
            Crew crew = crews.findByNickname(nickname);
            String inputUpdateDate = InputView.readUpdateDate();
            String inputUpdateTime = InputView.readUpdateDateTime();

            final LocalDate localDate = LocalDate.of(2024, 12, Integer.parseInt(inputUpdateDate));
            final LocalTime oldTime = LocalTime.parse(inputUpdateTime);
            LocalDateTime oldDateTime = LocalDateTime.of(localDate, oldTime);

            Attendance oldAttendance = crew.getAttendance(oldDateTime);
            Attendance newAttendance = new Attendance(oldDateTime);
            crew.updateAttendance(oldAttendance, newAttendance);
            OutputView.printUpdateAttendance(oldAttendance, newAttendance);

        } else if (command.equals(Command.CHECK_THE_ATTENDANCE_RECORD_BY_CREW)) {
            String inputNickName = InputView.readNickName();
            Nickname nickname = new Nickname(inputNickName);
            Crew crew = crews.findByNickname(nickname);

            OutputView.printCrewAttendances(crew);

        } else if (command.equals(Command.CONFIRMATION_OF_THOSE_AT_RISK_OF_EXPULSION)) {
            OutputView.printAllExpulsion(crews);

        }
    }
}
