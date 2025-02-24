package controller;

import constant.Command;
import converter.StringConverter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import model.Attendance;
import model.AttendanceStatistics;
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
        try {
            List<String> rawAttendances = new DataReader().readAttendances("src/main/resources/attendances.csv");
            Crews crews = stringConverter.convertToCrews(rawAttendances);
            Attendances attendances = stringConverter.convertToAttendances(rawAttendances, crews);

            Command command;
            do {
                command = readCommand();
                processCommand(command, crews, attendances);
            } while (!command.isQuit());
        } catch (RuntimeException e) {
            outputView.printErrorMessage(e);
        }
    }

    private Command readCommand() {
        String rawCommand = inputView.readCommand();
        return stringConverter.convertToCommand(rawCommand);
    }

    private void processCommand(Command command, Crews crews, Attendances attendances) {
        LocalDate today = LocalDate.now();
        if (command.isOne()) {
            checkInAttendance(attendances, today);
        }
        if (command.isTwo()) {
            modifyAttendance(attendances, today);
        }
        if (command.isThree()) {
            checkAttendance(attendances);
        }
        if (command.isFour()) {
            checkPunishment(crews, attendances);
        }
    }

    private void checkInAttendance(Attendances attendances, LocalDate today) {
        String rawNickname = inputView.readNickname();
        String rawCheckInTime = inputView.readCheckInTime();

        Attendance attendance = stringConverter.convertToAttendance(rawNickname, rawCheckInTime, today);
        attendances.checkIn(attendance);

        outputView.printCheckInResult(attendance);
    }

    private void modifyAttendance(Attendances attendances, LocalDate today) {
        String rawNickname = inputView.readNickname();
        String rawDay = inputView.readDay();
        String rawChangeTime = inputView.readChangeTime();
        Crew crew = stringConverter.convertToNickname(rawNickname);
        LocalDateTime changeTime = stringConverter.convertToLocalDateTime(rawDay, rawChangeTime, today);

        Optional<Attendance> existAttendance = attendances.find(crew, changeTime.toLocalDate());
        Attendance modifedAttendance = attendances.modify(crew, changeTime);

        outputView.printModifiedResult(existAttendance, modifedAttendance);
    }

    private void checkAttendance(Attendances attendances) {
        String rawNickname = inputView.readNickname();
        Crew crew = stringConverter.convertToNickname(rawNickname);

        LocalDate today = LocalDate.now();
        Attendances filteredAttendances = attendances.findByCrewThisMonth(crew, today);
        AttendanceStatistics attendanceResult = attendances.createStatistics(crew, today);
        outputView.printAttendanceRecord(crew, filteredAttendances, attendanceResult);
    }

    private void checkPunishment(Crews crews, Attendances attendances) {
        List<AttendanceStatistics> dangerCrews = crews.findDangerCrews(attendances, LocalDate.now());
        outputView.printAllCrewPunishment(dangerCrews);
    }
}
