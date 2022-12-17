import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.util.HashMap;

public class Stat {
    Categories maxCategory;

    public Stat(Categories maxCategory) {
        this.maxCategory = maxCategory;
    }

    static String statToJson(HashMap<LocalDate, HashMap<String, Long>> fullStat) {
        GsonBuilder builderGson = new GsonBuilder();
        Gson gson = builderGson.setPrettyPrinting().create();

        Categories maxAll = Categories.getMaxCategory(fullStat);

        Stat maxCat = new Stat(maxAll);

        return gson.toJson(maxCat);
    }
}
