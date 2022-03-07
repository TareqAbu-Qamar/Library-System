package src;
import src.Item.state;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
public class Controller  extends Thread{

    protected DataRepository db ;
    protected Borrower borrower ;
    protected Item item;
    private static Controller instance = new Controller();
    protected TimeoutChecker checker = new TimeoutChecker();



    public static Controller getInstance() {
       
    return instance;
    }
    

    private Controller()
    {
        db =  DataRepository.getInstance();
       
    }
    public ArrayList<Borrower> getBorrowers() {
        return db.getBorrowers();
    }
    public boolean borrow(int brId,int itemId)
    {  
        borrower=getBorrower(brId); //1.2
        item = getItem(itemId); // 2.2
        if(db.getBorrwer(brId).getMaxAllowedItems()>0) // check some conditions in phase1
        {
            if(item.status==Item.state.unAvailable)
           {
           
               JOptionPane.showMessageDialog(null, borrower.getName()+":\n The item you want to borrow unAvailable "
                , "Error!",JOptionPane.ERROR_MESSAGE);
           }
            else
            { borrower.update(); //2.3
              item.update(); //2.4
              db.setItem(db.getItem(itemId)); //2.5
              db.setBorrower(db.getBorrwer(brId)); //2.5
              borrower.addToBorrowedItem(item);
            
              return true;
            }
        }
        return false;
        
            
    }
    public Borrower getBorrower(int brId)
    {
        return db.getBorrwer(brId);
    }

    public void reserve(int brId,int itemId)
    {
 
         item = db.getItem(itemId); //1.2
         borrower = db.getBorrwer(brId); //2.2
         item.addToWaitingList(borrower); //2.3 
         JOptionPane.showMessageDialog(null, borrower.getName()+":\n The reserving operation completed successfully "
                , "Congratulations!",JOptionPane.INFORMATION_MESSAGE);
         
           //2.4 +2.5 depends on if borrower return item or not and these two step already exist in borrow process
    }
    public Item getItem(int itemId)
    {
         return db.getItem(itemId);
    }
    public void returnItem(int itemId)
    {    item=db.getItem(itemId);//1.2
         item.update();//1.3
         db.setItem(item);//1.4
         JOptionPane.showMessageDialog(null,"\n The return operation completed successfully "
                , "Congratulations!",JOptionPane.INFORMATION_MESSAGE);
          
          if(db.getLateItems().contains(db.getItem(itemId)))
              db.getLateItems().remove(db.getItem(itemId));
          sendSMStoFirstInWaitingList(itemId); //1.5
         
    }
    public void sendSMStoFirstInWaitingList(int itemId)
    {
          
           while(!db.getItem(itemId).getToWaitingListitems().isEmpty())
           { 
               db.getItem(itemId).SMS( db.getItem(itemId).getToWaitingListitems().get(0).getPhoneNumber(),
                       db.getItem(itemId).getToWaitingListitems().get(0).getName()+" you can check your reseved item");
                     try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
                     db.getItem(itemId).getToWaitingListitems().remove(0);
                
           }
           
    }

    public ArrayList<Item> getItems(int brId)
    {
         return borrower.getItems(); //1.2
    }
    public ArrayList<Item> getAllItems()
    {
         return db.getAllItems();
    }
    public void removeItem(int itemId)
    {
        db.removeItem(itemId);//1.2
        JOptionPane.showMessageDialog(null,"\n The item :  removed  successfully "
                , "Congratulations!",JOptionPane.INFORMATION_MESSAGE);
        
    }
    public void AddItem(Item item)
    {
        db.setItem(item);
        JOptionPane.showMessageDialog(null, "\n The item : "+item.getName()+" added successfully "
                , "Congratulations!",JOptionPane.INFORMATION_MESSAGE);
    }
    public void removeBorrower(int brId)
    {
        db.removeBorrower(brId);//1.2
        JOptionPane.showMessageDialog(null, "\n The borrower :  removed successfully "
                , "Congratulations!",JOptionPane.INFORMATION_MESSAGE);
    }
    public void addBorrower(Borrower borrower)
    {
        db.setBorrower(borrower);
        JOptionPane.showMessageDialog(null, "\n The borrower : "+borrower.getName()+" added successfully "
                , "Congratulations!",JOptionPane.INFORMATION_MESSAGE);
        
    }
    public void tick()
    {
          
          checker.TimeCheckOuts();  // 2 

    }
     public ArrayList<Item> getLateItems()
    {
        return db.getLateItems();
    }
}