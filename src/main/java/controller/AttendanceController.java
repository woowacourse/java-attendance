package controller;

import static constant.PathConstant.ATTENDANCE_FILE_PATH;

import dto.AttendanceCheckInRequest;
import dto.AttendanceCheckInResponse;
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
import view.OutputView;

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
                processWithRetry(() -> recordAttendance(attendances));
            }
            if (option.equals(Option.TWO)) {
                processWithRetry(this::updateAttendance);
            }
            if (option.equals(Option.THREE)) {
                processWithRetry(this::getAttendanceHistoryByCrew);
            }
            if (option.equals(Option.FOUR)) {
                processWithRetry(this::findRiskCrews);
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
        AttendanceOptionRequest request = InputView.readAttendanceOptionRequest(dateTimeGenerator);
        return Option.find(request.option());
    }

    private void recordAttendance(Attendances attendances) {
        AttendanceCheckInRequest request = InputView.readAttendanceCheckInRequest();

        AttendanceCheckInResponse response =
                attendances.add(request.nickname(), request.checkInTime(), dateTimeGenerator);

        OutputView.printCheckIn(response);
    }

    private void updateAttendance() {
        AttendanceUpdateRequest request = InputView.readAttendanceUpdateRequest(dateTimeGenerator);

    }

    private void getAttendanceHistoryByCrew() {
        AttendanceHistoryRequest request = InputView.readAttendanceHistoryRequest();

    }

    private void findRiskCrews() {

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
