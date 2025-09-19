# Onboarding System

Ett Java-baserat konsolprogram för att hantera onboarding av anställda.  
Här kan du:
- Lägga till nya employees
- Tilldela devices (laptop, telefon, headset etc.)
- Hantera kontrakt (signering, status)
- Markera onboarding som klar först när kontrakt är signerat och minst en device är tilldelad
- Se översikter över alla employees, devices och kontrakt

## Funktioner
- Söka på namn (även del av namn)
- Unik auto-genererad work email (ex: förnamn.efternamn@company.com)
- Devices och kontrakt kopplas till respektive employee
- Enkel meny med undermenyer för administration

## Struktur
src/main/java/
├── OnboardingSystem.java
├── Employee.java
├── EmployeeRegistry.java
├── Device.java
├── Contract.java
└── InputHandler.java

## 1. Klona projektet
```bash
git clone git@github.com:SaraGyllentid/Onboarding.git
cd Onboarding

mkdir -p out
javac -d out src/*.java

java -cp out Main

---