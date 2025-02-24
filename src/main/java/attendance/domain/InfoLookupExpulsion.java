package attendance.domain;

import java.util.ArrayList;
import java.util.List;

public class InfoLookupExpulsion {
    private final List<List<String>> crewsExpelExpectedInfo;

    private final List<InfoLookupExpelRecord> crewExpelRecords = new ArrayList<>();

    public InfoLookupExpulsion(List<List<String>> crewsExpelExpectedInfo) {
        this.crewsExpelExpectedInfo = crewsExpelExpectedInfo;
    }

    public void createCrewExpelRecords() {
        for (List<String> expelRecord : crewsExpelExpectedInfo) {
            crewExpelRecords.add(new InfoLookupExpelRecord(expelRecord));
        }
    }

    public List<InfoLookupExpelRecord> getCrewExpelRecords() {
        return crewExpelRecords;
    }
}
