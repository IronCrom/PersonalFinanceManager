import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Map<String, String> categories = new HashMap<>();
        HashMap<LocalDate, HashMap<String, Long>> fullStat = new HashMap<>();
        File binFile = new File("data.bin");
        if (binFile.exists()) {
            try (FileInputStream fis = new FileInputStream(binFile);
                 ObjectInputStream ois = new ObjectInputStream(fis)
            ) {
                fullStat = (HashMap<LocalDate, HashMap<String, Long>>) ois.readObject();
            }
        }
        try (Scanner scanner = new Scanner(new FileInputStream("categories.tsv"))
        ) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\t");
                categories.put(parts[0], parts[1]);
            }
        }
        try (ServerSocket serverSocket = new ServerSocket(8989)) {
            System.out.println("Сервер запущен!");

            while (true) {
                try (Socket client = serverSocket.accept();// ждем подключения
                     PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                     FileOutputStream fos = new FileOutputStream("data.bin");
                     ObjectOutputStream oos = new ObjectOutputStream(fos)
                ) {
                    Byu byu = Byu.byuFromJson(in.readLine());

                    byu.setTitle(categories.getOrDefault(byu.getTitle(), "другое"));
                    HashMap<String, Long> statForDay = new HashMap<>();
                    if (fullStat.containsKey(byu.getDate())) {
                        statForDay = fullStat.get(byu.getDate());
                        if (statForDay.containsKey(byu.getTitle())) {
                            statForDay.put(byu.getTitle(), statForDay.get(byu.getTitle()) + byu.getSum());
                        } else {
                            statForDay.put(byu.getTitle(), byu.getSum());
                        }
                    } else {
                        statForDay.put(byu.getTitle(), byu.getSum());
                        fullStat.put(byu.getDate(), statForDay);
                    }
                    oos.writeObject(fullStat);
                    out.println(Stat.statToJson(fullStat));
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}
