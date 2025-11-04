🧮 Actuary App

Actuary App is a simple object-oriented Java project that models how actuaries calculate insurance coverage and premium projections.
The project demonstrates core Object-Oriented Design (OOD) principles and how actuarial formulas can be implemented securely and modularly in code.

🧱 Object-Oriented Design Overview

This project applies all four pillars of OOD — Encapsulation, Abstraction, Inheritance, and Polymorphism — along with the Single Responsibility Principle for maintainable design.

1. Policy Class

Core blueprint for all policy types.

Defines shared variables: policyType, basePremium, coverageAmount, and termYears.

Serves as an abstract parent class that enforces a calculatePremium() method for subclasses.

2. AssumptionSet Class

Contains actuarial assumption variables: mortalityRate, expenseLoad, and interestRate.

Uses encapsulation — all fields are private and validated on creation to ensure data integrity.

3. ActuarialCalculator Class

The math engine of the application.

Performs key actuarial calculations such as expected claims, present value of benefits, and net premiums.

Uses abstraction — the internal formula logic is hidden behind clear, descriptive method names.

4. SSOService Class

Handles security and authentication.

Simulates Java token validation for protected access to the system.

Demonstrates abstraction, hiding authentication details from other parts of the system.

5. ActuarialApp Class

Main controller that runs the program.

Gathers user input, authenticates access, builds objects, and calls calculator methods.

Combines all classes through composition, while respecting Single Responsibility across modules.

🧩 Object-Oriented Design Principles Used
Principle	Description	Example in Project
Encapsulation	Keeps Policy and AssumptionSet data private	Private fields with getters
Abstraction	Hides complex math and auth logic	ActuarialCalculator & SSOService
Inheritance	Reuses core logic across policy types	LifePolicy extends Policy
Polymorphism	Same method behaves differently per object	calculatePremium() overridden in LifePolicy
Single Responsibility	Each class handles one concern	Calculator = math, SSO = auth, App = flow
💡 How It Works

The user authenticates with a valid token.

Enters policy details and actuarial assumptions.

The system creates objects for Policy, AssumptionSet, and Calculator.

Premium and projection values are calculated and displayed to the user.

⚙️ Technologies

Language: Java

Concepts: Object-Oriented Design, Encapsulation, Inheritance, Abstraction, Polymorphism

Security: Token-based SSO simulation

🧠 Example Output
=== Actuarial Projection (Life) ===

Enter SSO Token: ADMIN123
Enter Base Premium: 1200
Enter Coverage Amount: 500000
Enter Term (years): 20
Enter Mortality Rate: 0.002
Enter Expense Load: 0.10
Enter Interest Rate: 0.04

--- Results ---

Expected Claim: $1000.00
Gross/Loaded Premium: $1320.00
PV of Benefits: $13,604.57
Net Premium per Year: $680.23

🧾 Summary

This project demonstrates how actuarial systems can be structured using object-oriented design.
Each class models a real-world component of the actuarial process. From policies and assumptions to mathematical projections and secure access, resulting in a modular, extensible, and auditable software architecture.
