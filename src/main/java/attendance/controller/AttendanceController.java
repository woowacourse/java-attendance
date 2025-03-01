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

        if (menuOption == MenuOption.EDIT) {
            handleEditAttendance(currentDate);
        }
    }

    private void handleCheckAttendance(final LocalDate currentDate) {
        final AttendanceBook attendanceBook = findAttendanceBook(
            inputView.readCrewNickName());

        final AttendanceDate attendanceDate = AttendanceDate.from(
            currentDate);
        final AttendanceTime attendanceTime = AttendanceTime.from(
            inputView.readAttendanceTime());

        final AttendanceDateTime attendanceDateTime = saveAttendanceDateTime(
            attendanceBook, attendanceDate, attendanceTime);

        printAttendanceSave(attendanceDateTime);
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

    private void printAttendanceSave(
        final AttendanceDateTime attendanceDateTime
    ) {
        final AttendanceStatus attendanceStatus = AttendanceStatus.from(
            attendanceDateTime);

        outputView.printAttendanceDateTime(
            attendanceDateTime, attendanceStatus);
    }

    private void handleEditAttendance(final LocalDate currentDate) {
        final AttendanceBook attendanceBook = findAttendanceBook(
            inputView.readUpdateNickName());

        final AttendanceDate attendanceDate = new AttendanceDate(
            currentDate.getYear(),
            currentDate.getMonthValue(),
            inputView.readModifyAttendanceDate());

        final AttendanceDateTime originalAttendanceDateTime = attendanceBook.retrieveByDate(
            attendanceDate);

        final AttendanceDateTime modifiedAttendanceDateTime = modifyAttendance(
            attendanceBook, attendanceDate);

        printAttendanceModification(
            attendanceDate,
            originalAttendanceDateTime, modifiedAttendanceDateTime);
    }

    private AttendanceDateTime modifyAttendance(
        final AttendanceBook attendanceBook,
        final AttendanceDate attendanceDate
    ) {
        final AttendanceTime modifyAttendanceTime = AttendanceTime.from(
            inputView.readModifyAttendanceTime());
        attendanceBook.modify(new AttendanceDateTime(
            attendanceDate, modifyAttendanceTime));
        return attendanceBook.retrieveByDate(
            attendanceDate);
    }

    private void printAttendanceModification(
        final AttendanceDate attendanceDate,
        final AttendanceDateTime originalAttendanceDateTime,
        final AttendanceDateTime modifiedAttendanceDateTime
    ) {
        final AttendanceStatus originalAttendanceStatus = AttendanceStatus.from(
            originalAttendanceDateTime);
        final AttendanceStatus modifiedAttendanceStatus = AttendanceStatus.from(
            modifiedAttendanceDateTime);

        outputView.printModifyAttendanceDateTime(
            attendanceDate,
            originalAttendanceDateTime.getAttendanceTime(),
            originalAttendanceStatus,
            modifiedAttendanceDateTime.getAttendanceTime(),
            modifiedAttendanceStatus
        );
    }

    private AttendanceBook findAttendanceBook(final String crewNickName) {
        return attendanceBookRepository.findByCrewNickname(
                crewNickName)
            .orElseThrow(
                () -> new IllegalArgumentException("해당 닉네임의 출석부가 없습니다."));
    }
}
