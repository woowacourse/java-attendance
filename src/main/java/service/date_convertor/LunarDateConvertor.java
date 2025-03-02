package service.date_convertor;

import java.time.LocalDate;

public interface LunarDateConvertor {
    
    LocalDate convertSolarDateToLunarDate(LocalDate date);
}
