package attendance.view.converter;

import java.util.HashMap;
import java.util.Map;

import attendance.domain.SanctionLevel;
import attendance.interfaces.Converter;

public class LevelTextConverter implements Converter<SanctionLevel> {
    private static final Map<SanctionLevel, String> SANCTION_LEVEL_TEXT = new HashMap<>();
    private static final String CANT_CONVERTING = "변환할 수 없는 값입니다: ";

    static {
        SANCTION_LEVEL_TEXT.put(SanctionLevel.DISMISS, "제적");
        SANCTION_LEVEL_TEXT.put(SanctionLevel.NEED_MEETING, "면담");
        SANCTION_LEVEL_TEXT.put(SanctionLevel.WARNING, "경고");
        SANCTION_LEVEL_TEXT.put(SanctionLevel.NONE, "");
    }

    @Override
    public String convert(SanctionLevel level) {
        var converted = SANCTION_LEVEL_TEXT.get(level);
        if (converted == null) {
            throw new NullPointerException(CANT_CONVERTING + level);
        }
        return converted;
    }
}
