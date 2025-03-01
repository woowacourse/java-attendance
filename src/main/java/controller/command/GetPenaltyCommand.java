package controller.command;

import domain.AttendanceBook;
import domain.AttendanceStatus;
import domain.Penalty;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import view.OutputView;
import view.OutputView.DangerCrewVO;

public class GetPenaltyCommand implements ControllerCommand {

    @Override
    public void execute(AttendanceBook book) {
        LocalDate from = LocalDate.now().withDayOfMonth(1);
        LocalDate to = LocalDate.now().minusDays(1);

        List<DangerCrewVO> dangerCrewVOs = collectDangerCrews(book, from, to);
        OutputView.printDangerCrews(dangerCrewVOs);
    }

    private static List<DangerCrewVO> collectDangerCrews(AttendanceBook book, LocalDate from, LocalDate to) {
        return book.getAllCrews()
            .stream()
            .map(crew -> {
                Map<AttendanceStatus, Integer> map = book.countAttendanceStatuses(crew, from, to);

                int absence = map.get(AttendanceStatus.ABSENCE);
                int late = map.get(AttendanceStatus.LATE);
                Penalty penalty = Penalty.determine(late, absence);

                return new DangerCrewVO(crew, absence, late, penalty);
            })
            .filter(vo -> vo.penalty() != Penalty.NONE)
            .collect(Collectors.toList());
    }
}
