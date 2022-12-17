import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Stat {
    Categories maxCategory;
    Categories maxYearCategory;
    Categories maxMonthCategory;
    Categories maxDayCategory;


    public Stat(Categories maxCategory, Categories maxYearCategory, Categories maxMonthCategory, Categories maxDayCategory) {
        this.maxCategory = maxCategory;
        this.maxYearCategory = maxYearCategory;
        this.maxMonthCategory = maxMonthCategory;
        this.maxDayCategory = maxDayCategory;
    }

    static String statToJson(HashMap<LocalDate, HashMap<String, Long>> fullStat, LocalDate date) {
        GsonBuilder builderGson = new GsonBuilder();
        Gson gson = builderGson.setPrettyPrinting().create();

        HashMap<LocalDate, HashMap<String, Long>> statForDay = fullStat.entrySet()
                .stream()
                .filter(entry -> entry.getKey().isEqual(date))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (prev, next) -> next, HashMap::new)
                );

        HashMap<LocalDate, HashMap<String, Long>> statForMonth = fullStat.entrySet()
                .stream()
                .filter(entry -> (entry.getKey().getYear() == date.getYear()) && (entry.getKey().getMonth() == date.getMonth()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (prev, next) -> next, HashMap::new)
                );

        HashMap<LocalDate, HashMap<String, Long>> statForYear = fullStat.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getYear() == date.getYear())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (prev, next) -> next, HashMap::new)
                );
        Categories maxDay = Categories.getMaxCategory(statForDay);
        Categories maxMonth = Categories.getMaxCategory(statForMonth);
        Categories maxYear = Categories.getMaxCategory(statForYear);
        Categories maxAll = Categories.getMaxCategory(fullStat);

        Stat maxCat = new Stat(maxAll, maxYear, maxMonth, maxDay);

        return gson.toJson(maxCat);
    }
}
