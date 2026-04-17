import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javax.swing.*;

public class FileHandler {
    private static final String FILE_PATH = "items.txt";
    private static final String DELIMITER = ";";

    public static boolean saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(FILE_PATH), StandardCharsets.UTF_8))) {

            writer.write("inum;time;jnum");
            writer.newLine();

            for (Product product : ContexData.products) {
                writer.write(String.format("%d%s%.5f%s%.5f",
                        product.getInum(),
                        DELIMITER,
                        product.getTime(),
                        DELIMITER,
                        product.getJnum()));
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            ContexData.products.clear();
            String line;
            boolean headerSkipped = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (!headerSkipped && (line.startsWith("id;") || line.startsWith("inum;"))) {
                    headerSkipped = true;
                    continue;
                }

                try {
                    Product product = parseCsvLine(line);
                    if (product != null) {
                        ContexData.products.add(product);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Пропущена некорректная строка: " + line);
                }
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static Product parseCsvLine(String line) {
        String[] parts = line.split(DELIMITER, 3);
        if (parts.length != 3) return null;

        int inum = Integer.parseInt(parts[0].trim());
        double time = Double.parseDouble(parts[1].trim().replace(',', '.'));
        double jnum = Double.parseDouble(parts[2].trim().replace(',', '.'));

        return new Product(inum, time, jnum);
    }

}
