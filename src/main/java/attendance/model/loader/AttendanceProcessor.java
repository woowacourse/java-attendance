package attendance.model.loader;

import attendance.model.AttendanceDetail;
import attendance.model.Crew;
import attendance.model.FixedCustomClock;
import attendance.model.WoowaDate;

public class AttendanceProcessor {
    private final AttendanceLoader attendanceLoader;
    private final FixedCustomClock fixedCustomClock;
    private final CrewRegistry crewRegistry;

    public AttendanceProcessor(AttendanceLoader attendanceLoader,
                               FixedCustomClock fixedCustomClock, CrewRegistry crewRegistry) {
        this.fixedCustomClock = fixedCustomClock;
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

        WoowaDate woowaDate = new WoowaDate(entry.dateTime().toLocalDate(), fixedCustomClock);
        AttendanceDetail attendanceDetail = new AttendanceDetail(woowaDate, entry.dateTime().toLocalTime());

        crew.getAttendanceHistory().addAttendanceDetail(attendanceDetail);
    }
}