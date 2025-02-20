package controller;

import constant.Command;
import converter.StringConverter;
import dto.AttendanceResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import model.Attendance;
import model.Attendances;
import model.Crew;
import model.Crews;
import util.DataReader;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final StringConverter stringConverter;

    public AttendanceController(InputView inputView, OutputView outputView, StringConverter stringConverter) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.stringConverter = stringConverter;
    }

    public void run() {
        List<String> rawAttendances = new DataReader().readAttendances("src/main/resources/attendances.csv");
        Crews crews = stringConverter.convertToCrews(rawAttendances);
        Attendances attendances = stringConverter.convertToAttendances(rawAttendances, crews);

        String rawCommand = inputView.readCommand();
        Command command = stringConverter.convertToCommand(rawCommand);
        if (command.equals(Command.ONE)) {
            Attendance attendance = checkInAttendance(attendances);
            outputView.printCheckInResult(attendance);
        }
        if (command.equals(Command.TWO)) {
            modifyAttendance(attendances);
        }
        if (command.equals(Command.THREE)) {
            checkAttendance(attendances);
        }
        if (command.equals(Command.FOUR)) {

        }
        if (command.equals(Command.QUIT)) {
        }

    }

    private Attendance checkInAttendance(Attendances attendances) {
        String rawNickname = inputView.readNickname();
        String rawCheckInTime = inputView.readCheckInTime();

        Attendance attendance = stringConverter.convertToAttendance(rawNickname, rawCheckInTime);
        attendances.checkIn(attendance);

        return attendance;
    }

    private void modifyAttendance(Attendances attendances) {
        String rawNickname = inputView.readNickname();
        String rawDay = inputView.readDay();
        String rawChangeTime = inputView.readChangeTime();

        Crew crew = stringConverter.convertToNickname(rawNickname);
        LocalDateTime changeTime = stringConverter.convertToLocalDateTime(rawDay, rawChangeTime);

        Optional<Attendance> existAttendance = attendances.find(crew, changeTime.toLocalDate());
        Attendance modifedAttendance = attendances.modify(crew, changeTime);

        outputView.printModifiedResult(existAttendance, modifedAttendance);
    }

    private void checkAttendance(Attendances attendances) {
        String rawNickname = inputView.readNickname();
        Crew crew = stringConverter.convertToNickname(rawNickname);

        Attendances filteredAttendances = attendances.findByCrewAndMonth(crew, LocalDate.now().getMonthValue());

        AttendanceResult attendanceResult = AttendanceResult.of(filteredAttendances);
        outputView.printAttendanceRecord(crew, attendanceResult);
    }
}
