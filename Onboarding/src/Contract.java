// En enkel kontraktsklass. Vi håller den liten nu, men kan bygga ut senare.
public class Contract {
    private String type;     // t.ex. "Employment", "Consulting" (valfritt nu)
    private boolean signed;  // true = kontrakt är signerat
    private Employee employee;

    public Contract(String type, Employee employee) {
        this.type = type;
        this.signed = false;
        this.employee = employee;
    }

    public String getType() { return type; }
    public boolean isSigned() { return signed; }

    public void sign() {this.signed = true;}
    public Employee getEmployee() { return employee; }

    public void setType(String type) {
        this.type = type;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
