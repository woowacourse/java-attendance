package controller;

import static constant.PathConstant.ATTENDANCE_FILE_PATH;

import dto.AttendanceCheckInRequest;
import dto.AttendanceCheckInResponse;
import dto.AttendanceHistoryRequest;
import dto.AttendanceHistoryResponse;
import dto.AttendanceOptionRequest;
import dto.AttendanceUpdateRequest;
import dto.AttendanceUpdateResponse;
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
            Option option = processWithRetry(this::selectOption);

            if (option.equals(Option.ONE)) {
                processWithRetry(() -> checkInAttendance(attendances));
            }
            if (option.equals(Option.TWO)) {
                processWithRetry(() -> updateAttendance(attendances));
            }
            if (option.equals(Option.THREE)) {
                processWithRetry(() -> findAttendanceHistoryByCrew(attendances));
            }
            if (option.equals(Option.FOUR)) {
                processWithRetry(() -> findRiskCrews(attendances));
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

    private Option selectOption() {
        AttendanceOptionRequest request = InputView.readAttendanceOptionRequest(dateTimeGenerator);
        return Option.find(request.option());
    }

    private void checkInAttendance(Attendances attendances) {
        AttendanceCheckInRequest request = InputView.readAttendanceCheckInRequest();
        AttendanceCheckInResponse response = attendances.add(request, dateTimeGenerator);
        OutputView.printCheckInAttendance(response);
    }

    private void updateAttendance(Attendances attendances) {
        AttendanceUpdateRequest request = InputView.readAttendanceUpdateRequest(dateTimeGenerator);
        AttendanceUpdateResponse response = attendances.update(request, dateTimeGenerator);
        OutputView.printUpdateAttendance(response);
    }

    private void findAttendanceHistoryByCrew(Attendances attendances) {
        AttendanceHistoryRequest request = InputView.readAttendanceHistoryRequest();
        AttendanceHistoryResponse response = attendances.findHistoryByCrew(request);
        OutputView.printAttendanceHistory(response);
    }

    private void findRiskCrews(Attendances attendances) {

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
