import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PubSubLogin extends Thread{
    int port;
    public PubSubLogin(int port) {
        this.port = port;
    }

    private ServerSubscriber workerList=null;
    public ServerSubscriber getWorkerList(){
		 return workerList;
	}
    
    @Override
    public void run(){

        try{
            ServerSocket serverSocket = new ServerSocket(port);
            OutputStream outPut;
            InputStream input;

            while(true){
                Socket clientSocket = serverSocket.accept();
                
                input = clientSocket.getInputStream();
                outPut = clientSocket.getOutputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                
                outPut.write("\n:::::::::::::::::::Welcome to Stock Auction System::::::::::::::::::: \n".getBytes(StandardCharsets.UTF_8));
    	        
    	        outPut.write("\nTo Login, Enter the Username and Password".getBytes(StandardCharsets.UTF_8));
    	        outPut.write("\nEnter Username and Password: ".getBytes(StandardCharsets.UTF_8));
    	        
    	        //Publisher--> UserName = pub and Password = pub
    	        //Subscriber--> UserName = sub and Password = sub
    	        
    	        String line;
    	        if((line = reader.readLine()) != null) {
    	        	 String[] tokens = line.split(" ");
    	        	 if(tokens.length==2) {
    	        		 String login = tokens[0];
    		             String password = tokens[1];
    		             if(login.equals("sub") && password.equals("sub")){
    		            	 System.out.println(login+" Subscriber Login Successfully!");
    		            	 
    		            	 System.out.println("S: Accepted connection from "+clientSocket);
    		                 ServerSubscriber Sworker = new ServerSubscriber(this,clientSocket);
    		                 workerList = Sworker;
    		                 Sworker.start();
    		             }
    		             
    		             if(login.equals("pub") && password.equals("pub")) {
    		            	 System.out.println(login+" Publisher Login Successfully!");

    		                 System.out.println("P: Accepted connection from "+clientSocket);
    		                 ServerPublisher Pworker = new ServerPublisher(clientSocket);
    		                 Pworker.start();
    		             }
    	        	 }
    	        }    

            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
