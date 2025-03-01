package attendance.controller;

import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceDate;
import attendance.domain.AttendanceDateTime;
import attendance.domain.AttendanceStatus;
import attendance.domain.AttendanceTime;
import attendance.repository.AttendanceBookRepository;
import attendance.view.input.InputView;
import attendance.view.input.MenuOption;
import attendance.view.ouput.OutputView;
import java.time.LocalDate;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceBookRepository attendanceBookRepository;

    public AttendanceController(
        final InputView inputView,
        final OutputView outputView,
        final AttendanceBookRepository attendanceBookRepository
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceBookRepository = attendanceBookRepository;
    }

    public void run(LocalDate currentDate) {
        boolean isRunning = true;

        while (isRunning) {
            isRunning = processMenu(currentDate);
        }
    }

    private boolean processMenu(final LocalDate currentDate) {
        try {
            final MenuOption menuOption = inputView.readMenuOption(currentDate);
            runMenuOption(menuOption, currentDate);
            return menuOption != MenuOption.EXIT;
        } catch (IllegalArgumentException e) {
            outputView.printMessage(e.getMessage());
            return true;
        }
    }

    private void runMenuOption(
        final MenuOption menuOption,
        final LocalDate currentDate
    ) {
        if (menuOption == MenuOption.CHECK) {
            handleCheckAttendance(currentDate);
        }
    }

    private void handleCheckAttendance(final LocalDate currentDate) {
        final AttendanceBook attendanceBook = findAttendanceBook(
            inputView.readCrewNickName());

        final AttendanceDate attendanceDate = AttendanceDate.from(
            currentDate);
        final AttendanceTime attendanceTime = AttendanceTime.from(
            inputView.readAttendanceDate());

        final AttendanceDateTime attendanceDateTime = saveAttendanceDateTime(
            attendanceBook, attendanceDate, attendanceTime);
        final AttendanceStatus attendanceStatus = AttendanceStatus.from(
            attendanceDateTime);

        outputView.printAttendanceDateTime(
            attendanceDateTime, attendanceStatus);
    }

    private AttendanceDateTime saveAttendanceDateTime(
        final AttendanceBook attendanceBook,
        final AttendanceDate attendanceDate,
        final AttendanceTime attendanceTime
    ) {
        attendanceBook.save(new AttendanceDateTime(
            attendanceDate, attendanceTime));
        return attendanceBook.retrieveByDate(
            attendanceDate);
    }

    private AttendanceBook findAttendanceBook(final String crewNickName) {
        return attendanceBookRepository.findByCrewNickname(
                crewNickName)
            .orElseThrow(
                () -> new IllegalArgumentException("해당 닉네임의 출석부가 없습니다."));
    }
}
