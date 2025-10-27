import java.io.*;
import java.util.*;

public class SalesMenuApp {
    public static void main(String[] args) {
        String filePath = "EVE01Sales.txt"; // Path to your file
        List<String> headers = new ArrayList<>();

        // Step 1: Read header line to generate menu
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String headerLine = br.readLine(); // read first line
            if (headerLine == null) {
                System.err.println("File is empty!");
                return;
            }

            // Split by tab and store headers
            headers = Arrays.asList(headerLine.split("\t"));

            // Display dynamic menu
            System.out.println("=== Auto-Generated Menu from File Headers ===");
            for (int i = 0; i < headers.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, headers.get(i));
            }
            System.out.println("0. Exit");
            System.out.println("=============================================");

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        // Step 2: Load data and calculate sales summaries
        double totalSales = 0.0;
        Map<String, Double> employeeSales = new HashMap<>();
        Map<String, Double> productSales = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length < 6) continue;

                String repId = parts[2].trim();
                String product = parts[3].trim();
                int qty = Integer.parseInt(parts[4].trim());
                double unitPrice = Double.parseDouble(parts[5].trim());

                double saleAmount = qty * unitPrice;
                totalSales += saleAmount;

                employeeSales.put(repId, employeeSales.getOrDefault(repId, 0.0) + saleAmount);
                productSales.put(product, productSales.getOrDefault(product, 0.0) + saleAmount);
            }
        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
            return;
        }

        // Step 3: Simple interactive menu (optional)
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n=== Choose an Option ===");
            System.out.println("1. Show Total Sales");
            System.out.println("2. Show Employee-wise Sales");
            System.out.println("3. Show Product-wise Sales");
            System.out.println("4. Show Auto-Generated Header Menu Again");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\nTotal Sales Amount: " + totalSales);
                    break;
                case 2:
                    System.out.println("\nEmployee-wise Sales:");
                    employeeSales.forEach((k, v) -> System.out.println(k + " -> " + v));
                    break;
                case 3:
                    System.out.println("\nProduct-wise Sales:");
                    productSales.forEach((k, v) -> System.out.println(k + " -> " + v));
                    break;
                case 4:
                    System.out.println("\n=== File Header Menu ===");
                    for (int i = 0; i < headers.size(); i++) {
                        System.out.printf("%d. %s%n", i + 1, headers.get(i));
                    }
                    System.out.println("0. Exit");
                    break;
                case 0:
                    System.out.println("Exiting... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        } while (choice != 0);

        sc.close();
    }
}
