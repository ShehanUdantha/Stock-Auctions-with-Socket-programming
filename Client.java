import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Client extends Thread{
    int port;
    int minutes;
    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    private ArrayList<ServerClient> clientList = new ArrayList<>();
    
    public Client(int port,int minutes) {
        this.port = port;
        this.minutes = minutes;
    }

    @Override
    public void run(){

        try{
            serverSocket = new ServerSocket(port);

            while(true){
            	clientSocket = serverSocket.accept();
            	System.out.println("C: Accepted connection from "+clientSocket);
                ServerClient Cworker = new ServerClient(clientSocket);
                clientList.add(Cworker);
                Cworker.start();
            }
        }catch (IOException e){
        	System.out.println("BID Time's UP!");
        }

    }
   

    //Timer to closed connection after ending biding period
    Thread t1 = new Thread(new Runnable() {public void run(){

    	for(int i=0;i<=minutes;i++) {
    		try {
				Thread.sleep(1000);
				if(i==minutes) {
					serverSocket.close();
					for(ServerClient worker : clientList){
						worker.clientSocket.close();
	                }
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	   

    }});


}
