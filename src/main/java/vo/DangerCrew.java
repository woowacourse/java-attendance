package vo;

import domain.Crew;
import domain.Penalty;

public record DangerCrew(
    Crew crew,
    int late,
    int absence,
    Penalty penalty
) {

}
