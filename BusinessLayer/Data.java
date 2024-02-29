import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Data implements Serializable{
    private static DateTimeFormatter dataFormatoEuropeu = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static DateTimeFormatter dataFormatoAmericano = DateTimeFormatter.ofPattern("yyyy-MM-dd");
   
    public static LocalDate StringAmericano_toDate(String dateString) throws DateTimeException{
        return LocalDate.parse(dateString,dataFormatoAmericano);
    }

    public static LocalDate StringEuropeia_toDate(String dateString) throws DateTimeException{
        return LocalDate.parse(dateString,dataFormatoEuropeu);
    }

    public static String DatetoString (LocalDate date){
        return date.format(dataFormatoEuropeu);
    }
}
