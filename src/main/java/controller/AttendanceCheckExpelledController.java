package controller;

import domain.Attendance;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class AttendanceCheckExpelledController implements AttendanceController {

    @Override
    public void process(Attendance attendance, LocalDate nowDate) {
        List<String> expelledCrews = attendance.checkExpelledCrew();
        expelledCrews.sort(Comparator.comparing(attendance::getAbsentCount)
                .thenComparing(attendance::getLateCountForSort).reversed()
                .thenComparing(name->name));

        outputView.printExpelledCrewHeader();
        for (String crew : expelledCrews) {
            outputView.printExpelledCrew(crew, attendance.getCrewAttendanceStatus(crew));
        }
    }
}
