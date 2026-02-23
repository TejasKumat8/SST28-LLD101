# SST28-LLD101 - SOLID Refactoring Practice

This repository contains the completion of 6 refactoring exercises focused on the SOLID principles. 
Initially, the code for the exercises intentionally violated these principles, and they have been meticulously refactored following object-oriented best practices to maintain correct functionality while improving design.

## Repository Structure
The repository is structured as follows:

- `SOLID/Ex1/` - **Single Responsibility Principle (SRP):** Refactored an onboarding service so parsing, validation, and printing are extracted into separated context classes (`RawInputParser`, `StudentValidator`, `ConfirmationPrinter`), alongside a purely decoupled `StudentRepository` interface.
- `SOLID/Ex2/` - **Single Responsibility Principle (SRP):** Refactored a cafeteria checkout system to separate computations `BillingCalculator` and output formatting into an `InvoiceFormatter`, leaving the orchestrator clean.
- `SOLID/Ex3/` - **Open/Closed Principle (OCP):** Resolved long conditionals inside evaluating engine by creating a centralized `EligibilityRule` abstraction. We implemented 4 rules correctly which made our engine strictly closed for modification and effortlessly open for extensions.
- `SOLID/Ex4/` - **Open/Closed Principle (OCP):** Created an easily extendable architecture by extracting fixed-fee Room switches and Addons into a clean `PricingComponent` interface (`RoomPricing`, `AddOnPricing`).
- `SOLID/Ex5/` - **Liskov Substitution Principle (LSP):** Ensured complete subclass substitutability for file exporters by strictly documenting class constraints, providing safe uniform handling for edge cases without enforcing tightened preconditions.
- `SOLID/Ex6/` - **Liskov Substitution Principle (LSP):** Refactored notification senders to halt abrupt runtime errors natively by replacing unhandled constraint-tightening failures with graceful execution and extensive audit logging logs mapping strictly back to the core notification contract.

## Prerequisites and Local Run Guidelines
The exercises require only standard Java features.
- Java Development Kit (JDK) 17 or higher
- All scripts are run directly using `javac` and `java`. No external dependencies, libraries, Maven, or Gradle.

### Running any refactored Exercise
From any `src` folder (e.g., `SOLID/Ex1/src/`):

```bash
javac *.java
java Main
```
