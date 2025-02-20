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

    public void start() {
        while (true) {
            final LocalDateTime fixDateTime = LocalDateTime.of(Constants.FIXED_YEAR, Constants.FIXED_MONTH,
                    Constants.FIXED_DAY, 0, 0,
                    0, 0);
            final String input = InputView.readCommand(fixDateTime);
            final Command command = Command.findByCommandNumber(input);
            final Crews crews = CrewGenerator.generate(CsvReader.readFile(Constants.CSV_PATH),
                    fixDateTime.toLocalDate());

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

    private void processCheckAttendees(final Crews crews, final LocalDateTime fixDateTime) {
        final Nickname nickname = readNickname();
        final Crew crew = crews.findByNickname(nickname);
        final LocalDateTime attendedDateTime = LocalDateTime.of(fixDateTime.toLocalDate(), readLocalTime());

        if (crew.isAttended(attendedDateTime)) {
            throw new CustomIllegalArgumentException("이미 출석했습니다. 다음에는 수정기능을 이용해주세요.");
        }
        final Attendance attendance = attend(crew, attendedDateTime);

        OutputView.printAttendance(attendance);
    }

    private Nickname readNickname() {
        final String inputNickName = InputView.readNickName();
        return new Nickname(inputNickName);
    }

    private LocalTime readLocalTime() {
        final String inputTime = InputView.readDateTime();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.TIME_FORMAT);
        return LocalTime.parse(inputTime, formatter);
    }

    private Attendance attend(Crew crew, LocalDateTime attendedDateTime) {
        final Attendance attendance = new Attendance(attendedDateTime);
        crew.add(attendance);
        return attendance;
    }


    private void processEditAttendance(final Crews crews) {
        final Nickname nickname = readNickname();
        final Crew crew = crews.findByNickname(nickname);
        final LocalDateTime desiredUpdateDateTime = readUpdateDateTime();
        final Attendance oldAttendance = crew.getAttendance(desiredUpdateDateTime);
        final Attendance newAttendance = new Attendance(desiredUpdateDateTime);

        crew.updateAttendance(oldAttendance, newAttendance);
        OutputView.printUpdateAttendance(oldAttendance, newAttendance);
    }

    private LocalDateTime readUpdateDateTime() {
        final String updateDate = InputView.readUpdateDate();
        final LocalTime desiredUpdateTime = LocalTime.parse(InputView.readUpdateDateTime());
        final LocalDate fixedLocalDate = LocalDate.of(Constants.FIXED_YEAR, Constants.FIXED_MONTH,
                Integer.parseInt(updateDate));
        return LocalDateTime.of(fixedLocalDate, desiredUpdateTime);
    }

    private void processAttendanceRecordByCrew(final Crews crews) {
        Nickname nickname = readNickname();
        Crew crew = crews.findByNickname(nickname);

        OutputView.printCrewAttendances(crew);
    }
}
