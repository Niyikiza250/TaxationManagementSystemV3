package rw.gov.rra.model;

/**
 *
 * @author jeremie
 */
public class TaxPayer {
   // private variables
   private String tin;
   private String firstName;
   private String lastName;
   private Double amount;
   private String email;
   private String phoneNumber;

    public void setTin(String tin) {
        this.tin = tin;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    

    public String getTin() {
        return tin;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Double getAmount() {
        return amount;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    

    public TaxPayer() {
    }

    public TaxPayer(String tin, String firstName, String lastName, Double amount, String email, String phoneNumber) {
        this.tin = tin;
        this.firstName = firstName;
        this.lastName = lastName;
        this.amount = amount;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

   

  
   
}
