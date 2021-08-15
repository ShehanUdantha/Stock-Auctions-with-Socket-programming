import java.io.IOException;
import java.util.*;

public class Main {
	
	public static void main(String args[]){
		// TODO Auto-generated method stub
		
		// Milestone 1 : Create the CSV reader and populate datastructure

	    CSVReader csvreader = new CSVReader();    // CSVreader
	    csvreader.read();   //read CSV data 


	    // Milestone 2 : Create a server and accept 1 connection
	    
	    // Milestone 3 : Modify server to accept multiple connections (multi-threading)
	    
	    
	    String arg = args[0];  //get biding time period
	    int Minutes = Integer.parseInt(arg)*60; //minutes to seconds
	    
	    int port1 = 2021;
	    int port2 = 2022;

	    //Client port 2021
	    Client serverc = new Client(port1,Minutes); // Server
        serverc.start();  // Server starts running here.
        serverc.t1.start();  //start timer
        
        //Publisher && Subscriber port 2022
        PubSubLogin servers = new PubSubLogin(port2); // Server
        servers.start();  // Server starts running here.
        
        
        
	    


	}

}
