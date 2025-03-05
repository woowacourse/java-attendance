package attendance.util;

import java.util.List;

public interface Reader {

    List<String> getContents(final String filePath);
}
