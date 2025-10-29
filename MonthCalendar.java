import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Scanner;

public class MonthCalendar {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Taking input from user
        System.out.print("Enter year: ");
        int year = input.nextInt();

        System.out.print("Enter month (1-12): ");
        int month = input.nextInt();

        // Create LocalDate object for the first day of the given month and year
        LocalDate firstDay = LocalDate.of(year, month, 1);

        // Get the name of the month
        String monthName = firstDay.getMonth().toString().toLowerCase();
        monthName = monthName.substring(0, 1).toUpperCase() + monthName.substring(1);

        // Print header
        System.out.println("\nCalendar for the month of " + monthName + ", " + year);
        System.out.println("Su  Mo  Tu  We  Th  Fr  Sa");

        // Find out the day of week of the 1st day of the month
        int dayOfWeek = firstDay.getDayOfWeek().getValue(); // 1=Monday ... 7=Sunday

        // Adjust so that Sunday = 0, Monday = 1, ...
        int startPosition = (dayOfWeek % 7);

        // Print leading spaces for the first week
        for (int i = 0; i < startPosition; i++) {
            System.out.print("    ");
        }

        // Print days of the month
        int daysInMonth = firstDay.lengthOfMonth();

        for (int day = 1; day <= daysInMonth; day++) {
            System.out.printf("%2d  ", day);

            // Move to new line after Saturday
            if ((day + startPosition) % 7 == 0) {
                System.out.println();
            }
        }

        System.out.println(); // Final newline
    }
}
