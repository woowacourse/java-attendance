package service.dateconvertor;

import java.time.LocalDate;

public interface LunarDateConvertor {
    
    LocalDate convertSolarDateToLunarDate(LocalDate date);
}
