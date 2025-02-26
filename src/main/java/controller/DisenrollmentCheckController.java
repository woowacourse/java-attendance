package controller;

import domain.AttendanceCustomDate;
import domain.AttendanceStatistic;
import domain.Crew;
import domain.CrewAttendances;
import view.dto.DisenrollmentCheckResponse;
import view.OutputView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DisenrollmentCheckController implements Controller{
    private final OutputView outputView;
    private final CrewAttendances crewAttendances;

    public DisenrollmentCheckController(
            OutputView outputView,
            CrewAttendances crewAttendances
    ) {
        this.outputView = outputView;
        this.crewAttendances = crewAttendances;
    }

    @Override
    public void run() {
        LocalDate now = AttendanceCustomDate.now().toLocalDate();
        List<Crew> targetCrews = crewAttendances.getDisenrollCrews(now.withDayOfMonth(1), now);
        List<DisenrollmentCheckResponse> responses = getDisenrollResponses(targetCrews, now.withDayOfMonth(1), now);
        outputView.printDisenrollmentCheckResult(responses);
    }

    private List<DisenrollmentCheckResponse> getDisenrollResponses(
            List<Crew> targetCrews,
            LocalDate start,
            LocalDate end
    ) {
        List<DisenrollmentCheckResponse> responses = new ArrayList<>();
        for (Crew crew : targetCrews) {
            AttendanceStatistic statistic = crewAttendances.getAttendanceStatistic(crew.getName(), start, end);
            DisenrollmentCheckResponse response = new DisenrollmentCheckResponse(
                    crew.getName(),
                    statistic.getTotalAbsenceCount(),
                    statistic.getAbsenceCount(),
                    statistic.getLateCount(),
                    statistic.getCrewStatus()
            );
            responses.add(response);
        }
        return responses;
    }
}
