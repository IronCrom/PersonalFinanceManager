import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Byu {
    String title;
    LocalDate date;
    Long sum;

    public Byu() {
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getSum() {
        return sum;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    static Byu byuFromJson(String jsonString) {
        GsonBuilder builderGson = new GsonBuilder();
        builderGson.registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context) ->
                LocalDate.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        Gson gson = builderGson.create();
        new Byu();
        return gson.fromJson(jsonString, Byu.class);
    }
}
