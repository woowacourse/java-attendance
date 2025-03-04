package attendance.controller.option;

import attendance.domain.Attendance;
import attendance.domain.AttendanceDate;
import attendance.domain.AttendanceTime;
import attendance.domain.Attendances;
import attendance.domain.CrewName;
import attendance.domain.CrewNames;
import attendance.dto.AttendanceCheckDto;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;

public class AttendanceCheckOption extends MenuOption {
    private static final LocalDate TODAY_LOCALDATE = LocalDate.now();

    private AttendanceDate attendanceDate;

    public AttendanceCheckOption(InputView inputView, CrewNames crewNames, Attendances attendances,
                                 OutputView outputView) {
        super(inputView, outputView, crewNames, attendances);
        this.attendanceDate = new AttendanceDate(TODAY_LOCALDATE);
    }

    @Override
    public void execute() {
        attendanceDate.checkAttendanceDateIsWeekend();
        String crewNameInput = inputView.readCrewName();
        CrewName crewName = crewNames.findCrewName(crewNameInput);

        String attendanceTimeInput = inputView.readAttendanceTime();
        AttendanceTime attendanceTime = new AttendanceTime(attendanceTimeInput);
        attendanceDate = new AttendanceDate(TODAY_LOCALDATE);
        Attendance attendance = new Attendance(crewName, attendanceDate, attendanceTime);

        attendances.addAttendance(attendance);
        outputView.printAttendanceCheckResult(AttendanceCheckDto.fromAttendance(attendance));
    }
}
