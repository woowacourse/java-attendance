import domain.DateProvider;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;

public class BaseAttendanceTest {

    protected static final LocalDate DEC_13 = LocalDate.of(2024, 12, 13);
    protected static final LocalDate DEC_14 = LocalDate.of(2024, 12, 14);

    protected DateProvider dateProviderDec13;
    protected DateProvider dateProviderDec14;

    @BeforeEach
    void setUp() {
        dateProviderDec13 = () -> DEC_13;
        dateProviderDec14 = () -> DEC_14;
    }
}
