import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.text.*;

public class ServerSubscriber extends Thread{

    private final Socket Subscribersock;
    private final PubSubLogin server;  
    private OutputStream outPut;
    
    //create default constructor
    public ServerSubscriber() {
		this.Subscribersock = new Socket();
		this.server = null;}

    //Get Socket
    public ServerSubscriber(PubSubLogin server,Socket Subscribersock){
    	this.server = server;
        this.Subscribersock = Subscribersock;
    }
	          
    @Override
    public void run(){
    	
        try{
           //Thread.sleep(5000);
	       handlePubSocket();
        }catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
 
	private void handlePubSocket() throws IOException, InterruptedException {
	    	
	    	String Symbol;
	        String Snumber;
	        String Profit;	        
	        ServerSubscriber worker = server.getWorkerList();
	        
	        //create input stream to get data from Publisher/Subscriber
	        InputStream input = Subscribersock.getInputStream();
	        //create output stream to send data to Publisher/Subscriber
	        outPut = Subscribersock.getOutputStream();
	
	        //open buffered reader for reading data from Publisher/Subscriber
	        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

	        	outPut.write("\nPress Number to Subscribe".getBytes(StandardCharsets.UTF_8));
	        	outPut.write("\nPress 1 - to Subscribe Profits ".getBytes(StandardCharsets.UTF_8));
        		outPut.write("\nPress 2 - to Subscribe Bid ".getBytes(StandardCharsets.UTF_8));
        		outPut.write("\nEnter Number: \n".getBytes(StandardCharsets.UTF_8));
        		String Num = reader.readLine();
        		
	        	if(Num.equals("1")) {
	        		outPut.write("\nEnter the Symbols to Subscribe Profit".getBytes(StandardCharsets.UTF_8));
            		outPut.write("\nEnter 3 Symbols to Subscribe: ".getBytes(StandardCharsets.UTF_8));
            		
            		String GetSymbol = reader.readLine();
            		
            		//EX: PRFT Symbol Symbol Symbol
        	        String[] token = GetSymbol.split(" "); 
        	        String Valu = token[0];
        	        String SYM1 = token[1];
        	        String SYM2 = token[2];
        	        String SYM3 = token[3];
        	        
        	        
        	        //Call checkSymbol method to validate the Symbols
            	    CSVReader obj1 = new CSVReader();
            	    //If all symbols are correct return 0
            	    String msg3 = obj1.checkSymbol(SYM1,SYM2,SYM3);

            	    if("0".equalsIgnoreCase(msg3)) {
            	    	outPut.write("Subscribe: ".getBytes(StandardCharsets.UTF_8));
	            	    outPut.write("0".getBytes(StandardCharsets.UTF_8));

	            	    outPut.write("\nSubscribed List of Profit:\n".getBytes(StandardCharsets.UTF_8));
	            	    
	            	    PubSub obj2 = new PubSub();
	            	    obj2.getSubSymbols(worker,SYM1,SYM2,SYM3);
	            	    
	            	    //To show Subscription current profit
	            	    CSVReader bj5 = new CSVReader();
	            	    String first = bj5.getProft(SYM1);
	            	    String second = bj5.getProft(SYM2);
	            	    String third = bj5.getProft(SYM3);
	            	    
	            	    if(first != null) {
	            	    	String msg7 = SYM1 + " "+" PRFT "+" " + first+"\n";
	            	    	send(worker,msg7);
	            	    }
	            	    if(second != null) {
	            	    	String msg7 = SYM2 + " "+" PRFT "+" " + second+"\n";
	            	    	send(worker,msg7);
	            	    }
	            	    if(third != null) {
	            	    	String msg7 = SYM3 + " "+" PRFT "+" " + third+"\n";
	            	    	send(worker,msg7);
	            	    }
	            	    
	            	    outPut.flush();
  
            	    }else {
            	    	outPut.write("-1".getBytes(StandardCharsets.UTF_8));
            	    }
	        	}else if(Num.equals("2")) {
    	        		
    	        		outPut.write("\nEnter the Symbols to Subscribe Bid".getBytes(StandardCharsets.UTF_8));
                		outPut.write("\nEnter 3 Symbols to Subscribe: ".getBytes(StandardCharsets.UTF_8));
                		
                		String Getsymbol = reader.readLine();		
    		            		
                		//EX: BID Symbol Symbol Symbol
            	        String[] tokn = Getsymbol.split(" "); 
            	        String BValu = tokn[0];
            	        String BSYM1 = tokn[1];
            	        String BSYM2 = tokn[2];
            	        String BSYM3 = tokn[3];    		
    			            		
            	        //Call checkSymbol method to validate the Symbols
                	    CSVReader obj2 = new CSVReader();
                	    //If all symbols are correct return 0
                	    String msg5 = obj2.checkSymbol(BSYM1,BSYM2,BSYM3);    	    
    	    	            	    
                	    if("0".equalsIgnoreCase(msg5)) {
                	    	outPut.write("Subscribe: ".getBytes(StandardCharsets.UTF_8));
    	            	    outPut.write("0".getBytes(StandardCharsets.UTF_8));
    	            	    
    	            	    outPut.write("\nSubscribed List of BID:\n".getBytes(StandardCharsets.UTF_8));
    	            	    
    	            	    PubSub obj3 = new PubSub();
    	            	    obj3.getSubBid(worker,BSYM1,BSYM2,BSYM3);
    	            	    
    	            	    
    	            	    //To show Subscription current price
    	            	    CSVReader bj4 = new CSVReader();
    	            	    String first = bj4.getItem(BSYM1);
    	            	    String second = bj4.getItem(BSYM2);
    	            	    String third = bj4.getItem(BSYM3);
    	            	    
    	            	    if(first != null) {
    	            	    	String msg8 = BSYM1 + " "+" BID "+" " + first+"\n";
    	            	    	send(worker,msg8);
    	            	    }
    	            	    if(second != null) {
    	            	    	String msg8 = BSYM2 + " "+" BID "+" " + second+"\n";
    	            	    	send(worker,msg8);
    	            	    }
    	            	    if(third != null) {
    	            	    	String msg8 = BSYM3 + " "+" BID "+" " + third+"\n";
    	            	    	send(worker,msg8);
    	            	    }
    	            	    
    	            	    outPut.flush();
 
                	    }else {
    	            	    outPut.write("-1".getBytes(StandardCharsets.UTF_8));
                	    }    	    

    	        	}

	}

	
	//To send output to the Subscriber
	public void send(ServerSubscriber key,String outMsg) throws IOException {
        if(outMsg!=null) {
        	key.outPut.write(outMsg.getBytes(StandardCharsets.UTF_8));
        }
    }

}



