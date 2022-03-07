package src;
public class Student extends Borrower {
  
    
    public Student(int BorrowerId, String Name, String PhoneNumber) {
        super(BorrowerId, Name, PhoneNumber);
        this.setMaxAllowedItems(3);
    }

   
    @Override
    public String toString() {
        return "Student\t" +  getBorrowerId() + "\t" + getName() + "\t" + getPhoneNumber() + "\n";
    }
    
    
}
