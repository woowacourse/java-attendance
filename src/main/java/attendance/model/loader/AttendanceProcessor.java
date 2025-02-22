package attendance.model.loader;

import attendance.model.AttendanceDetail;
import attendance.model.Crew;
import attendance.model.CustomClock;
import attendance.model.WoowaDate;

public class AttendanceProcessor {
    private final AttendanceLoader attendanceLoader;
    private final CustomClock clock;
    private final CrewRegistry crewRegistry;

    public AttendanceProcessor(AttendanceLoader attendanceLoader,
                               CustomClock clock, CrewRegistry crewRegistry) {
        this.clock = clock;
        this.attendanceLoader = attendanceLoader;
        this.crewRegistry = crewRegistry;
    }

    public void processAttendanceRecords() {
        attendanceLoader.load();

        for (AttendanceLoader.RawAttendanceEntry entry : attendanceLoader.getAllEntries()) {
            processEntry(entry);
        }
    }

    private void processEntry(AttendanceLoader.RawAttendanceEntry entry) {
        Crew crew = crewRegistry.findCrewOrCreate(entry.crewName());

        WoowaDate woowaDate = new WoowaDate(entry.dateTime().toLocalDate(), clock);
        AttendanceDetail attendanceDetail = new AttendanceDetail(woowaDate, entry.dateTime().toLocalTime());

        crew.getAttendanceHistory().addAttendanceDetail(attendanceDetail);
    }
}