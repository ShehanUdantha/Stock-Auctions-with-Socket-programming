import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ServerPublisher extends Thread{

    private final Socket PubSocket;
    
    //Get Socket
    public ServerPublisher(Socket PubSocket){
        this.PubSocket = PubSocket;
    }

	@Override
    public void run(){
    	
        try{
	       handlePubSocket();
        }catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
    
    
    private void handlePubSocket() throws IOException, InterruptedException {   	
        
    	String PubSymbol;
        String Snumber;
        String Profit;
        
        //create input stream to get data from client
        InputStream input = PubSocket.getInputStream();
        //create output stream to send data to client
        OutputStream outPut = PubSocket.getOutputStream();

        //open buffered reader for reading data from client
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        outPut.write("\nEnter Your Company Name: ".getBytes(StandardCharsets.UTF_8));
        String Uname = reader.readLine();

        while(true){
        	
        	outPut.write("\nTo Publish Monthly Profit, Enter the symbol of the item or To exit, Enter 'quit'".getBytes(StandardCharsets.UTF_8));
        	outPut.write("\nEnter Symbol: ".getBytes(StandardCharsets.UTF_8));
        	PubSymbol = reader.readLine();
        	
        	if ("quit".equalsIgnoreCase(PubSymbol)){
                break;
            }
        	
        	 outPut.write("Enter Security number: ".getBytes(StandardCharsets.UTF_8));
	         Snumber = reader.readLine();
            
	         outPut.write("New Profit: ".getBytes(StandardCharsets.UTF_8));
	         Profit = reader.readLine();
	         CSVReader obj2 = new CSVReader();        
	         String msg2 = obj2.publishProfit(PubSymbol,Snumber,Profit)+"\n";
	         outPut.write(msg2.getBytes(StandardCharsets.UTF_8));
            
	         //Publisher profit updates put in to the PubSub Class HashMap
	         PubSub obj3 = new PubSub();
		     obj3.listUpdate(PubSymbol,Snumber,Profit); 
		     
		     //call PubSub getData method to push notification to the subscriber
		     PubSub obj5 = new PubSub();
		     obj5.getData();



        }
        
        outPut.flush();
        input.close();
        outPut.close();
        reader.close();
        PubSocket.close();

    }

}
