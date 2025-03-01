package controller;

import converter.StringConverter;
import domain.Attendance;
import domain.Attendances;
import domain.Crew;
import domain.Crews;
import file.DataReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final StringConverter converter;

    public AttendanceController(InputView inputView, OutputView outputView, StringConverter converter) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.converter = converter;
    }

    public void run() {
        try {
            List<String> rawAttendances = new DataReader().readRawAttendances();
            Crews crews = converter.convertToCrews(rawAttendances);
            Attendances attendances = setUpAttendances(crews, rawAttendances);

            Command command;
            do {
                command = readCommand();
                process(command, crews, attendances);
            } while (command != Command.QUIT);
        } catch (RuntimeException e) {
            outputView.printErrorMessage(e);
        }
    }

    private Attendances setUpAttendances(Crews crews, List<String> rawAttendances) {
        List<Attendance> attendances = new ArrayList<>();
        for (String rawAttendance : rawAttendances) {
            String[] attendanceInfos = converter.splitToNicknameAndTime(rawAttendance);
            Crew crew = crews.findByNickname(attendanceInfos[0]);
            attendances.add(converter.convertToAttendance(attendanceInfos[1], crew));
        }
        return new Attendances(attendances);
    }

    private Command readCommand() {
        LocalDate today = LocalDate.now();
        return Command.find(inputView.readCommand(today));
    }

    private void process(Command command, Crews crews, Attendances attendances) {
        if (command.isOne()) {
            checkIn(crews, attendances);
        }
    }

    private void checkIn(Crews crews, Attendances attendances) {
        LocalDate today = LocalDate.now();
        String rawNickname = inputView.readNickname();
        String rawCheckInTime = inputView.readCheckInTime();

        Crew crew = crews.findByNickname(rawNickname);

        Attendance attendance = converter.convertToAttendance(crew, rawCheckInTime, today);
        attendances.add(attendance);

        outputView.printCheckInResult(attendance);
    }
}
