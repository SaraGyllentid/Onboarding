import java.util.ArrayList;
import java.util.List;

public class EmployeeRegistry {

    private final ArrayList<Employee> employees;
    public EmployeeRegistry() {
        this.employees = new ArrayList<>();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    //lägg till ny employee
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    //Söka på namn
    public List<Employee> searchByName(String name) {
        List<Employee> matches = new ArrayList<>();
        if (name == null) {
            return matches;
        }
        String trimmedName = name.trim().toLowerCase();
        if (trimmedName.isEmpty()) {
            return matches;
        }
        for (Employee employee : employees) {
            String firstName = employee.getFirstName();
            String lastName = employee.getLastName();
            String full = (firstName + " " + lastName).trim();
            if (firstName.toLowerCase().contains(trimmedName) || lastName.toLowerCase().contains(trimmedName) || full.toLowerCase().contains(trimmedName)) {
                matches.add(employee);
            }
        }
        return matches;
    }

    //lista alla employees
    public void printEmployees() {
        System.out.println("----------Employees----------");
        if(employees.isEmpty()) {
            System.out.println("No employees found");
            return;
        }
        employees.sort((a, b) -> {
            int cmp = a.getLastName().compareToIgnoreCase(b.getLastName());
            if (cmp == 0) {
                return a.getFirstName().compareToIgnoreCase(b.getFirstName());
            }
            return cmp;
        });
        int index = 1;
        for (Employee employee : employees) {
            String status; if(employee.isOnboardingDone()) {
                status ="Done";
            } else {
                status = "Pending";
            }
            String devicesText;
            if (employee.getDevices().isEmpty()) {
                devicesText = "no devices";
            } else {
                String devicesTextTemp = "";
                for (int i = 0; i < employee.getDevices().size(); i++) {
                    Device device = employee.getDevices().get(i);
                    devicesTextTemp = devicesTextTemp + device.toString();
                    if (i < employee.getDevices().size() - 1) {
                        devicesTextTemp = devicesTextTemp + ",";
                    }
                }
                devicesText = devicesTextTemp;
            }
            String contractStatus;
            if (employee.hasSignedContract()) {
                contractStatus = "Signed";
            } else {
                contractStatus = "Not signed";
            }

            System.out.printf("%d. %s %s | Private: %s | Work: %s | %s | Contract: %s | Onboarding: %s | Devices: %s%n",
                    index,
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getEmail(),
                    employee.getWorkEmail(),
                    employee.getRole(),
                    contractStatus,
                    status,
                    devicesText);
            index++;
        }
    }
    //Koll om det redan finns en email
    public boolean emailExists(String email) {
        if (email == null) {return false;}
        for (Employee employee : employees) {
            String p = employee.getEmail();
            String w = employee.getWorkEmail();
            if (p != null && p.equalsIgnoreCase(email)) {return true;}
            if (w != null && w.equalsIgnoreCase(email)) {return true;}
        }
        return false;
    }
    public String generateUniqueEmail(String firstName, String lastName, String domain) {
        String baseFirst = normalizeNamePart(firstName);  // a–z, 0–9
        String baseLast  = normalizeSurname(lastName);    // flera efternamn -> punktseparerat

        String base = baseFirst + "." + baseLast;         // t.ex. "anna.af.akker"
        String candidate = base + "@" + domain;
        int counter = 2;                                  // börja på 2 för första krock

        while (emailExists(candidate)) {
            candidate = base + counter + "@" + domain;    // anna.af.akker2@domain
            counter++;
        }
        return candidate;
    }


    private String normalizeNamePart(String s) {
        if (s== null) {
            return "";
        }
        String lower  = s.toLowerCase();
        lower = replaceNordic(lower);
        return lower.replaceAll("[^a-z0-9]", "");
    }
    private String normalizeSurname(String s) {
        if (s == null) { return ""; }
        String lower = s.toLowerCase();
        lower = replaceNordic(lower);
        // splitta på whitespace, normalisera varje del, och join:a med punkt
        String[] parts = lower.trim().split("\\s+");
        String result = "";
        for (int i = 0; i < parts.length; i++) {
            String p = parts[i].replaceAll("[^a-z0-9]", "");
            if (p.isEmpty()) {
                continue;
            }
            if (result.isEmpty()) {
                result = p;
            } else {
                result = result + "." + p;
            }

        }
        return result;
    }
    private String replaceNordic(String s) {
        return s
                .replace('å','a').replace('Å','A')
                .replace('ä','a').replace('Ä','A')
                .replace('ö','o').replace('Ö','O');
    }
    // Kolla om en email redan finns hos någon annan
    public boolean emailExistsForOther(String email, Employee current) {
        if (email == null) return false;
        for (Employee e : employees) {
            if (e == current) continue; // hoppa över nuvarande
            String p = e.getEmail();
            String w = e.getWorkEmail();
            if (p != null && p.equalsIgnoreCase(email)) return true;
            if (w != null && w.equalsIgnoreCase(email)) return true;
        }
        return false;
    }


}
