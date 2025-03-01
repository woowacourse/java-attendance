package controller.command;

import domain.AttendanceBook;
import domain.AttendanceDateTime;
import domain.AttendanceStatus;
import domain.Crew;
import domain.Penalty;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import view.InputView;
import view.OutputView;

public class GetRecordsCommand implements ControllerCommand {

    @Override
    public void execute(AttendanceBook book) {
        String nickName = InputView.readNickName();
        Crew crew = retrieveCrew(book, nickName);

        LocalDate from = LocalDate.now().withDayOfMonth(1);
        LocalDate to = LocalDate.now().minusDays(1);
        List<AttendanceDateTime> attendances = book.listAttendancesOfCrew(crew, from, to);

        Map<AttendanceStatus, Integer> counts = book.countAttendanceStatuses(crew, from, to);
        int onTime = counts.get(AttendanceStatus.ON_TIME);
        int late = counts.get(AttendanceStatus.LATE);
        int absence = counts.get(AttendanceStatus.ABSENCE);
        Penalty penalty = Penalty.determine(late, absence);

        OutputView.printAttendanceList(crew, attendances);
        OutputView.printCountAndPenalty(onTime, late, absence, penalty);
    }

    private Crew retrieveCrew(AttendanceBook book, String nickName) {
        return book.findCrewByName(nickName)
            .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 닉네임입니다."));
    }
}
