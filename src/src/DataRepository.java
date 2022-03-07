package src;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
public class DataRepository {
    private ArrayList <Item> listOfLibraryItems;
    private ArrayList <Borrower> listOfBorrowers;
    private static DataRepository instance = new DataRepository();
    private static ArrayList <Item> listOfLateItems  = new ArrayList();
    File file = new File("src/RepositoryFiles/input.txt");
    
    

    public static DataRepository getInstance() {
       
    return instance;
    }
    private DataRepository() {
        
        listOfLibraryItems = new ArrayList();
        listOfBorrowers = new ArrayList();
       
        try {
            readRepositoryFile();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public ArrayList<Borrower> getBorrowers() {
        return listOfBorrowers;
    }
    private void readRepositoryFile() throws FileNotFoundException{

        File file = new File("src/RepositoryFiles/input.txt");
        Scanner scanner = new Scanner(file);
        int numberOfItems, numberOfBorrowers;

        numberOfItems = 2;
        scanner.next();

        for (int i = 0; i < numberOfItems; i++) {
            int itemId = scanner.nextInt();
            String name = scanner.next();
            String category = scanner.next();
            double price = scanner.nextDouble();
            Item newItem = new Item(itemId, name, category, price);
            listOfLibraryItems.add(newItem);
        }

        numberOfBorrowers = 2; 
        scanner.next();

        for (int i = 0; i < numberOfBorrowers; i++) {
            String type = scanner.next();
            int borrowerId = scanner.nextInt();
            String name = scanner.next();
            String phoneNumber = scanner.next();
            Borrower newBorrower;

            if (type.equals("Teacher"))
                 newBorrower = new Teacher(borrowerId, name, phoneNumber);

            else
                newBorrower = new Student(borrowerId, name, phoneNumber);

            listOfBorrowers.add(newBorrower);
        }
    }
    private void writeRepositoryFile(Object obj) throws IOException{
      
        BufferedWriter out = new BufferedWriter( 
                   new FileWriter("src/RepositoryFiles/output.txt", true)); 
             
        
        String data = "";

          if(obj instanceof Item)
            data += "Item : "+obj.toString();
          if(obj instanceof Borrower)
            data += "Borrowed by : "+obj.toString() ;

        
        out.write(data); 
        out.close();
        
        
    }
    
    public void setItem(Item item) {
        if(!listOfLibraryItems.contains(item))
            listOfLibraryItems.add(item);
        try {
            writeRepositoryFile(item);
        } catch (IOException ex) {
            Logger.getLogger(DataRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    } ///////////////////////////////////////// add to file
    
    public Item getItem(int itemId) {//get item with id
        for (Item item : listOfLibraryItems)
            if (item.getItemId() == itemId)
                return item;
        return null;//no item with that id
    }
    
    public Item getItem(String itemName) {//get item with name
        for (Item item : listOfLibraryItems)
            if (item.getName() == itemName)
                return item;
        return null;//no item with that name
    }
    
    
    public void setBorrower(Borrower borrower){
        try {
            if(!listOfBorrowers.contains(borrower))
                  listOfBorrowers.add(borrower);
            writeRepositoryFile(borrower);
        } catch (IOException ex) {
            Logger.getLogger(DataRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }///////////////////////////////////////// add to file
    
    public Borrower getBorrwer(int borrowerId) {
        for (Borrower borrower : listOfBorrowers)
            if (borrower.getBorrowerId() == borrowerId)
                return borrower;
        
        return null;//no borrower with that id
    }
    
    
    public ArrayList<Item> getAllItems() {
        return listOfLibraryItems;
    }

    public ArrayList<Item> getLateItems() {

        return listOfLateItems;
    }
     public void addLateItem(Item item) {
         if(!listOfLateItems.contains(item))
            listOfLateItems.add(item);
    }

    public void removeItem(int itemId) {

                
        listOfLibraryItems.remove(this.getItem(itemId));
    }

    void removeBorrower(int brId) {
        Borrower deleted = getBorrwer(brId);
        listOfBorrowers.remove(deleted);
    }
    
    
    
    
}
