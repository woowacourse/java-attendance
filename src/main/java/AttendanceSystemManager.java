import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class AttendanceSystemManager {

    private final AttendanceHistories attendanceHistories;
    private final Crews crews;

    public AttendanceSystemManager(AttendanceHistories attendanceHistories, Crews crews) {
        this.attendanceHistories = attendanceHistories;
        this.crews = crews;
    }

    public AttendanceHistory registerNewAttendance(Crew crew, LocalDateTime requestedAt) {
        boolean existedHistory = attendanceHistories.checkExistenceByCrewAndDate(crew, requestedAt.toLocalDate());
        if (existedHistory) {
            throw new IllegalArgumentException("이미 존재하는 출석 기록입니다. 수정 기능을 이용해주세요.");
        }

        AttendanceHistory attendanceHistory = new AttendanceHistory(crew, requestedAt);
        attendanceHistories.addNewHistory(attendanceHistory);

        return attendanceHistory;
    }

    public void updateRegisteredAttendance(Crew crew, LocalDateTime requestedAt) {
        AttendanceHistory oldAttendanceHistory = attendanceHistories.findByCrewAndDate(crew, requestedAt.toLocalDate());
        AttendanceHistory newAttendanceHistory = new AttendanceHistory(crew, requestedAt);

        attendanceHistories.update(oldAttendanceHistory, newAttendanceHistory);
    }

    public Map<LocalDateTime, AttendanceType> findAllHistoriesOfCrew(Crew crew, LocalDate requestedAt) {
        List<AttendanceHistory> historiesOfCrew = attendanceHistories.findAllHistoriesOfCrewDateBefore(
                crew, requestedAt);

        return AttendanceTypeCounter.count(requestedAt, historiesOfCrew);
    }

    public List<PenaltyResultOfCrew> findExpulsionCandidates(LocalDate requestedAt) {
        List<Crew> registeredCrews = crews.getAll();

        List<PenaltyResultOfCrew> expulsionCandidates = new ArrayList<>();

        for (Crew crew : registeredCrews) {
            List<AttendanceHistory> historiesOfCrew = attendanceHistories.findAllHistoriesOfCrewDateBefore(
                    crew, requestedAt);

            Map<LocalDateTime, AttendanceType> attendanceTypeCountOfDates = AttendanceTypeCounter.count(
                    requestedAt,
                    historiesOfCrew);

            AttendanceTypeCount attendanceTypeCount = AttendanceTypeCount.createFrom(attendanceTypeCountOfDates);
            int adjustedAbsenceCount = attendanceTypeCount.getAdjustedAbsenceCount();

            if (PenaltyType.findByAbsenceCount(adjustedAbsenceCount).isAtExpulsionCandidateState()) {
                expulsionCandidates.add(PenaltyResultOfCrew.from(crew, attendanceTypeCount));
            }
        }

        return expulsionCandidates;
    }

    public List<PenaltyResultOfCrew> sortExpulsionCandidates(List<PenaltyResultOfCrew> expulsionCandidates) {
        Map<PenaltyType, Integer> priorityOfPenaltyTypes = Map.of(
                PenaltyType.BAN, 1,
                PenaltyType.ONE_ON_ONE, 2,
                PenaltyType.WARNING, 3
        );

        expulsionCandidates.sort(Comparator
                .comparing(
                        (PenaltyResultOfCrew candidate) -> priorityOfPenaltyTypes.getOrDefault(candidate.penaltyType(),
                                4))
                .thenComparing(
                        (PenaltyResultOfCrew candidate) -> candidate.attendanceTypeCount().getAdjustedAbsenceCount())
                .reversed()
                .thenComparing((PenaltyResultOfCrew candidate) -> candidate.crew().getName()));

        return expulsionCandidates;
    }
}
