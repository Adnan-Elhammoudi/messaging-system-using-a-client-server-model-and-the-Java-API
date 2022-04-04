import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;


  

public class Server 
{
    public static void main(String[] args) throws IOException 
    {
  // creat server socket and binding with port 60000
        ServerSocket ss = new ServerSocket(6000);

   
        while (true) 
        {
            Socket s = null;
              
            try 
            {
             //accept client connection   
            s = ss.accept();
                  
            System.out.println("A new client is connected : " + s);
                       
//read the response from the user using DataInputStream
            DataInputStream in = new DataInputStream(s.getInputStream());
  //writingthe  response from the user using DataOutputStream   
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
//writing the messages object to the user using ObjectOutputStream
            OutputStream outputStream = s.getOutputStream();
            ObjectOutputStream m= new ObjectOutputStream(outputStream);
                  
            System.out.println("Assigning new thread for this client");
  
                // create a new thread object
                Thread t = new ClientHandler(s, in, out,m);
  
                // Invoking the start() method
                    t.start();
                  
            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }
}
  
// ClientHandler class
class ClientHandler extends Thread 
{
//passing the connection parameters
             ObjectOutputStream m;
    final    DataOutputStream out;
    final    DataInputStream in;
    final    Socket s;
             String userIN;
             ObjectInputStream dataread;
    ArrayList<Account> Accounts = new ArrayList<>()  ;


 

    public ClientHandler(Socket s,  DataInputStream in , DataOutputStream out,ObjectOutputStream m) 
    {
        this.s = s;
        this.in = in;
        this.out = out;
        this.m= m;

        
    }
  
    @Override

    public void run() 
    {



while (true) 
{
    try {
              //read data of accounts from data.out object stream       
            FileInputStream fin = new FileInputStream("data.out");
            BufferedInputStream bim=new BufferedInputStream(fin);
            this.dataread = new ObjectInputStream(bim);

        // Ask user what he wants
        out.writeUTF("[ (1) Sgin in | (2) Sign Up]... \n type exit to terminate connection.");
        
        // receive the answer from client
        String   received = in.readUTF();
// if the received response is exit the server determines the connection and close all streams
        if("exit".equals(received))
        {
            System.out.println("Client " + this.s + " sends exit...");
            System.out.println("Closing this connection.");
            WriteData.save(Accounts);
            this.s.close();
            this.out.close();
            this.in.close();
            this.m.close();
            
            
            System.out.println("Connection closed");
            break;
        }
                   
        outerloop:
             switch (received) {
            
 case "1" : //sign in case
                
                 this.userIN = in.readUTF(); //read the username
                String passIN = in.readUTF();//read the password
try{
               Accounts=(ArrayList<Account>) dataread.readObject(); //read the accounts info
                             
                              
}//catch end of file and file not found exceptions and break from the switch state
  catch(EOFException | FileNotFoundException x){ out.writeUTF("No Accounts are Listed ...Sign up !");  break; }
   
                for(int r = 0 ;r<Accounts.size();r++) {
                    // Verify that the received username and password are correct
                    if((userIN.equals(Accounts.get(r).getUsername()))&&(passIN.equals(Accounts.get(r).getPass()))){
                                 WriteData.save(Accounts);
                        out.writeUTF("signed in Successfully!");
                        //begin new session
                        logInfo(); 
                        //break to outerloop after logging off
                        break outerloop;}
                }
                
                out.writeUTF("Invalid Username and/or Password . Please try again");
                
                
                break;
//             }
 case "2" :  //sign up case
               
                String userUP = in.readUTF(); //read the username
                String passUP = in.readUTF(); //read the password
                boolean taken =true;
                      
                      
 try{
              
  
                        Accounts=(ArrayList<Account>) dataread.readObject(); //read the Accounts info
                   
                   
      
                }
 //catch end of file and file not found exceptions and add the sign up info then save it to data.out
catch(EOFException | FileNotFoundException z){  Accounts.add(new Account(userUP,passUP));    WriteData.save(Accounts);
                      out.writeUTF("signed up Successfully !");   break;}
              
//confirm this new username is not taken
                for(int r = 0 ;r<Accounts.size();r++) {
                    
                    if(userUP.equals(Accounts.get(r).getUsername())){
                        out.writeUTF("Username already taken !");  WriteData.save(Accounts); 
                        
                        taken =false; break;
                        
                    }}
                // if taken flag is false the add new account info
                if(taken){
                    
                    Accounts.add(new Account(userUP,passUP));
                          WriteData.save(Accounts);
                    
                    out.writeUTF("signed up Successfully !");   break;}
             
                  
                break;
// invalid input from the user
            default:
                out.writeUTF("Invalid input");
                break;
        }}

    catch (IOException e) {
        try {
            WriteData.save(Accounts);
        } catch (IOException ex) {
         ex.printStackTrace();
        }
    
    }

    
    catch (Exception ex) {
        ex.printStackTrace();

    }}
}
    
     public void logInfo() throws Exception{
    
      ArrayList<Object> usernames = new ArrayList<>();
      boolean flag=true; //correct username selection flage for sendin a message
      int index = 0; // index of the accounts objects
         
 while(true){
                        
      out.writeUTF("[ (1) View Messages | (2) Send a Message | (3) Log Off ].. \n ");      //main menu selections
      
           String main = in.readUTF();// read the user response
           if ("3".equals(main)){   WriteData.save(Accounts);break;} // 3 indicate logging off
                   
              
switch(main){
    
    
       case "1"://View Messages
            
       
           
        out.writeUTF( "[ (1) view inbox messages | (2) sent messages ]");
           String response = in.readUTF();
           if("1".equals(response)){ //view inbox messages 
               
               
               //serching for the account object index
                  index = 0;
                 for(int i =0 ; i<Accounts.size();i++){
                  if(userIN.equals(Accounts.get(i).getUsername())){
                    break;

                              }
                      index++;
                              }     

 //check if the inbox is empty
           if(!(Accounts.get(index).getNewmesseges().isEmpty())){
                  out.writeUTF("is");//sending  "is" means it's not empty
                  m.writeObject(  Accounts.get(index).getNewmesseges()); //sending the inbox messages list array
          break;}
           
               else{ out.writeUTF("Empty Inbox!"); break;}
           
           }
         
        
    
          else{
           if("2".equals(response)) {//sent messages
       //serching for the account object index
                index = 0;
                for(int i =0 ; i<Accounts.size();i++){
                  if(userIN.equals(Accounts.get(i).getUsername())){
                    break;

                              }
                      index++;
                              }  

           m.writeObject(Accounts.get(index).getSentMessages()); //sending the sent messages list array
            break;
           }
           
                  else {out.writeUTF("Invalid input"); //sending the invalid input from the user
                       break;}}
                   

       
          
        
 case "2"://Send a Message
        for(int r = 0 ;r<Accounts.size();r++) {
                       
        usernames.add(Accounts.get(r).getUsername());  }//getting all the usernames that sign up
  
         m.writeObject(usernames); //sending the usernames array list
       
        String   receiver = in.readUTF();//read  the desired receiver username
        
        for(int r = 0 ;r<Accounts.size();r++) {
                   //checking for the receiver username spelling
 if(receiver.equals(Accounts.get(r).getUsername())){
               out.writeUTF("countinue");// sending countinue if the username is correct 
               String   title = in.readUTF(); //recieve the title
               String   content = in.readUTF(); //recieve the content
                   index = 0;                        
                for(int i =0 ; i<Accounts.size();i++){
                  if(receiver.equals(Accounts.get(i).getUsername())){    //serching for the reciever account object index
                    break;

                              }
                      index++;
                              }  

                Accounts.get(index).addMessege(userIN, receiver, title, content, new Date());// adding a message inside the reciever inbox
              
                 index = 0;
                for(int i =0 ; i<Accounts.size();i++){
                  if(userIN.equals(Accounts.get(i).getUsername())){     //serching for the user object index
                    break;

                              }
                      index++;
                              }  

                  Accounts.get(index).SentMessages(userIN, receiver, title, content, new Date()); // adding the message inside the user  sent massages
                      WriteData.save(Accounts);  //saving the modification
                  out.writeUTF("Messsge Sent!");
                 
                    flag=false;  break ;  }}
             // if the flag is true that means the receiver username spelling is incorrect
          if(flag){
                   out.writeUTF("Write The Correct Username!");    
                 break ;}
              
           break;
            
     default:
        out.writeUTF("Invalid input");// sending invalid input from the user
           
         break;
   
   }
   
  }}

   
}
 