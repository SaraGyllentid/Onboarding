
import java.util.ArrayList;
import java.util.List;


// Här hanteras:
// Meny
// Användainput
// Skapa och lista employees
// Slutför onboardingen

//TODO:ändra befintlig användare
//TODO:
//TODO: Kunna lägga till devices till användare, lista devices, se vilka är utlånade och inte
public class OnboardingSystem {


    private final EmployeeRegistry registry;

    private final List<Device> inventory;


    public OnboardingSystem() {
        this.registry = new EmployeeRegistry();
        // ---------Sample data
        this.inventory = new ArrayList<>(); //tomma hållare för iinventory
        inventory.add(new Device("Laptop", "Dell XPS13"));
        inventory.add(new Device("Laptop", "MacBook Pro 14”"));
        inventory.add(new Device("Mobile phone", "iPhone 13"));
        inventory.add(new Device("Mobile phone", "Samsung Galaxy S22"));
        inventory.add(new Device("External monitor", "Dell UltraSharp 27”"));
        inventory.add(new Device("Headset", "Jabra Evolve2 65"));

        Employee johanna = new Employee("Johanna", "Hanson", "johanna@example.com", "2025-09-01", "Sales Manager", false);
        johanna.setWorkEmail(registry.generateUniqueEmail(johanna.getFirstName(), johanna.getLastName(), "company.com"));
        registry.addEmployee(johanna);


        Employee andersS = new Employee("Anders", "Sjöberg", "anders@example.com", "2025-09-08", "Junior Sales Associate", false);
        andersS.setWorkEmail(registry.generateUniqueEmail(andersS.getFirstName(), andersS.getLastName(), "company.com"));
        registry.addEmployee(andersS);


        Employee my = new Employee("My", "Karlsson", "my@example.com", "2025-09-15", "Support Engineer", false);
        my.setWorkEmail(registry.generateUniqueEmail(my.getFirstName(), my.getLastName(), "company.com"));
        registry.addEmployee(my);


        Employee kalle = new Employee("Kalle", "Isaksson", "kalle@example.com", "2025-09-15", "Sales Associate", false);
        kalle.setWorkEmail(registry.generateUniqueEmail(kalle.getFirstName(), kalle.getLastName(), "company.com"));
        registry.addEmployee(kalle);


        Employee andersA = new Employee("Anders", "af Akker", "anders1@example.com", "2025-10-01", "Sales Associate", false);
        andersA.setWorkEmail(registry.generateUniqueEmail(andersA.getFirstName(), andersA.getLastName(), "company.com"));
        registry.addEmployee(andersA);

        Contract cJohanna = new Contract("Employment", johanna);
        cJohanna.sign();
        johanna.setContract(cJohanna);

        // Anders S: kontrakt finns men ej signerat
        Contract cAndersS = new Contract("Employment", andersS);
        andersS.setContract(cAndersS);

        // Kalle: signerat kontrakt
        Contract cKalle = new Contract("Employment", kalle);
        cKalle.sign();
        kalle.setContract(cKalle);

        Device dellXps = inventory.get(0);
        johanna.addDevice(dellXps);
        dellXps.assignTo(johanna);

        Device iphone = inventory.get(2);               // "Mobile phone - iPhone 13"
        kalle.addDevice(iphone);
        iphone.assignTo(kalle);

        johanna.setOnboardingDone(true);

    }

    //kör programmet
    public void run() {
        //körs till man väljer exit
        while (true) {
            printMenu();
            int choice = InputHandler.getInt("Select: ");

            switch (choice) {
                case 1:
                    //Skapa ny employee
                    Employee employee = createEmployee();
                    //Lägg till i registret
                    registry.addEmployee(employee);
                    System.out.printf("Employee created: %s %s (%s)\n",
                            employee.getFirstName(), employee.getLastName(), employee.getWorkEmail());
                    break;
                case 2:
                    //lista alla employees
                    registry.printEmployees();
                    break;
                case 3:
                    //Hantera onboarding-menu
                    manageEmployeeByName();
                    break;
                case 4:
                    deviceMenu();
                    break;
                case 5:
                    printAllContracts();
                    break;
                case 6:
                    System.out.println("Programmet stängs ner.");
                    return;
                default:
                    System.out.println("Invalid choice. Choose a number between 1 and 4");
                    break;

            }
        }
    }

    private void printMenu() {
        System.out.println("---------------------------------\n" + "---------Onboarding Menu:--------");
        System.out.println("1. Add employee");
        System.out.println("2. List employees");
        System.out.println("3. Manage employee");
        System.out.println("4. Devices");
        System.out.println("5. List contracts");
        System.out.println("6. Exit");

    }

    // Skapar employee utan devices
    private Employee createEmployee() {
        String firstName = InputHandler.getValidName("First name: ");
        String lastName  = InputHandler.getValidName("Last name: ");

        String email;
        while (true) {
            email = InputHandler.getValidEmail("Email: ");
            if (!registry.emailExists(email)) {
                break;
            }
            System.out.println("The email already exists: " + email);
        }



        String startDate = InputHandler.getValidDate("Start date (yyyy-mm-dd): ");
        String role = InputHandler.getNonEmptyString("Role: ");

        Employee emp = new Employee(firstName, lastName, email, startDate, role);
        String workEmail = registry.generateUniqueEmail(firstName, lastName, "company.com");
        emp.setWorkEmail(workEmail);
        System.out.printf("Generated work email: %s\n", emp.getWorkEmail());
        return emp;
    }


    //söka efter employee
    private void manageEmployeeByName() {
        String question = InputHandler.getNonEmptyString("Search for name: ");
        List<Employee> employees = registry.searchByName(question);
        //inga träffar
        if (employees.isEmpty()) {
            System.out.println("Employee not found.");
            return;
        }
        //en träff
        if (employees.size() == 1) {
            Employee oneEmployee = employees.get(0);
            employeeMenu(oneEmployee);
            return;
        }
        //fler träffar
        if (employees.size() > 1) {
            System.out.println("Multiple employees found: ");
            int index = 1;
            //visar listan men med index
            for (Employee employee : employees) {
                String status;
                if (employee.isOnboardingDone()) {
                    status = "Done";
                } else {
                    status = "Pending";
                }
                System.out.printf("%d. %s %s (%s) | Onboarding: %s\n", index, employee.getFirstName(), employee.getLastName(), employee.getWorkEmail(), status);
                index++;
            }

        }

        int pick = InputHandler.getInt("Select a number: ");
        if (pick == 0) {
            System.out.println("Canceled.");
            return;
        }
        if (pick > employees.size() || pick < 1) {
            System.out.println("Invalid input. Please try again.");
            return;
        }
        Employee chosen = employees.get(pick - 1);
        employeeMenu(chosen);
    }

    //undermeny anställd
    private void employeeMenu(Employee employee) {
        while (true) {
            if (employee.isOnboardingDone()) {
                System.out.printf("Onboarding for %s is already done.\n", employee.getFirstName());
                return;
            } else {
                System.out.printf("---------Manage employee: %s %s--------\n", employee.getFirstName(), employee.getLastName());

                if (employee.hasSignedContract()) {
                    System.out.println("Contract: Signed");
                } else {
                    System.out.println("Contract: Not signed");
                }

                if (employee.hasAtLeastOneDevice()) {
                    System.out.println("Devices: Assigned");
                } else {
                    System.out.println("Devices: None");
                }

                if (employee.isOnboardingDone()) {
                    System.out.println("Onboarding: Done");
                } else {
                    System.out.println("Onboarding: Pending");
                }

                System.out.println("1. Sign contract");
                System.out.println("2. Assign devices");
                System.out.println("3. Remove devices");
                System.out.println("4. Mark onboarding as done");
                System.out.println("5. Change employee details");
                System.out.println("6. Back to main menu");
                printEmployeeDevices(employee);
                int choice = InputHandler.getInt("Select: ");
                switch (choice) {
                    case 1:
                        if (employee.getContract() == null) {
                            Contract c = new Contract("Employment", employee);
                            c.sign();
                            employee.setContract(c);
                            System.out.printf("Contract signed for %s %s%n", employee.getFirstName(), employee.getLastName());
                        } else {
                            // kontrakt finns redan
                            if (employee.getContract().isSigned()) {
                                System.out.println("Contract is already signed.");
                            } else {
                                employee.getContract().sign();
                                System.out.printf("Contract signed for %s %s%n", employee.getFirstName(), employee.getLastName());
                            }
                        }
                        break;
                    case 2:

                        assignDevicesToEmployee(employee);
                        break;
                    case 3:
                        removeDevicesFromEmployee(employee);
                        break;
                    case 4:
                        if (!employee.hasSignedContract()) {
                            System.out.println("You must sign the contract before completing onboarding.");
                            break;
                        }
                        if (!employee.hasAtLeastOneDevice()) {
                            System.out.println("You must assign at least one device before completing onboarding.");
                            break;
                        }
                        if (!employee.isOnboardingDone()) {
                            employee.setOnboardingDone(true);
                            System.out.printf("Onboarding is marked as done for %s%n", employee.getFirstName());
                        } else {
                            System.out.println("Onboarding is already done.");
                        }
                        break;
                    case 5:
                        System.out.printf("Change details for %s", employee.getFirstName());
                        changeEmployeeDetails(employee);
                        break;
                    case 6:
                        System.out.println("Returning...");
                        return;
                    default:
                        System.out.println("Invalid choice. Choose a number between 1 and 6");
                        break;
                }
            }
        }

    }

    private void printEmployeeDevices(Employee employee) {
        System.out.printf("Devices for %s %s\n", employee.getFirstName(), employee.getLastName());
        if (employee.getDevices().isEmpty()) {
            System.out.println("None");
            return;
        }
        for (Device d : employee.getDevices()) {
            System.out.println(" - " + d.toString());
        }
    }

    private void assignDevicesToEmployee(Employee employee) {
        List<Integer> selectableIndexes = new ArrayList<>();
        int displayIndex = 1;
        System.out.println("Available devices: ");
        for (int i = 0; i < inventory.size(); i++) {
            Device d = inventory.get(i);
            if (!d.isLoaned()) {
                System.out.printf("%d. %s\n", displayIndex, d.toString());
                selectableIndexes.add(i);
                displayIndex++;
            }
        }
        if (selectableIndexes.isEmpty()) {
            System.out.println("No available devices.");
            return;
        }
        String raw = InputHandler.getNonEmptyString("Enter numbers separated by comma: (0 to cancel)");
        if (raw.equals("0")) {
            System.out.println("Canceled.");
            return;
        }
        String[] chosenDevices = raw.split(",");
        for (String device : chosenDevices) {
            String trimmed = device.trim();
            boolean isNumber = true;
            for (int i = 0; i < trimmed.length(); i++) {
                if (!Character.isDigit(trimmed.charAt(i))) {
                    isNumber = false;
                    break;
                }
            }
            if (!isNumber) {
                System.out.println("Invalid number: " + trimmed);
                continue;
            }
            int pickNumber = Integer.parseInt(trimmed);
            if (pickNumber < 1 || pickNumber > selectableIndexes.size()) {
                System.out.println("Invalid input:" + trimmed);
                continue;
            }
            int inventoryIndex = selectableIndexes.get(pickNumber - 1);
            Device chosenDevice = inventory.get(inventoryIndex);
            if (chosenDevice.isLoaned()) {
                System.out.printf("Device %s is loaned to %s", chosenDevice.toString(), chosenDevice.getBorrowerName());
            } else {
                employee.addDevice(chosenDevice);
                chosenDevice.assignTo(employee);
                System.out.printf("Assigned %s to %s", chosenDevice.toString(), chosenDevice.getBorrowerName());
            }
        }
    }


    private void removeDevicesFromEmployee(Employee employee) {
        // 1) Om personen inte har devices, avbryt
        if (employee.getDevices().isEmpty()) {
            System.out.println("This employee has no devices to remove.");
            return;
        }

        // 2) Visa numrerad lista över devices som personen har
        System.out.printf("Devices for %s %s:%n", employee.getFirstName(), employee.getLastName());
        for (int i = 0; i < employee.getDevices().size(); i++) {
            Device d = employee.getDevices().get(i);
            System.out.printf("%d. %s%n", i + 1, d.toString());
        }

        // 3) Låt användaren välja vilka nummer som ska tas bort (komma-separerat)
        String raw = InputHandler.getNonEmptyString("Enter number(s) to remove (comma-separated). 0 cancels: ");
        if (raw.equals("0")) {
            System.out.println("Canceled.");
            return;
        }

        String[] parts = raw.split(",");
        boolean removedAny = false;

        // 4) Gå igenom varje inmatat nummer
        for (String p : parts) {
            String trimmed = p.trim();
            if (trimmed.isEmpty()) {
                System.out.println("Empty value ignored.");
                continue;
            }

            // Tillåt bara siffror (ingen try/catch som du önskade)
            boolean isNumber = true;
            for (int i = 0; i < trimmed.length(); i++) {
                if (!Character.isDigit(trimmed.charAt(i))) {
                    isNumber = false;
                    break;
                }
            }
            if (!isNumber) {
                System.out.println("Invalid number: " + trimmed);
                continue;
            }

            int pick = Integer.parseInt(trimmed);
            if (pick < 1 || pick > employee.getDevices().size()) {
                System.out.println("Out of range: " + trimmed);
                continue;
            }

            // 5) Hämta device (index = pick-1), ta bort från employee och ”unassigna” på device
            Device toRemove = employee.getDevices().get(pick - 1);
            // Viktigt: först frigör device, sedan ta bort kopplingen i employee
            toRemove.unassign();            // markerar enheten som ledig
            employee.removeDevice(toRemove);
            System.out.printf("Removed %s from %s %s%n",
                    toRemove.toString(), employee.getFirstName(), employee.getLastName());
            removedAny = true;
        }

        if (!removedAny) {
            System.out.println("No devices were removed.");
        }
    }


    private void printDeviceInventory() {
        if (inventory.isEmpty()) {
            System.out.println("No inventory found.");
            return;
        }
        inventory.sort((a, b) -> {
            int cmp = a.getType().compareToIgnoreCase(b.getType());
            if (cmp == 0) {
                return a.getIdentifier().compareToIgnoreCase(b.getIdentifier());
            }
            return cmp;
        });
        System.out.println("------------Device inventory-------------");
        int i = 1;
        for (Device d : inventory) {
            String status;
            if (d.isLoaned()) {
                status = "Loaned to " + d.getBorrowerName() + " (" + d.getBorrowerEmail() + ")";
            } else {
                status = "Available";
            }
            System.out.printf("%d. %s | %s\n", i, d.toString(), status);
            i++;
        }
    }

    private void printAllContracts() {
        List<Employee> employees = registry.getEmployees();
        employees.sort((a, b) -> {
            int cmp = a.getLastName().compareToIgnoreCase(b.getLastName());
            if (cmp == 0) {
                return a.getFirstName().compareToIgnoreCase(b.getFirstName());
            }
            return cmp;
        });
        System.out.println("------------Contracts-------------");

        boolean any = false;
        int i = 1;
        for (Employee e : employees) {
            Contract c = e.getContract();
            if (c != null) {
                any = true;
                String status;
                if (c.isSigned()) {
                    status = "Signed";
                } else {
                    status = "Not signed";
                }
                System.out.printf(
                        "%d. %s %s | Type: %s | Status: %s\n",
                        i,
                        e.getFirstName(),
                        e.getLastName(),
                        c.getType(),
                        status
                );
                i++;
            }
        }

        if (!any) {
            System.out.println("No contracts found.");
        }
    }

    private void deviceMenu() {
        while (true) {
            System.out.println("------ Device Administration ------");
            System.out.println("1. List all devices");
            System.out.println("2. Add new device");
            System.out.println("3. Remove a device");
            System.out.println("4. Back to main menu");

            int choice = InputHandler.getInt("Select: ");
            switch (choice) {
                case 1:
                    printDeviceInventory();   // listar alla devices
                    break;
                case 2:
                    addNewDevice();           // lägger till en ny device
                    break;
                case 3:
                    removeDevice();           // tar bort en device
                    break;
                case 4:
                    return; // tillbaka till main
                default:
                    System.out.println("Invalid choice. Choose 1–4.");
            }
        }
    }


    private void addNewDevice() {
        String type = InputHandler.getNonEmptyString("Enter device type (Laptop/Mobile/etc): ");
        String identifier = InputHandler.getNonEmptyString("Enter device identifier (e.g. Dell XPS13): ");
        Device d = new Device(type, identifier);
        inventory.add(d);
        System.out.printf("Device added: %s%n", d);
    }

    private void removeDevice() {
        if (inventory.isEmpty()) {
            System.out.println("No devices to remove.");
            return;
        }

        printDeviceInventory();
        int choice = InputHandler.getInt("Enter device number to remove (0 to cancel): ");
        if (choice == 0) {
            System.out.println("Canceled.");
            return;
        }
        if (choice < 1 || choice > inventory.size()) {
            System.out.println("Invalid number.");
            return;
        }

        Device d = inventory.get(choice - 1);

        if (d.isLoaned()) {
            System.out.println("Cannot remove. Device is currently loaned to " + d.getBorrowerName());
            return;
        }

        inventory.remove(d);
        System.out.printf("Device removed: %s%n", d);
    }

    // Ändra detaljer för en employee
    private void changeEmployeeDetails(Employee e) {
        System.out.println("----- Change details -----");
        System.out.printf("Current first name: %s%n", e.getFirstName());
        System.out.printf("Current last name : %s%n", e.getLastName());
        System.out.printf("Current private email: %s%n", e.getEmail());
        System.out.printf("Current work email   : %s%n", e.getWorkEmail());
        System.out.printf("Current role         : %s%n", e.getRole());
        System.out.printf("Current start date   : %s%n", e.getStartDate());
        System.out.println("--------------------------------------");
        System.out.println("Press Enter to keep the current value.");
        System.out.println();

        // 1) Namn
        String newFirst = InputHandler.getLineAllowEmpty("New first name (leave empty to keep): ");
        String newLast = InputHandler.getLineAllowEmpty("New last name  (leave empty to keep): ");

        boolean nameChanged = false;
        if (!newFirst.isEmpty() && !newFirst.equals(e.getFirstName())) {
            e.setFirstName(newFirst);
            nameChanged = true;
        }
        if (!newLast.isEmpty() && !newLast.equals(e.getLastName())) {
            e.setLastName(newLast);
            nameChanged = true;
        }

        // 2) Privat email (måste vara unik om den ändras)
        String newPrivateEmail = InputHandler.getLineAllowEmpty("New private email (leave empty to keep): ");
        if (!newPrivateEmail.isEmpty() && !newPrivateEmail.equalsIgnoreCase(e.getEmail())) {
            while (registry.emailExistsForOther(newPrivateEmail, e)) {
                System.out.println("That email already exists. Please enter a different email (or leave empty to cancel change).");
                String retry = InputHandler.getLineAllowEmpty("New private email: ");
                if (retry.isEmpty()) {
                    newPrivateEmail = ""; // avbryt ändring
                    break;
                }
                newPrivateEmail = retry;
            }
            if (!newPrivateEmail.isEmpty()) {
                e.setEmail(newPrivateEmail);
            }
        }

        // 3) Roll
        String newRole = InputHandler.getLineAllowEmpty("New role (leave empty to keep): ");
        if (!newRole.isEmpty() && !newRole.equals(e.getRole())) {
            e.setRole(newRole);
        }

        // 4) Startdatum
        String newStart = InputHandler.getLineAllowEmpty("New start date yyyy-mm-dd (leave empty to keep): ");
        if (!newStart.isEmpty() && !newStart.equals(e.getStartDate())) {
            e.setStartDate(newStart);
        }

        // 5) Om namnet ändrades → autogenera NY work email
        if (nameChanged) {
            String newWork = registry.generateUniqueEmail(e.getFirstName(), e.getLastName(), "company.com");
            e.setWorkEmail(newWork);
            System.out.printf("Work email regenerated: %s%n", newWork);
        }

        System.out.println("Details updated.");
    }


}
