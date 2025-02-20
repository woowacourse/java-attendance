package attendance.controller;

import static attendance.domain.AttendanceType.*;

import attendance.domain.AttendancePolicy;
import attendance.domain.AttendanceHistory;
import attendance.domain.CrewStatus;
import attendance.domain.dto.AttendanceHistoryDto;
import attendance.domain.AttendanceType;
import attendance.domain.Crew;
import attendance.domain.CrewManager;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class AttendanceController {
    private final InputView inputView;
    private final CrewManager crewManager;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, CrewManager crewManager, OutputView outputView) {
        this.inputView = inputView;
        this.crewManager = crewManager;
        this.outputView = outputView;
    }

    public void start() {
        Crew crew1 = new Crew("레오");
        Crew crew2 = new Crew("젠슨");
        crewManager.addCrew(crew1);
        crewManager.addCrew(crew2);
        LocalDate now = LocalDate.now();
        while (true) {
            String option = inputView.inputOption(now);
            if (option.equals("1")) {
                AttendancePolicy.checkHoliday(now);
                String crewName = inputView.inputCrewName();
                Crew crew = crewManager.findByCrewName(crewName);
                LocalTime attendanceTime = inputView.inputAttendanceTime();
                AttendanceType attendanceType = AttendancePolicy.checkAttendanceType(now, attendanceTime);
                AttendanceHistory attendanceHistory = new AttendanceHistory(LocalDateTime.of(now, attendanceTime),
                        attendanceType);
                crew.addAttendanceResult(attendanceHistory);
                outputView.printAttendanceResult(attendanceHistory);
                continue;
            }
            if (option.equals("2")) {
                String crewName = inputView.inputCrewName();
                Crew crew = crewManager.findByCrewName(crewName);
                LocalDate modifyDate = inputView.inputModifyDate(now);
                LocalTime modifyTime = inputView.inputAttendanceTime();
                AttendanceHistory attendanceHistory = crew.getAttendanceHistory(modifyDate);
                AttendanceHistoryDto beforeAttendanceHistoryDto = AttendanceHistoryDto.of(attendanceHistory); // 수정 전
                AttendanceHistory afterAttendanceHistory = crew.modifyAttendanceResult(attendanceHistory, modifyTime);
                outputView.printModifyAttendanceResult(beforeAttendanceHistoryDto, afterAttendanceHistory);
                continue;
            }
            if (option.equals("3")) {
                String crewName = inputView.inputCrewName();
                Crew crew = crewManager.findByCrewName(crewName);
                Map<AttendanceType, Integer> attendanceResult = crew.calculateAttendanceResult(now);
                outputView.printAttendanceHistories(now, crew);
                outputView.printAttendanceResult(attendanceResult);
                continue;
            }
            if (option.equals("4")) {
                List<Crew> dangerousCrews = crewManager.getDangerousCrews(now);
                dangerousCrews.sort(new Comparator<Crew>() {
                    @Override
                    public int compare(Crew o1, Crew o2) {
                        CrewStatus crewStatus1 = o1.calculateCrewStatus(o1.calculateAttendanceResult(now));
                        CrewStatus crewStatus2 = o2.calculateCrewStatus(o2.calculateAttendanceResult(now));
                        if (crewStatus1 == crewStatus2) {
                            return o1.getName().compareTo(o2.getName());
                        }
                        return crewStatus2.getOrder() - crewStatus1.getOrder();
                    }
                });
                outputView.printDangerousCrews(now, dangerousCrews);
                continue;
            }
            if (option.equals("Q")) {
                continue;
            }
            throw new IllegalArgumentException("잘못된 입력 입니다.");
        }
    }
}
