import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.text.*;

public class ServerClient extends Thread{

    final Socket clientSocket;

    //Get Client Socket
    public ServerClient(Socket clientSocket){
        this.clientSocket = clientSocket;
    }
    
        
    @Override
    public void run(){
    	
        try{
	       //Thread.sleep(5000);
	       handleClientSocket();
        }catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
    
    
    private void handleClientSocket() throws IOException, InterruptedException {   	
        
    	String Symbol;
        String newprice;
        
        //create input stream to get data from client
        InputStream input = clientSocket.getInputStream();
        //create output stream to send data to client
        OutputStream outPut = clientSocket.getOutputStream();

        //open buffered reader for reading data from client
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
    
        
        outPut.write("\n:::::::::::::::::::Welcome to Stock Auction System::::::::::::::::::: \n".getBytes(StandardCharsets.UTF_8));
        outPut.write("\nEnter Your Name: ".getBytes(StandardCharsets.UTF_8));
        String Uname = reader.readLine();
        
        //To generate unique ID
        //create random class
        Random r = new Random();
        //create hash set to store only unique number
        HashSet<Integer> set = new HashSet<>();
        
        while(true){
        	
            while(set.size()<1) {
        		//Generate random number and store it hash set
        		int Rnumber = r.nextInt(999)+1000;
        		set.add(Rnumber);
        	}
        	
        	int getRnumber=0;
        	//get hash set values
        	for(int randomNumbers: set) {
        		getRnumber=randomNumbers;
        	}
        	
        	
        	outPut.write("\nTo bit, Enter the symbol of the item or To exit, Enter 'quit'".getBytes(StandardCharsets.UTF_8));
        	outPut.write("\nEnter Symbol: ".getBytes(StandardCharsets.UTF_8));
        	Symbol = reader.readLine();
        	
        	if ("quit".equalsIgnoreCase(Symbol)){
                break;
            }
        	
            //Create object of the CSVReader Class
            CSVReader obj1 = new CSVReader();
            //Call getItem Method to Get Current Price of the Symbol
            String msg1 = "Current Price: "+obj1.getItem(Symbol)+"\n";
            //Sent to the Client
            outPut.write(msg1.getBytes(StandardCharsets.UTF_8));
            
            //Update Price
            outPut.write("Enter NewPrice: ".getBytes(StandardCharsets.UTF_8));
            newprice = reader.readLine();
            CSVReader obj2 = new CSVReader();
            String msg2 = "New Price: "+obj2.updatePrice(Symbol, newprice)+"\n";
            outPut.write(msg2.getBytes(StandardCharsets.UTF_8));
            
            //Client bid updates put in to the PubSub Class HashMap
            PubSub obj4 = new PubSub();
            obj4.bidUpdates(Symbol, newprice);
            
            //call PubSub getCData method to push notification to the subscriber
            PubSub obj5 = new PubSub();
            obj5.getCData();
            
            //write details
            write(getRnumber,Uname,Symbol,newprice);
            
        }
        
        outPut.flush();
        input.close();
        outPut.close();
        reader.close();
        clientSocket.close();

    }
    
    //Get log.txt File
    protected static String LogFile = "log.txt";
	
    //To Store Clients Updates
    public static void write(int getRnumber,String Uname, String Symbol, String price) throws IOException {
   	 	
    	//create current time
        TimeZone time = TimeZone.getTimeZone("IST");
        Date date = new Date();
        DateFormat df = new SimpleDateFormat ("yyyy.mm.dd hh:mm:ss ");
        df.setTimeZone(time);
        String currentTime = df.format(date);
        
        //write log file
        FileWriter fWriter = new FileWriter(LogFile, true);
        fWriter.write("ID-->"+getRnumber+" "+" UserName-->"+Uname + " " +" Symbol-->"+ Symbol + " " +" Updated Price-->"+ price + " " +" Updated Time-->"+ currentTime +"\n");
        fWriter.flush();
        fWriter.close();
    }

}
