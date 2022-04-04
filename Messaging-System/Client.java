
import java.io.*;
import java.net.*;
import java.util.ArrayList;

  
// Client class
public class Client 
{
    public static void main(String[] args) throws IOException 
    {
        try
        {  // creat  socket and binding with port 60000
            Socket s = new Socket("localhost", 6000);
      
                String username =null;
                
                // receiving the response of the server using DataInputStream
                DataInputStream In = new DataInputStream(s.getInputStream());
                 //sending the response of the user using DataInputStream
                DataOutputStream Out = new DataOutputStream(s.getOutputStream());
                //receiving the messages objects from the server using ObjectInputStream
                InputStream inputStream = s.getInputStream();
                ObjectInputStream m= new ObjectInputStream(inputStream);
                  // receiving the response of the user 
                BufferedReader UserInput =new BufferedReader(   new InputStreamReader(System.in));
                
                
                
                
                String response = In.readUTF(); //read the response from the server
                System.out.println(response+"\n");
       while(true){
             
               String  userInput = UserInput.readLine();//read the response from the user and write it to the server
               Out.writeUTF(userInput);
               
     switch (userInput){
                   
                   
        case "exit":// determine the connection
                      
                     System.out.println("Closing this connection : " + s);
                     s.close();
                     In.close();
                     Out.close();
                     m.close();
                     UserInput.close();
                   
                     System.out.println("Connection closed");
                  break;
                    
                    
        case "1":// read the username and password for signing in and signing up 
        case "2":
                   
                   System.out.print("Username: ");//read the username from the user and write it to the server
                    username = UserInput.readLine();
                   Out.writeUTF(username);
                
                  System.out.print("Password: "); //read the password from the user and write it to the server
                   String Password = UserInput.readLine();
                   Out.writeUTF(Password);
                        response = In.readUTF(); //read the response from server
                System.out.println("\n"+response);
                break;
                
        default :
                  response = In.readUTF(); //read the response from the server if the input is invalid
                System.out.println(response);
                break;

        }
                  if("signed in Successfully!".equals(response)){  
               
     
                logInfo t = new logInfo(s,username, In, Out,m,UserInput); //begin new session if the input info is correct
                t.session();
                }
                 response = In.readUTF(); //read the response from the server if the username is already taken or if the log in info is incorrect
                System.out.println(response);
        
     
               }                
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
static class logInfo{
                             //passing the connection parameters
       final  ObjectInputStream m;
       final  DataOutputStream out;
       final  DataInputStream in;           
       final  Socket s;
       final String username;
       final BufferedReader UserInput;
       
       
       
       
      public logInfo( Socket s, String username, DataInputStream in, DataOutputStream out, ObjectInputStream m, BufferedReader UserInput) {
       this.username= username;
        this.s = s;
        this.in = in;
        this.out = out;
        this.m= m;
        this.UserInput =UserInput;
 
 
    }

 public void session() throws IOException{
 
     while (true) {
try{   
            
              String response = in.readUTF();// read the main menu from the server 
                System.out.println(response);
               String userInput = UserInput.readLine(); //read the response from the user and send it to the server
               out.writeUTF(userInput);            
               if("3".equals(userInput)){break;} // "3" indicate to log off
               
   switch(userInput){
       
       
   case "1": //View messages
                  response = in.readUTF();// read the main menu from the server 
                  System.out.println(response);
                      
                  userInput = UserInput.readLine();//read the response from the user and send it to the server
                  out.writeUTF(userInput);
                       
                        if("1".equals(userInput)){ //read the inbox messages
                          response = in.readUTF();// read the main menu from the server 
                            System.out.println(response);
                            if ("Empty Inbox!".equals(response)){ // if the inbox is empty break from the loop
                            
                            break;
                            }

                ArrayList<Account>Inbox=( ArrayList<Account>) m.readObject();            //read the inbox messages from the server 
                        for(int r = 0 ;r<Inbox.size();r++)   System.out.println(Inbox);
                        break;
                        }
                     
                        
                        else 
                            if("2".equals(userInput)){  //read the sent messages

                      ArrayList<Account> Sent=( ArrayList<Account> ) m.readObject(); 
                        for(int r = 0 ;r<Sent.size();r++)   System.out.println(Sent);
                              break;    
                            
                            }
                            else  {      response = in.readUTF();
                                   System.out.println(response);   break;}
                       
       
 case "2"://send a messege
                          ArrayList<Object>  usernames= (ArrayList<Object> ) m.readObject();   //read the the usernames and display all of them 
                          System.out.print("Select one from the Usernames : ");
                            
                          for(int r = 0 ;r<usernames.size();r++) {System.out.print(usernames.get(r)+", ");}
                          System.out.print("Send to : ");
                          
                          userInput = UserInput.readLine(); //read the selected username receiver and send it to the server
                          out.writeUTF(userInput);          
                           response = in.readUTF();                   //read the response from the server
                           if("countinue".equals(response)){// if the response is "countinue" write the title and the content
                            System.out.print("Title :  ");
                            userInput = UserInput.readLine();
                            out.writeUTF(userInput);
                             System.out.println("Content : ");
                            userInput = UserInput.readLine();
                            out.writeUTF(userInput);
                           
                              response = in.readUTF();
                             System.out.println("\n\n"+response); //read the response from  the server if the message is sent
                              break;
                           }
                          
                          
                          
                         response = in.readUTF();
                         System.out.println(response); //main menu                 
                         break;

                   default :    
                             response = in.readUTF();     //read the response from  the server if the user input is invalid
                             System.out.println(response); 
    break;
}

     }

catch (ClassCastException | ClassNotFoundException cce){System.out.println(cce.getMessage());}   
     
     
     }
 }  }}
 
