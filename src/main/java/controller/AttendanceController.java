package controller;

import static constant.PathConstant.ATTENDANCE_FILE_PATH;

import dto.AttendanceCheckInRequest;
import dto.AttendanceHistoryRequest;
import dto.AttendanceOptionRequest;
import dto.AttendanceUpdateRequest;
import java.util.List;
import java.util.function.Supplier;
import model.Attendances;
import model.Option;
import util.DateTimeGenerator;
import util.FileParser;
import view.InputView;

public class AttendanceController {

    private final DateTimeGenerator dateTimeGenerator;

    public AttendanceController(DateTimeGenerator dateTimeGenerator) {
        this.dateTimeGenerator = dateTimeGenerator;
    }

    public void run() {
        Attendances attendances = initialize();

        while (true) {
            Option option = processWithRetry(this::option);

            if (option.equals(Option.ONE)) {
                processWithRetry(this::checkIn);
            }
            if (option.equals(Option.TWO)) {
                processWithRetry(this::checkIn);
            }
            if (option.equals(Option.THREE)) {
                processWithRetry(this::update);
            }
            if (option.equals(Option.FOUR)) {
                processWithRetry(this::history);
            }
            if (option.equals(Option.QUIT)) {
                break;
            }
        }
    }

    public Attendances initialize() {
        List<String> lines = FileParser.readLines(ATTENDANCE_FILE_PATH.getPath());
        return Attendances.from(lines, dateTimeGenerator);
    }

    private Option option() {
        AttendanceOptionRequest attendanceOptionRequest = InputView.readAttendanceOptionRequest(dateTimeGenerator);
        return Option.find(attendanceOptionRequest.option());
    }

    private void checkIn() {
        AttendanceCheckInRequest attendanceCheckInRequest = InputView.readAttendanceCheckInRequest();

    }

    private void update() {
        AttendanceUpdateRequest attendanceUpdateRequest = InputView.readAttendanceUpdateRequest(dateTimeGenerator);

    }

    private void history() {
        AttendanceHistoryRequest attendanceHistoryRequest = InputView.readAttendanceHistoryRequest();

    }

    private <T> T processWithRetry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void processWithRetry(Runnable runnable) {
        while (true) {
            try {
                runnable.run();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
