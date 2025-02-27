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
import java.util.List;
import java.util.Map;
import view.InputView;
import view.OutputView;

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
        checkRiskOfExpulsionCrews(attendanceBook);
    }

    public void checkAttendanceForEachCrew(final AttendanceBook attendanceBook, final Crews crews) {
        final Crew crew = inputCrew(crews);
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

    public void checkRiskOfExpulsionCrews(final AttendanceBook attendanceBook) {
        final List<AttendanceHistory> attendanceHistories = attendanceBook.calculateRiskOfExpulsionHistory(now());
        final List<RiskOfExpulsionCrewDto> riskOfExpulsionCrewDtos = dtoConverter.convertToRiskOfExpulsionCrewDtos(
                attendanceHistories, now());
        outputView.printRiskOfExpulsionCrews(riskOfExpulsionCrewDtos);
    }

    private Crew inputCrew(final Crews crews) {
        final String crewName = inputView.readCrewName();
        return crews.findByName(crewName);
    }

    private LocalDate now() {
        return LocalDate.of(2024, 12, 13);
    }
}
