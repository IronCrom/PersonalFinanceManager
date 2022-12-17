

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Categories {
    String category;
    Long sum;

    public Categories(String category, Long sum) {
        this.category = category;
        this.sum = sum;
    }

    static Categories getMaxCategory(HashMap<LocalDate, HashMap<String, Long>> fullStat) {
        HashMap<String, Long> statForAll = new HashMap<>();
        for (HashMap<String, Long> value : fullStat.values()) {
            for (Map.Entry<String, Long> cat : value.entrySet()) {
                statForAll.merge(cat.getKey(), cat.getValue(), Long::sum);
            }
        }
        var maxEntry = Collections.max(statForAll.entrySet(), Map.Entry.comparingByValue()).getKey();
        return new Categories(maxEntry, statForAll.get(maxEntry));
    }
}
