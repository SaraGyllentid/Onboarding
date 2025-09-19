public class Device {
    private String type;
    private String identifier;
    private boolean loaned;
    private String borrowerName;
    private String borrowerEmail;


    public Device(String type, String identifier) {
        this.type = type;
        this.identifier = identifier;
        this.loaned = false;
        this.borrowerName = null;
        this.borrowerEmail = null;
    }

    public String getType() {
        return this.type;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public boolean isLoaned() {
        return this.loaned;
    }

    public String getBorrowerName() {
        return this.borrowerName;
    }

    public String getBorrowedEmail() {
        return this.borrowerEmail;
    }

    public void assignTo(Employee employee) {
        this.loaned = true;
        this.borrowerEmail = employee.getEmail();
        this.borrowerName = employee.getFirstName() + " " + employee.getLastName();
    }

    public void unassign() {
        this.loaned = false;
        this.borrowerEmail = null;
        this.borrowerName = null;
    }

    public String getBorrowerEmail() {
        return this.borrowerEmail;
    }

    @Override
    public String toString() {
        return "Device [type=" + type + ", identifyer=" + identifier + "]";
    }
}
