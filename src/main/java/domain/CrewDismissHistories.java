package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrewDismissHistories {
    private final List<CrewDismissHistory> crewDismisses;

    public CrewDismissHistories() {
        crewDismisses = new ArrayList<>();
    }

    public void addDismissHistory(String crewNickname,
                                  SystemTimeCrewAttendanceHistories systemTimeCrewAttendanceHistories) {
        CrewDismiss crewDismiss = systemTimeCrewAttendanceHistories.crewDismiss();
        if (crewDismiss.dismissStatus() == DismissStatus.ELSE) {
            return;
        }
        crewDismisses.add(new CrewDismissHistory(crewNickname, crewDismiss));
    }

    public List<CrewDismissHistory> crewDismissHistories() {
        Collections.sort(crewDismisses);
        return crewDismisses;
    }
}
