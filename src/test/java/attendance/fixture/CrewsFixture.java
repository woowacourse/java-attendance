package attendance.fixture;

import attendance.domain.Crews;

public class CrewsFixture {

    public static Crews onlyPobi() {
        Crews crews = Crews.generate();
        crews.add(CrewFixture.pobi());
        return crews;
    }
}
