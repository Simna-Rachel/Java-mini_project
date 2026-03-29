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
