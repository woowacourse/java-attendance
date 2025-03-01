package controller;

import components.DtoConverter;
import components.SavedDataLoader;
import domain.AttendanceBook;
import domain.AttendanceHistory;
import domain.AttendanceRecord;
import domain.AttendanceStatus;
import domain.Crew;
import domain.Crews;
import domain.RiskOfExpulsionStatus;
import dto.RiskOfExpulsionCrewDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import utils.RetryHandler;
import view.InputView;
import view.OutputView;
import view.UserCommand;

public class AttendanceController {
    private final SavedDataLoader savedDataLoader;
    private final InputView inputView;
    private final OutputView outputView;
    private final DtoConverter dtoConverter;

    public AttendanceController(final SavedDataLoader savedDataLoader, final InputView inputView,
                                final OutputView outputView, final DtoConverter dtoConverter) {
        this.savedDataLoader = savedDataLoader;
        this.inputView = inputView;
        this.outputView = outputView;
        this.dtoConverter = dtoConverter;
    }

    public void run() {
        final AttendanceBook attendanceBook = new AttendanceBook();
        final Crews crews = savedDataLoader.loadCrews();
        savedDataLoader.loadAttendances(attendanceBook, crews);
        retryUntilQuit(attendanceBook, crews);
    }

    private void retryUntilQuit(final AttendanceBook attendanceBook, final Crews crews) {
        UserCommand userCommand = inputUserCommand();
        while (userCommand != UserCommand.QUIT) {
            runCommand(userCommand, attendanceBook, crews);
            userCommand = inputUserCommand();
        }
    }

    private void runCommand(final UserCommand userCommand, final AttendanceBook attendanceBook, final Crews crews) {
        switch (userCommand) {
            case CHECK_ATTENDANCE -> attendance(attendanceBook, crews);
            case UPDATE_ATTENDANCE -> updateAttendance(attendanceBook, crews);
            case CHECK_ATTENDANCE_HISTORY -> checkAttendanceForEachCrew(attendanceBook, crews);
            case CHECK_RISK_OF_EXPULSION_CREWS -> checkRiskOfExpulsionCrews(attendanceBook);
        }
    }

    private void attendance(final AttendanceBook attendanceBook, final Crews crews) {
        final Crew crew = RetryHandler.retryUntilNotException(this::inputCrew, crews,
                outputView::printExceptionMessage);
        final LocalTime attendanceTime = RetryHandler.retryUntilNotException(inputView::readAttendanceTime,
                outputView::printExceptionMessage);

        final AttendanceHistory attendanceHistory = attendanceBook.findByCrew(crew);
        final AttendanceRecord attendanceRecord = attendanceHistory.attendance(LocalDateTime.of(now(), attendanceTime));

        outputView.printAttendanceRecord(dtoConverter.convertToAttendanceRecordDto(attendanceRecord));
    }

    private void updateAttendance(final AttendanceBook attendanceBook, final Crews crews) {
        final Crew crew = RetryHandler.retryUntilNotException(this::inputUpdateCrew, crews,
                outputView::printExceptionMessage);
        final int updateMonthOfDay = RetryHandler.retryUntilNotException(inputView::readUpdateMonthOfDay,
                outputView::printExceptionMessage);
        final LocalTime updateTime = RetryHandler.retryUntilNotException(inputView::readUpdateTime,
                outputView::printExceptionMessage);

        final AttendanceHistory attendanceHistory = attendanceBook.findByCrew(crew);
        final AttendanceRecord beforeAttendanceRecord = attendanceHistory.updateTimeByDate(
                LocalDateTime.of(now().withDayOfMonth(updateMonthOfDay), updateTime));
        final AttendanceRecord afterAttendanceRecord = attendanceHistory.findByDate(
                now().withDayOfMonth(updateMonthOfDay));

        outputView.printUpdateAttendanceResult(dtoConverter.convertToAttendanceRecordDto(beforeAttendanceRecord),
                dtoConverter.convertToAttendanceRecordDto(afterAttendanceRecord));

    }

    private void checkAttendanceForEachCrew(final AttendanceBook attendanceBook, final Crews crews) {
        final Crew crew = RetryHandler.retryUntilNotException(this::inputCrew, crews,
                outputView::printExceptionMessage);
        outputView.printIntroduceAttendanceRecords(crew.getName());
        final AttendanceHistory attendanceHistory = outputAttendanceRecord(attendanceBook, crew);
        outputAttendanceStatistics(attendanceHistory);
        outputRiskOfExpulsion(attendanceHistory);
    }

    private AttendanceHistory outputAttendanceRecord(final AttendanceBook attendanceBook, final Crew crew) {
        final AttendanceHistory attendanceHistory = attendanceBook.findByCrew(crew);
        final List<AttendanceRecord> records = attendanceHistory.findAllUntilBeforeToday(now());
        outputView.printAttendanceRecords(dtoConverter.convertToAttendanceRecordDtos(records));
        return attendanceHistory;
    }

    private void outputAttendanceStatistics(final AttendanceHistory attendanceHistory) {
        final Map<AttendanceStatus, Integer> statistics = attendanceHistory.calculateAttendanceStatusStatistics(now());
        outputView.printAttendanceStatistics(dtoConverter.convertToStringStatistics(statistics));
    }

    private void outputRiskOfExpulsion(final AttendanceHistory attendanceHistory) {
        if (attendanceHistory.isRiskOfExpulsion(now())) {
            final RiskOfExpulsionStatus status = attendanceHistory.calculateRiskOfExpulsionStatus(now());
            outputView.printRiskOfExpulsion(dtoConverter.convertRiskOfExpulsionStatusToString(status));
        }
    }

    private void checkRiskOfExpulsionCrews(final AttendanceBook attendanceBook) {
        final List<AttendanceHistory> attendanceHistories = attendanceBook.calculateRiskOfExpulsionHistory(now());
        final List<RiskOfExpulsionCrewDto> riskOfExpulsionCrewDtos = dtoConverter.convertToRiskOfExpulsionCrewDtos(
                attendanceHistories, now());
        outputView.printRiskOfExpulsionCrews(riskOfExpulsionCrewDtos);
    }

    private UserCommand inputUserCommand() {
        outputView.printIntroduceCommand(now());
        return inputView.readUserCommand();
    }

    private Crew inputCrew(final Crews crews) {
        final String crewName = inputView.readCrewName();
        return crews.findByName(crewName);
    }

    private Crew inputUpdateCrew(final Crews crews){
        final String crewName = inputView.readUpdateCrewName();
        return crews.findByName(crewName);
    }

    private LocalDate now() {
        return LocalDate.of(2024, 12, 13);
    }
}
