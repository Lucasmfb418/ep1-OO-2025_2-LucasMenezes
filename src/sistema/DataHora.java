package sistema;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataHora {
    private static final String FORMATO_PADRAO = "dd-MM-yyyy";

    public static LocalDateTime parse(String dataHoraStr) {
        return LocalDateTime.parse(dataHoraStr, DateTimeFormatter.ofPattern(FORMATO_PADRAO));
    }

    public static String format(LocalDateTime dataHora) {
        return dataHora.format(DateTimeFormatter.ofPattern(FORMATO_PADRAO));
    }
}

