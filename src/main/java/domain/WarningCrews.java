package domain;

import java.util.Collections;
import java.util.List;

public class WarningCrews {
    private final List<WarningCrew> warningCrews;

    public WarningCrews(List<WarningCrew> warningCrews) {
        this.warningCrews = warningCrews;
    }

    public List<WarningCrew> getWarningCrews() {
        return Collections.unmodifiableList(warningCrews);
    }
}
