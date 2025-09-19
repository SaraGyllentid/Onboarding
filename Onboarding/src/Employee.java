import java.util.ArrayList;
import java.util.List;

public class Employee {
    private String firstName;
    private String lastName;
    private String email;
    private String startDate;
    private String role;
    private String workEmail;
    private boolean onboardingDone;
    // lägger till devices
    private List<Device> devices;
    private Contract contract;

    // standard me default false för onboarding
    public Employee(String firstName, String lastName, String email, String startDate, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.startDate = startDate;
        this.role = role;
        this.workEmail = null;
        this.onboardingDone = false;
        this.devices = new ArrayList<>();
        this.contract = null;
    }
    //ospecificerad onboarddingstatus
    public Employee(String firstName, String lastName, String email, String startDate, String role, boolean onboardingDone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.startDate = startDate;
        this.role = role;
        this.onboardingDone = onboardingDone;
        this.devices = new ArrayList<>();
        this.workEmail = null;
        this.contract = null;
    }
    public void addDevice(Device device) {
        this.devices.add(device);
    }
    public void removeDevice(Device device) {
        this.devices.remove(device);
    }
    public List<Device> getDevices() {return devices;}

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public boolean isOnboardingDone() {
        return onboardingDone;
    }

    public void setOnboardingDone(boolean onboardingDone) {
        this.onboardingDone = onboardingDone;
    }

    public String getWorkEmail() {
        return workEmail;
    }
    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }
    public Contract getContract() {
        return contract;
    }
    public void setContract(Contract contract) {
        this.contract = contract;
    }
    public boolean hasSignedContract() {
        if (contract == null) {
            return false;
        }
        return contract.isSigned();
    }
    public boolean hasAtLeastOneDevice() {
        if (devices == null) {
            return false;
        }
        return !devices.isEmpty();
    }




}
