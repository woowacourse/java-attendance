package attendance.service;

import attendance.model.AttendanceDetail;
import attendance.model.Crew;
import attendance.model.Crews;
import attendance.model.FixedCustomClock;
import attendance.model.WoowaDate;
import attendance.model.loader.CrewDataLoader;
import attendance.model.loader.CrewRegistry;
import attendance.model.loader.RawAttendanceStore;

public class AttendanceProcessor {
    public static final String FILE_NAME = "attendances.csv";

    private final RawAttendanceStore rawAttendanceStore;
    private final CrewDataLoader crewDataLoader;
    private final FixedCustomClock fixedCustomClock;
    private final CrewRegistry crewRegistry;
    private final Crews crews;

    public AttendanceProcessor(CrewDataLoader crewDataLoader, CrewRegistry crewRegistry,
                               FixedCustomClock fixedCustomClock, Crews crews, RawAttendanceStore rawAttendanceStore) {
        this.crewDataLoader = crewDataLoader;
        this.fixedCustomClock = fixedCustomClock;
        this.crewRegistry = crewRegistry;
        this.crews = crews;
        this.rawAttendanceStore = rawAttendanceStore;
    }

    public void processAttendanceRecords() {
        crewDataLoader.load(FILE_NAME);

        for (RawAttendanceStore.RawAttendanceEntry entry : rawAttendanceStore.getAllEntries()) {
            Crew crew = crewRegistry.findCrewOrCreate(entry.crewName());
            if (!crews.containsCrew(crew.getName())) {
                crews.add(crew);
            }

            WoowaDate woowaDate = new WoowaDate(entry.dateTime().toLocalDate(), fixedCustomClock);
            AttendanceDetail attendanceDetail = new AttendanceDetail(woowaDate, entry.dateTime().toLocalTime());

            crew.getAttendanceHistory().addAttendanceDetail(attendanceDetail);

        }
    }
}
