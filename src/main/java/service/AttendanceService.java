package service;

import domain.AttendanceBook;
import domain.AttendanceDateTime;
import domain.AttendanceStatus;
import domain.Crew;
import domain.Penalty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import vo.AttendanceStatusCount;
import vo.DangerCrew;
import vo.ModifyResult;

public class AttendanceService {

    private final AttendanceBook book;

    public AttendanceService(AttendanceBook book) {
        this.book = book;
    }

    public Crew getCrewByNickName(String nickName) {
        return book.findCrewByName(nickName)
            .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 닉네임입니다."));
    }

    public AttendanceDateTime attend(Crew crew, LocalDate date, LocalTime time) {
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.from(dateTime);
        book.attend(crew, attendanceDateTime);

        return book.getRecordByCrewAndDate(crew, date);
    }

    public ModifyResult modify(Crew crew, LocalDate dateToBeModified, LocalTime timeToModify) {
        AttendanceDateTime before = book.getRecordByCrewAndDate(crew, dateToBeModified);

        LocalDateTime dateTime = LocalDateTime.of(dateToBeModified, timeToModify);
        AttendanceDateTime after = AttendanceDateTime.from(dateTime);

        book.modify(crew, dateToBeModified, after);
        return new ModifyResult(before, after);
    }

    public List<AttendanceDateTime> findAllRecordsByCrewBetween(Crew crew, LocalDate fromInclusive, LocalDate toInclusive) {
        return book.listAttendancesOfCrew(crew, fromInclusive, toInclusive);
    }

    public AttendanceStatusCount countAttendanceStatusesByCrewBetween(Crew crew,
        LocalDate fromInclusive, LocalDate toInclusive) {
        Map<AttendanceStatus, Integer> map = book.countAttendanceStatuses(crew, fromInclusive, toInclusive);
        int onTime = map.get(AttendanceStatus.ON_TIME);
        int late = map.get(AttendanceStatus.LATE);
        int absence = map.get(AttendanceStatus.ABSENCE);

        return new AttendanceStatusCount(onTime, late, absence);
    }

    public List<DangerCrew> findDangerCrews(LocalDate fromInclusive, LocalDate toInclusive) {
        return book.getAllCrews()
            .stream()
            .map(crew -> createDangerCrew(fromInclusive, toInclusive, crew))
            .filter(dangerCrew -> dangerCrew.penalty() != Penalty.NONE)
            .collect(Collectors.toList());
    }

    private DangerCrew createDangerCrew(LocalDate fromInclusive, LocalDate toInclusive, Crew crew) {
        AttendanceStatusCount counts =
            countAttendanceStatusesByCrewBetween(crew, fromInclusive, toInclusive);
        Penalty penalty = Penalty.determine(counts.late(), counts.absence());

        return new DangerCrew(crew, counts.late(), counts.absence(), penalty);
    }
}
