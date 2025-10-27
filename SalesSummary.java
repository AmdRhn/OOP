import java.io.*;
import java.util.*;

public class SalesSummary {
    public static void main(String[] args) {
        String filePath = "EVE01Sales.txt"; // Path to your file

        double totalSales = 0.0;
        Map<String, Double> employeeSales = new HashMap<>();
        Map<String, Double> productSales = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip header line

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");

                if (parts.length < 6) continue; // Skip invalid rows

                String repId = parts[2].trim();
                String product = parts[3].trim();
                int qty = Integer.parseInt(parts[4].trim());
                double unitPrice = Double.parseDouble(parts[5].trim());

                double saleAmount = qty * unitPrice;
                totalSales += saleAmount;

                // Employee-wise total
                employeeSales.put(repId, employeeSales.getOrDefault(repId, 0.0) + saleAmount);

                // Product-wise total
                productSales.put(product, productSales.getOrDefault(product, 0.0) + saleAmount);
            }

            // Print results
            System.out.println("=== Sales Summary ===");
            System.out.println("Total Sales Amount: " + totalSales);

            System.out.println("\nEmployee-wise Sales Totals:");
            for (Map.Entry<String, Double> entry : employeeSales.entrySet()) {
                System.out.println(entry.getKey() + " -> " + entry.getValue());
            }

            System.out.println("\nProduct-wise Sales Totals:");
            for (Map.Entry<String, Double> entry : productSales.entrySet()) {
                System.out.println(entry.getKey() + " -> " + entry.getValue());
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format in file: " + e.getMessage());
        }
    }
}
