package attendance.domain;

public class CrewRisk {
    private final Crew crew;
    private final AbsenceInfo absenceInfo;

    public CrewRisk(Crew crew, AbsenceInfo absenceInfo) {
        this.crew = crew;
        this.absenceInfo = absenceInfo;
    }

    public Crew getCrew() {
        return crew;
    }

    public AbsenceInfo getAbsenceInfo() {
        return absenceInfo;
    }
}
