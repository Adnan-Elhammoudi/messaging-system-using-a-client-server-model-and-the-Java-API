

import java.util.*;

public class Messeges implements java.io.Serializable {


 
    private String sender;
    private String receiver;
    private Date date;
    private String title;
    private String content;
 

     
    public Messeges(){
     
    }
    public Messeges(String sender, String receiver,String title,String content,  Date date){
       this.sender = sender;
        this.receiver= receiver;
         this.title = title;
          this.content= content;
           this.date= date;
          
    }
 

    public String getsender() {
        return sender;
    }
    
       public String getreceiver() {
        return receiver;
    }
    
          public String gettitle() {
        return title;
    }
    
             public String getcontent() {
        return content;
    }
    
       public Date getdate() {
            return date;
    }
    
              

    public void setsender(String sender) {
        this.sender = sender;
    }


    public void setreceiver(String receiver) {
        this.receiver = receiver;
    }
    
    public void settitle(String title) {
        this.title = title;
    }
    
    public void setcontent(String content) {
        this.content = content;
    }
    
    public void setdate(Date date) {
        Date dateSent = new Date();
         date=dateSent;
    }
    

   public String toString(){
        return "From: "+sender+"\n"+"To: "+receiver+"\n"+"title: "+title+"\n"+"Date: "+date+"\n\n\n"+content;
    }
}
