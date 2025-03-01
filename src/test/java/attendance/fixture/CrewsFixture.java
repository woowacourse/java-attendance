package attendance.fixture;

import java.util.Arrays;

import attendance.domain.Crew;
import attendance.domain.Crews;

public class CrewsFixture {

    public static Crews of(String... names) {
        return new Crews(
            Arrays.stream(names)
                .map(Crew::new)
                .toList()
        );
    }
}
