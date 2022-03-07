package src;

import java.util.ArrayList;

abstract public class Borrower {
    private ArrayList<Item> borrowedItems;
    private int BorrowerId;
    private String Name;
    private String PhoneNumber;
    private int MaxAllowedItems;
    
    public Borrower(int BorrowerId, String Name, String PhoneNumber) {
        borrowedItems = new ArrayList();
        this.BorrowerId = BorrowerId;
        this.Name = Name;
        this.PhoneNumber = PhoneNumber;
    }
   public int getMaxAllowedItems() {
        return MaxAllowedItems;
    }

    public void setMaxAllowedItems(int MaxAllowedItems) {
        this.MaxAllowedItems = MaxAllowedItems;
    }

    public  void update()
    {
        MaxAllowedItems--;
    }
        
    
    public int getBorrowerId() {
        return BorrowerId;
    }

    public void setBorrowerId(int BorrowerId) {
        this.BorrowerId = BorrowerId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }
    public void addToBorrowedItem(Item item){
        borrowedItems.add(item);
    }
    
    public ArrayList<Item> getItems() {
        return borrowedItems;
    }
}
