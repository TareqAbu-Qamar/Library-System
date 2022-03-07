package src;
public class Teacher extends Borrower {
    
    public Teacher(int BorrowerId, String Name, String PhoneNumber) {
        super(BorrowerId, Name, PhoneNumber);
        this.setMaxAllowedItems(6);
    }

    
    @Override
    public String toString() {
        return "Teacher\t" +  getBorrowerId() + "\t" + getName() + "\t" + getPhoneNumber() + "\n";
    }
   
    
}
