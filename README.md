# Carbon Footprint Calculator

A Java GUI application that helps users calculate their monthly carbon emissions based on lifestyle choices and provides personalized recommendations for reducing environmental impact.

## 🌍 What It Does

The Carbon Footprint Calculator analyzes your daily activities and estimates how much CO2 you generate monthly. Based on your lifestyle category, it provides:

- **Personalized Calculations** - Based on 12 lifestyle categories (Student, Professional, Business Traveler, etc.)
- **CO2 Breakdown** - See emissions from car travel, electricity, food, and flights
- **Smart Suggestions** - Get actionable recommendations tailored to your current carbon footprint
- **Data Storage** - Results saved in MySQL for tracking progress over time

## ✨ Key Features

- **12 Lifestyle Categories** - Student, Homemaker, Professional, Business Traveler, Remote Worker, Industrial Worker, Government Official, Retired, Medical Professional, Tech Employee, Airline Staff, Environmental Activist
- **Customizable Inputs** - Start with suggested values or enter your own
- **Real-time Calculations** - Instant CO2 emission results
- **Eco-Friendly Feedback** - Get encouragement and specific actions to reduce your footprint
- **Database Integration** - MySQL stores your calculation history

## 🛠️ Technologies Used

- **Language**: Java
- **GUI**: Java Swing (Desktop Application)
- **Database**: MySQL
- **Tools**: VS Code with Copilot assistance, XAMPP for local MySQL
- **Connector**: MySQL Connector/J 9.5.0

## 🚀 How to Run

### Prerequisites
- Java 8 or higher
- MySQL (via XAMPP or standalone)
- MySQL Connector JAR (included in project)

### Steps

1. **Start MySQL**
   - If using XAMPP: Open XAMPP Control Panel and click "Start" next to MySQL
   - Ensure your MySQL connection is active

2. **Run the Application**
   - Open project in VS Code or your Java IDE
   - Compile: `javac *.java`
   - Run: `java Eco` (or `java CarbonCalculatorApp` depending on entry point)

3. **Use the Application**
   - Select your lifestyle category (1-12)
   - Review suggested values or customize them
   - View your monthly carbon footprint
   - Read personalized recommendations

## 📊 Example Output
```
--- Monthly Carbon Emissions (in kg CO2) ---
Car Travel:         156.24 kg
Electricity Use:    42.60 kg
Meat-based Meals:   86.45 kg
Flights:            20.83 kg
--------------------------------------------
Total Carbon Footprint: 306.12 kg CO2

🌱 Excellent! Your monthly footprint is very low.
🌟 You're leading by example. Keep up your amazing eco-conscious lifestyle!
💡 Tip: Share your sustainability practices with friends and family to inspire change.
```

## 📈 Emission Categories

The calculator tracks:
- **Car Travel** - Weekly kilometers driven
- **Electricity** - Monthly kWh consumption
- **Diet** - Meat-based meals per week
- **Air Travel** - Flights per year

## 💡 Lifestyle Categories Included

1. **Student** - Lower emissions baseline
2. **Homemaker** - Home-focused activities
3. **Working Professional** - Higher commute emissions
4. **Business Traveler** - Frequent flight emissions
5. **Freelancer/Remote** - Low commute, variable energy
6. **Industrial Worker** - Higher energy consumption
7. **Government Official** - High travel requirements
8. **Retired/Senior** - Lower activity baseline
9. **Medical Professional** - Variable schedule, moderate travel
10. **Tech Employee** - Tech industry standards
11. **Airline Staff** - Highest flight emissions
12. **Environmental Activist** - Minimal emissions

## 🎨 Features Overview

### Interactive Lifestyle Selection
Users can choose from 12 predefined lifestyle categories or enter custom values:
- Each category has pre-calculated default values based on typical activities
- Users can modify these values for their specific situation
- Real-time calculations show instant results

### Comprehensive Feedback System
The application provides personalized feedback based on carbon footprint levels:

**Excellent (< 250 kg)** - Encouragement and tips to maintain low footprint
**Good (250-400 kg)** - Recognition and suggestions for minor improvements
**Balanced (400-600 kg)** - Actionable steps to reduce emissions
**High (600-800 kg)** - Specific recommendations for significant reductions
**Very High (800-1000 kg)** - Urgent action items and lifestyle changes
**Extreme (> 1000 kg)** - Comprehensive transformation suggestions

### Database Integration
- Stores calculation history in MySQL
- Tracks user progress over time
- Allows comparison of different lifestyle choices

## 🎯 How It Calculates Emissions

The calculator uses these emission factors:
- **Car Travel**: 0.192 kg CO2 per km
- **Electricity**: 0.142 kg CO2 per kWh
- **Meat Meals**: 2.5 kg CO2 per meal
- **Flights**: 250 kg CO2 per flight

Weekly values are converted to monthly (multiplied by ~4.33) for accurate monthly reporting.

## 💻 Project Structure
```
JAVA MINIPROJECT/
├── Eco.java (Main entry point)
├── CarbonCalculatorApp.java
├── EmissionInput.java (Data class)
├── EmissionFactors.java (Constants)
├── EmissionCalculator.java (Calculation logic)
├── EmissionResult.java (Result handling)
├── Ecometer.java
├── mysql-connector-j-9.5.0/ (MySQL driver)
├── home.html
├── style.css
└── README.md
```

## 🔄 How to Use Step by Step

1. **Run the Program**
```bash
   java Eco
```

2. **Select Lifestyle Category**
   - Choose from 1-12 options displayed
   - Each category has typical values pre-filled

3. **Review or Customize Values**
   - See suggested emissions estimates
   - Type "yes" to customize, "no" to continue

4. **View Results**
   - See breakdown of emissions by category
   - Get personalized recommendations
   - Understand your carbon footprint

5. **Take Action**
   - Follow the suggestions provided
   - Track your progress over time
   - Make lifestyle adjustments

## 📊 Sample Scenarios

### Scenario 1: Student
```
Car travel: 10 km/week
Electricity: 120 kWh/month
Meat meals: 4/week
Flights: 1/year

Result: ~150 kg CO2/month (Excellent!)
```

### Scenario 2: Business Traveler
```
Car travel: 150 km/week
Electricity: 400 kWh/month
Meat meals: 10/week
Flights: 25/year

Result: ~850 kg CO2/month (Very High - Action Needed!)
```

### Scenario 3: Environmental Activist
```
Car travel: 5 km/week
Electricity: 150 kWh/month
Meat meals: 0/week
Flights: 0/year

Result: ~45 kg CO2/month (Extreme Excellence!)
```

## 🌱 Tips to Reduce Your Carbon Footprint

### Transportation
- Use public transport or carpool
- Walk or bike for short distances
- Consider an electric vehicle
- Combine errands into fewer trips

### Energy
- Use energy-efficient appliances
- Switch to LED bulbs
- Unplug devices when not in use
- Improve home insulation

### Food
- Reduce meat consumption
- Buy local and seasonal produce
- Minimize food waste
- Choose sustainable sources

### Travel
- Avoid unnecessary flights
- Use trains instead of planes when possible
- Combine trips when traveling
- Consider virtual meetings

## 🎯 Future Enhancements

- [ ] GUI improvements with better visualizations
- [ ] Historical data tracking and trend analysis
- [ ] Carbon offset recommendations
- [ ] Multi-user support with accounts
- [ ] Export reports to PDF format
- [ ] Web-based version for broader access
- [ ] Mobile app for on-the-go tracking
- [ ] Integration with fitness apps for travel data
- [ ] Community leaderboard for eco-challenges
- [ ] Real-time carbon price tracking

## 📚 Learning Outcomes

This project helped me learn:
- Object-oriented Java programming principles
- Database connectivity with MySQL
- User input handling and validation techniques
- Complex calculations and business logic
- Clean code practices with multiple classes
- How environmental impact scales with lifestyle choices
- Practical application development
- GUI development with Java Swing

## 🤝 Contributing

This is a personal learning project, but feel free to fork and modify for your own learning! Contributions, suggestions, and improvements are welcome.

## 📄 License

This project is open source and available for educational purposes. Feel free to use, modify, and distribute for learning.

## 🙋 About Me

I'm Simna Rachel, a Java developer passionate about building applications that make a positive environmental impact. This Carbon Footprint Calculator is one of my learning projects where I combined Java programming skills with environmental awareness.

### Contact & Connect
- **GitHub**: [Simna-Rachel](https://github.com/Simna-Rachel)
- **Email**: simnaracheljacob@gmail.com

## 🌍 Environmental Note

Every small change counts! Even if you reduce your carbon footprint by just 10%, you're making a positive impact on our planet. This calculator is a tool to help you understand your environmental impact and make informed choices.

## 📞 Support

If you have questions or encounter issues:
1. Check the Prerequisites section
2. Ensure MySQL is running
3. Verify all Java files are compiled
4. Check MySQL connection credentials in the code
5. Make sure all dependencies are in the project folder

---

**Made with 🐱 by Simna Rachel**

*Turning Code into Positive Environmental Impact*

Last Updated: 2026
