package domain;

import dto.CrewResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Crews {
    private final List<Crew> crews;

    public Crews() {
        crews = new ArrayList<>();
    }

    public void addCrew(final String name) {
        crews.add(new Crew(name));
    }

    public void initAttendStatus(final String name, final LocalDateTime target) {
        if (!hasCrewName(name)) {
            addCrew(name);
        }
        addAttendStatus(name, target);
    }

    private void addAttendStatus(final String name, final LocalDateTime target) {
        Crew crew = findCrewByName(name);
        crew.addAttendStatus(target);
    }

    public LocalTime getAttendanceTime(final String name, final LocalDate date) {
        Crew crew = findCrewByName(name);
        return crew.getAttendanceTime(date);
    }

    public boolean hasCrewName(final String name) {
        return crews.stream().anyMatch(crew -> crew.isNameMatch(name));
    }

    public Crew findCrewByName(final String name) {
        return crews.stream()
                .filter(crew -> crew.isNameMatch(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 닉네임입니다."));
    }

    public CrewResponse createCrewResponse(Crew crew) {
        return crew.createCrewResponse();
    }

    public List<CrewResponse> getCrewResponseWithRisk() {
        return crews.stream()
                .filter(crew ->
                        crew.calculateRiskStatus()
                                .hasRisk())
                .map(Crew::createCrewRiskStatusResponse)
                .toList();
    }
}
