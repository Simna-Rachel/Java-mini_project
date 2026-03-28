import java.util.Scanner;

// Data class to hold the user's input
class EmissionInput {
    private double weeklyCarKm;
    private double monthlyElectricityKWh;
    private int meatMealsPerWeek;
    private int flightsPerYear;

    EmissionInput(double weeklyCarKm, double monthlyElectricityKWh, int meatMealsPerWeek, int flightsPerYear) {
        this.weeklyCarKm = weeklyCarKm;
        this.monthlyElectricityKWh = monthlyElectricityKWh;
        this.meatMealsPerWeek = meatMealsPerWeek;
        this.flightsPerYear = flightsPerYear;
    }

    public double getWeeklyCarKm() { return weeklyCarKm; }
    public double getMonthlyElectricityKWh() { return monthlyElectricityKWh; }
    public int getMeatMealsPerWeek() { return meatMealsPerWeek; }
    public int getFlightsPerYear() { return flightsPerYear; }
}

// Emission Factors
class EmissionFactors {
    static final double CAR_EMISSION_PER_KM = 0.192;
    static final double ELECTRICITY_EMISSION_PER_KWH = 0.142;
    static final double MEAT_MEAL_EMISSION = 2.5;
    static final double FLIGHT_EMISSION = 250.0;
}

// Calculator
class EmissionCalculator {
    static double calculateCarEmission(double km) {
        return km * EmissionFactors.CAR_EMISSION_PER_KM;
    }

    static double calculateElectricityEmission(double kwh) {
        return kwh * EmissionFactors.ELECTRICITY_EMISSION_PER_KWH;
    }

    static double calculateMeatEmission(double meals) {
        return meals * EmissionFactors.MEAT_MEAL_EMISSION;
    }

    static double calculateFlightEmission(double flights) {
        return flights * EmissionFactors.FLIGHT_EMISSION;
    }
}

// Result Printer (Monthly Version)
class EmissionResult {
    private EmissionInput input;
    private double carEmission;
    private double electricityEmission;
    private double meatEmission;
    private double flightEmission;
    private double totalEmission;

    EmissionResult(EmissionInput input) {
        this.input = input;

        // Convert weekly data to monthly (approx. 4.33 weeks in a month)
        this.carEmission = EmissionCalculator.calculateCarEmission(input.getWeeklyCarKm() * 4.33);
        this.electricityEmission = EmissionCalculator.calculateElectricityEmission(input.getMonthlyElectricityKWh());
        this.meatEmission = EmissionCalculator.calculateMeatEmission(input.getMeatMealsPerWeek() * 4.33);
        this.flightEmission = EmissionCalculator.calculateFlightEmission(input.getFlightsPerYear() / 12.0);
        this.totalEmission = carEmission + electricityEmission + meatEmission + flightEmission;
    }

    void printResult() {
        System.out.println("\n--- Monthly Carbon Emissions (in kg CO2) ---");
        System.out.printf("Car Travel:         %.2f kg\n", carEmission);
        System.out.printf("Electricity Use:    %.2f kg\n", electricityEmission);
        System.out.printf("Meat-based Meals:   %.2f kg\n", meatEmission);
        System.out.printf("Flights:            %.2f kg\n", flightEmission);
        System.out.println("--------------------------------------------");
        System.out.printf("Total Carbon Footprint: %.2f kg CO2\n", totalEmission);

        // Monthly Feedback Thresholds
        // Monthly Feedback Thresholds
        if (totalEmission < 250) {
            System.out.println("\n🌱 Excellent! Your monthly footprint is very low.");
            System.out.println("   🌟 You're leading by example. Keep up your amazing eco-conscious lifestyle!");
            System.out.println("   💡 Tip: Share your sustainability practices with friends and family to inspire change.");
        } else if (totalEmission < 400) {
            System.out.println("\n👍 Good job! Your monthly footprint is low.");
            System.out.println("   ✅ You're doing better than most people!");
            System.out.println("   💪 Keep making mindful choices, and consider exploring even greener options.");
        } else if (totalEmission < 600) {
            System.out.println("\n⚖ Balanced. You’re doing okay, but small improvements can help.");
            System.out.println("   🌿 You're on the right track. Try small tweaks like energy-saving bulbs or reducing short car trips.");
        } else if (totalEmission < 800) {
            System.out.println("\n⚠ High footprint. Consider making the following changes:");
            System.out.println("   - Reduce unnecessary car trips or carpool");
            System.out.println("   - Unplug electronics when not in use");
            System.out.println("   - Use energy-efficient appliances");
            System.out.println("   - Try reducing red meat consumption");
            System.out.println("   - Combine errands into one trip to save fuel");
        } else if (totalEmission < 1000) {
            System.out.println("\n🚨 Very high footprint! Significant changes are needed:");
            System.out.println("   - Consider switching to an electric vehicle or using public transport");
            System.out.println("   - Install solar panels or choose renewable electricity providers");
            System.out.println("   - Reduce meat consumption, especially red meat");
            System.out.println("   - Avoid short flights and use trains or buses when possible");
            System.out.println("   - Audit your home for energy leaks and insulate properly");
        } else {
            System.out.println("\n🔥 Extreme footprint! Urgent action needed!");
            System.out.println("   🌍 You may be among the top carbon emitters globally.");
            System.out.println("   Suggestions:");
            System.out.println("   - Stop or severely reduce flying (use virtual meetings or trains)");
            System.out.println("   - Transition to a fully plant-based diet");
            System.out.println("   - Sell or replace fuel-inefficient vehicles");
            System.out.println("   - Conduct a full home energy audit and retrofit");
            System.out.println("   - Offset your emissions by supporting reforestation or clean energy projects");
            System.out.println("   - Consider lifestyle downsizing if feasible");
        }

    }
}

// Main class
public class Eco {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Carbon Footprint Calculator ===\n");

        // Show lifestyle options
        System.out.println("Select your lifestyle category:");
        System.out.println("1. Student");
        System.out.println("2. Homemaker");
        System.out.println("3. Working Professional");
        System.out.println("4. Business Traveler");
        System.out.println("5. Freelancer / Remote Worker");
        System.out.println("6. Industrial Worker");
        System.out.println("7. Politician / Government Representative");
        System.out.println("8. Retired / Senior");
        System.out.println("9. Medical Professional");
        System.out.println("10. Tech Employee");
        System.out.println("11. Airline Staff (Pilot, Crew)");
        System.out.println("12. Environmental Activist");

        System.out.print("\nEnter choice (1-12): ");
        int category = scanner.nextInt();

        // Default values based on category
        double weeklyCarKm = 0;
        double monthlyElectricityKWh = 0;
        int meatMealsPerWeek = 0;
        int flightsPerYear = 0;

        switch (category) {
            case 1 -> { weeklyCarKm = 10; monthlyElectricityKWh = 120; meatMealsPerWeek = 4; flightsPerYear = 1; }
            case 2 -> { weeklyCarKm = 30; monthlyElectricityKWh = 250; meatMealsPerWeek = 7; flightsPerYear = 1; }
            case 3 -> { weeklyCarKm = 80; monthlyElectricityKWh = 350; meatMealsPerWeek = 7; flightsPerYear = 5; }
            case 4 -> { weeklyCarKm = 150; monthlyElectricityKWh = 400; meatMealsPerWeek = 10; flightsPerYear = 25; }
            case 5 -> { weeklyCarKm = 20; monthlyElectricityKWh = 300; meatMealsPerWeek = 6; flightsPerYear = 2; }
            case 6 -> { weeklyCarKm = 100; monthlyElectricityKWh = 450; meatMealsPerWeek = 10; flightsPerYear = 3; }
            case 7 -> { weeklyCarKm = 200; monthlyElectricityKWh = 500; meatMealsPerWeek = 10; flightsPerYear = 40; }
            case 8 -> { weeklyCarKm = 15; monthlyElectricityKWh = 200; meatMealsPerWeek = 5; flightsPerYear = 0; }
            case 9 -> { weeklyCarKm = 60; monthlyElectricityKWh = 300; meatMealsPerWeek = 8; flightsPerYear = 2; }
            case 10 -> { weeklyCarKm = 70; monthlyElectricityKWh = 350; meatMealsPerWeek = 6; flightsPerYear = 5; }
            case 11 -> { weeklyCarKm = 30; monthlyElectricityKWh = 250; meatMealsPerWeek = 8; flightsPerYear = 100; }
            case 12 -> { weeklyCarKm = 5; monthlyElectricityKWh = 150; meatMealsPerWeek = 0; flightsPerYear = 0; }
            default -> {
                System.out.println("Invalid category. Using default (Student).");
                weeklyCarKm = 10; monthlyElectricityKWh = 120; meatMealsPerWeek = 4; flightsPerYear = 1;
            }
        }

        // Show suggested values
        System.out.println("\nSuggested values based on your lifestyle:");
        System.out.println("Car travel per week: " + weeklyCarKm + " km");
        System.out.println("Monthly electricity usage: " + monthlyElectricityKWh + " kWh");
        System.out.println("Meat meals per week: " + meatMealsPerWeek);
        System.out.println("Flights per year: " + flightsPerYear);

        System.out.print("\nWould you like to customize these values? (yes/no): ");
        String customize = scanner.next().toLowerCase();

        if (customize.equals("yes")) {
            System.out.print("Enter your average car travel per week (in km): ");
            weeklyCarKm = scanner.nextDouble();

            System.out.print("Enter your average monthly electricity usage (in kWh): ");
            monthlyElectricityKWh = scanner.nextDouble();

            System.out.print("Enter the number of meat-based meals you eat per week: ");
            meatMealsPerWeek = scanner.nextInt();

            System.out.print("Enter the number of flights you take per year: ");
            flightsPerYear = scanner.nextInt();
        }

        // Final input object
        EmissionInput input = new EmissionInput(weeklyCarKm, monthlyElectricityKWh, meatMealsPerWeek, flightsPerYear);

        // Calculate and print result
        EmissionResult result = new EmissionResult(input);
        result.printResult();

        scanner.close();
    }
}
