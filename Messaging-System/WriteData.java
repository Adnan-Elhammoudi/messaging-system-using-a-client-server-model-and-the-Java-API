
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;




 public class WriteData {
    



 static void save( ArrayList<Account> Accounts) throws IOException{
        
        try{
         // writing the accounts info to data.out file using ObjectOutputStream
	    FileOutputStream fout =  new FileOutputStream("data.out");
            ObjectOutputStream dataWrite= new ObjectOutputStream(fout);
       
             dataWrite.writeObject(Accounts);
        
        }
        
        
        
        catch(IOException x){  x.printStackTrace();}
    
    
    }
}

     
