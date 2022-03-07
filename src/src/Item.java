package src;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
//import com.teknikindustries.bulksms.SMS;


public class Item {
    private ArrayList<Borrower> waitingList;
    Controller c = Controller.getInstance();
    private int ItemId;
    private String Name;
    private String Category;
    private double Price;
    private double chargePrice;
    public state status;
    public enum state
    {
        Available,
        Reserved,
        unAvailable
    }
    private int reservationTimeout=5;
    private int notReturnedTimeout=2;
    private int overdueTimeout=0;
    Controller e = Controller.getInstance();
    
    public Item(int ItemId, String Name, String Category, double Price) {
        waitingList = new ArrayList();
        status = state.Available;
        this.ItemId = ItemId;
        this.Name = Name;
        this.Category = Category;
        this.Price = Price;
    }

    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int ItemId) {
        this.ItemId = ItemId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public int getReservationTimeout() {
        return reservationTimeout;
    }

    public void setReservationTimeout(int reservationTimeout) {
        this.reservationTimeout = reservationTimeout;
    }

    public int getNotReturnedTimeout() {
        return notReturnedTimeout;
    }

    public void setNotReturnedTimeout(int notReturnedTimeout) {
        this.notReturnedTimeout = notReturnedTimeout;
    }

    public int getOverdueTimeout() {
        return overdueTimeout;
    }

    public void setOverdueTimeout(int overdueTimeout) {
        this.overdueTimeout = overdueTimeout;
    }
    public void update(){
        if(this.status == status.Available)
        { this.status=status.unAvailable;
          chargePrice=0;
        }
        else
        {
            this.status=status.Available;
            chargePrice=0;
        }
            
    }
    public void addToWaitingList(Borrower borrower){
       // this.status= state.Reserved;
        waitingList.add(borrower);
    }
    public ArrayList<Borrower> getToWaitingListitems(){
       // this.status= state.Reserved;
        return waitingList;
    }
    
    @Override
    public String toString() {
        return ItemId + "\t" + Name + "\t" + Category + "\t" + Price + "\n";
    }
     public void SMS(String phoneNum,String msg) 
     {
         System.out.println(msg);
       try {
			// Construct data
			String apiKey = "apikey=" + "NlomzLJgxOc-p38JIKKdzQ6jA33GtIxCIKeCQYBJ04";
			String message = "&message=" + msg;
			String sender = "&sender=" + "Libray Sytem";
			String numbers = "&numbers=" + phoneNum;
			
			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
			String data = apiKey + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes());
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();
		
		} catch (Exception e) {
			System.out.println("Error SMS "+e);
		}
       /*This code is supposed to be executed without problems as it appears from 
       the website of this service that the SMS message arrives to the person concerned, 
       we have attached a picture explaining this*/



    }
    public void checkTimeOuts(){
        DataRepository db = DataRepository.getInstance();
        int id=0 ;
        for (Borrower borrower : db.getBorrowers())//5
        {
            if(borrower.getItems().contains(this))
                id=borrower.getBorrowerId();
        }

        if(reservationTimeout>0)
        {
             reservationTimeout--;
        
        }
        else if(reservationTimeout==0 && notReturnedTimeout>0)
        {   
            if(notReturnedTimeout==2) // the msg sent when reservationTimeout==0 for one time.
                SMS(db.getBorrwer(id).getPhoneNumber(),db.getBorrwer(id).getName()+" Reservation Time out = 0 you will have choice with two more days to return "+this.Name
                +" with actul price = " +this.Price);
            notReturnedTimeout--;
        }

        else if( reservationTimeout==0 && notReturnedTimeout==0 )
            {
                overdueTimeout++;
                if(notReturnedTimeout==0) // the msg sent while overdueTimeout>=0 with each period.
                    SMS(db.getBorrwer(id).getPhoneNumber(),db.getBorrwer(id).getName()+"Reservation Time out = 0, You have exceeded the "
                            + "borrowing period by "+overdueTimeout+" days you must return the item and pay for it "+charge());
            }

        
    }
    public double charge()
    {
        chargePrice+=Price*3;
        return chargePrice;
    }
            
    
        
}
    

