package controller;

import converter.StringConverter;
import java.util.List;
import model.Attendance;
import model.Attendances;
import model.Crew;
import model.Crews;
import util.DataReader;
import view.InputView;

public class AttendanceController {

    private final InputView inputView;
    private final StringConverter stringConverter;

    public AttendanceController(InputView inputView, StringConverter stringConverter) {
        this.inputView = inputView;
        this.stringConverter = stringConverter;
    }

    public void run() {
        List<String> rawAttendances = new DataReader().readAttendances("src/main/resources/attendances.csv");
        Crews crews = stringConverter.convertToCrews(rawAttendances);
        Attendances attendances = stringConverter.convertToAttendances(rawAttendances, crews);

    }

    private void checkInAttendance() {
        String rawNickname = inputView.readNickname();
        String rawCheckInTime = inputView.readCheckInTime();

        Attendance attendance = stringConverter.convertToAttendance(rawNickname, rawCheckInTime);

    }

    private void modifyAttendance() {
        String rawNickname = inputView.readNickname();
        String rawDay = inputView.readDay();
        String rawChangeTime = inputView.readChangeTime();

        Attendance changeAttendance = stringConverter.convertToAttendance(rawNickname, rawChangeTime);
    }

    private void checkAttendance() {
        String rawNickname = inputView.readNickname();
        Crew crew = stringConverter.convertToNickname(rawNickname);

    }
}
