
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;



public class CategoriesTest {

static HashMap<LocalDate, HashMap<String, Long>> fullStat = new HashMap<>();

    @BeforeAll
    public static void beforeAll(){
        List<String> categories = Arrays.asList("еда", "финансы", "быт", "другое", "одежда", "связь");

        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            LocalDate date = LocalDate.ofEpochDay(ThreadLocalRandom.current()
                    .nextLong(LocalDate.of(2021,1,1).toEpochDay(), LocalDate.now().toEpochDay()));
            HashMap<String, Long> category = new HashMap<>();
            for (int j = 0; j < 10; j++) {
                category.put(categories.get(random.nextInt(categories.size())), (long) random.nextInt(500));
            }
            fullStat.put(date, category);
        }
    }


    @Test
     public void getMaxCategory() {
        System.out.println(Categories.getMaxCategory(fullStat).category
                + " " + Categories.getMaxCategory(fullStat).sum);
        System.out.println();
    }

    @Test
    public void getMaxCategoryAssertions(){
        HashMap<LocalDate, HashMap<String, Long>> stat = new HashMap<>();
        HashMap<String, Long> category = new HashMap<>();
        category.put("еда", 200L);
        category.put("быт", 300L);
        stat.put(LocalDate.ofEpochDay(2022-8-5), category);

        Assertions.assertEquals("быт", Categories.getMaxCategory(stat).category);
        System.out.println("Test O'k");
        Assertions.assertEquals(300, Categories.getMaxCategory(stat).sum);
        System.out.println("Test O'k \n");
    }
}