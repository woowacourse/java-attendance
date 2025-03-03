package controller;

import controller.exception.ProgramQuitException;
import domain.Attendance;
import domain.AttendanceBook;
import domain.AttendanceCounts;
import domain.AttendanceDate;
import domain.AttendanceStatistics;
import domain.AttendanceTime;
import domain.Attendances;
import domain.Nickname;
import domain.command.AttendanceCommand;
import domain.command.AttendanceCommandHandler;
import domain.policy.absent.AbsentRule;
import reader.AttendanceFileReader;
import reader.exception.FileReadException;
import util.FormatUtil;
import util.TimeMachine;
import view.InputView;
import view.OutputView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceCommandHandler commandHandler;

    public AttendanceController(InputView inputView,
                                OutputView outputView,
                                AttendanceCommandHandler attendanceCommandHandler) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.commandHandler = attendanceCommandHandler;
        setMenu();
    }

    public void run(AttendanceFileReader attendanceFileReader,
                    String attendanceFilePath) throws IOException {
        setDayOfMonth();

        AttendanceBook attendanceBook =
                initializeByAttendanceFile(attendanceFileReader, attendanceFilePath);

        RepeatUntilUserQuitSelect.repeat(() -> selectMenu(attendanceBook), outputView);
    }

    private void setMenu() {
        commandHandler.addAction(AttendanceCommand.ATTEND, this::attend);
        commandHandler.addAction(AttendanceCommand.UPDATE, this::updateAttendance);
        commandHandler.addAction(AttendanceCommand.DISPLAY, this::displayAttendances);
        commandHandler.addAction(AttendanceCommand.EXPULSION, this::displayExpulsionCandidates);
        commandHandler.addAction(AttendanceCommand.QUIT, this::quitProgram);
    }

    private void setDayOfMonth() {
        int input;
        do {
            input = inputView.inputToday();
        } while (!TimeMachine.timeTravelAt(input));
    }

    private AttendanceBook initializeByAttendanceFile(AttendanceFileReader attendanceFileReader,
                                                      String attendanceFilePath) throws FileReadException {
        try {
            AttendanceBook attendanceBook = AttendanceBook.initialize();
            attendanceBook.loadAttendance(attendanceFileReader, attendanceFilePath);
            return attendanceBook;
        } catch (FileReadException e) {
            outputView.printErrorMessage(e.getMessage());
            throw e;
        }
    }

    private void selectMenu(AttendanceBook attendanceBook) {
        AttendanceCommand command = AttendanceCommand.from(inputView.inputMenu());
        commandHandler.execute(command, attendanceBook);
    }

    private void attend(AttendanceBook attendanceBook) {
        Nickname nickname = Nickname.from(inputView.inputNickname());

        AttendanceTime attendanceTime = AttendanceTime.from(LocalTime.parse(inputView.inputTime(), FormatUtil.TIME_FORMATTER));
        AttendanceDate attendanceDate = AttendanceDate.from(TimeMachine.dateOfNow());
        Attendance attendance = Attendance.of(attendanceDate, attendanceTime);

        outputView.printAttendance(attendanceBook.add(nickname, attendance));
    }

    private void updateAttendance(AttendanceBook attendanceBook) {
        Nickname nickname = Nickname.from(inputView.inputUpdateNickname());

        Attendances attendances = attendanceBook.findByNickname(nickname);

        int day = inputView.inputUpdateDate();
        AttendanceDate attendanceDate = AttendanceDate.from(LocalDate.of(TimeMachine.FIXED_YEAR, TimeMachine.FIXED_MONTH, day));
        AttendanceTime attendanceTimeForUpdate = AttendanceTime.from(LocalTime.parse(inputView.inputUpdateTime(), FormatUtil.TIME_FORMATTER));
        Attendance attendanceForUpdate = Attendance.of(attendanceDate, attendanceTimeForUpdate);

        Attendance originalAttendance = attendances.findByDate(attendanceDate);
        Attendance updatedAttendance = attendances.update(attendanceForUpdate);

        outputView.printAttendanceUpdate(originalAttendance, updatedAttendance);
    }

    private void displayAttendances(AttendanceBook attendanceBook) {
        Nickname nickname = Nickname.from(inputView.inputNickname());

        Attendances attendances = attendanceBook.findByNickname(nickname);

        outputView.printAttendanceIntro(nickname.value());
        outputView.printAttendances(attendances);

        AttendanceCounts attendanceCounts = attendances.calculateAttendanceCounts(nickname);
        outputView.printAttendanceStatistics(attendanceCounts);

        AbsentRule absentRule = AbsentRule.calculateAbsentPolicy(attendanceCounts);
        outputView.printAbsentPolicy(absentRule);
    }

    private void displayExpulsionCandidates(AttendanceBook attendanceBook) {
        AttendanceStatistics expulsionCandidates = attendanceBook.findExpulsionCandidates().orderByExpulsionRiskLevelAndNickname();
        outputView.printRiskOfExpulsionBanner();
        outputView.printExpulsionCandidate(expulsionCandidates);
    }

    private void quitProgram(AttendanceBook attendanceBook) {
        throw new ProgramQuitException(ProgramQuitException.MESSAGE);
    }
}
