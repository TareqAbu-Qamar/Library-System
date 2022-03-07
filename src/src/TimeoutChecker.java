package src;
import java.util.ArrayList;

public class TimeoutChecker extends Timer{
    DataRepository db = DataRepository.getInstance();
    public void TimeCheckOuts()
    {
        for(Item item : db.getAllItems()) //3
        {  if(item.status==Item.state.unAvailable)
            item.checkTimeOuts(); //4
            if(item.getReservationTimeout() == 0 && item.status==Item.state.unAvailable)
                db.addLateItem(item);
         }
        
    }
        
}
        
    
