


import java.util.ArrayList;
import java.util.*;

public class Account implements java.io.Serializable {

    private ArrayList<Messeges> newmessege ;
     private ArrayList<Messeges> Sent ;
    private String Username;
    private String Pass;


   
    public Account(){

    }
    public Account(String Username, String Pass){
        this.Username = Username;
        this.Pass= Pass;
        this.newmessege = new ArrayList<>();
        this.Sent = new ArrayList<>();
    } 
    ArrayList<Messeges> getNewmesseges() {
        return newmessege;
    }

    ArrayList<Messeges> getSentMessages() {
        return Sent;
    }
    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String Pass) {
        this.Pass = Pass;
    }

//inbox messages method
    public void addMessege(String sender, String receiver,String title,String content,  Date date) {
//    adding new message
        newmessege.add(new Messeges( sender,  receiver, title, content,   date));
    }
    //sent messages method
    public void SentMessages(String sender, String receiver,String title,String content,  Date date){
    //    adding new message
            Sent.add(new Messeges( sender,  receiver, title, content,   date));
    }
    
}