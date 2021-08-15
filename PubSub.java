import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PubSub {

	private static HashMap<String,String> WorkerList = new HashMap<String,String>();
	private static Map<ServerSubscriber, ArrayList<String>> hm = new HashMap<ServerSubscriber, ArrayList<String>>();
	private static HashMap<String,String> ClientList = new HashMap<String,String>();
	private static Map<ServerSubscriber, ArrayList<String>> cl = new HashMap<ServerSubscriber, ArrayList<String>>();
	
	//Client bid updates put in to the HashMap
	public void bidUpdates(String Symbol, String bid) {
		ClientList.put(Symbol,bid);
	}

	
	//Publisher profit updates put in to the HashMap
	public void listUpdate(String PubSymbol,String Snumber,String Profit) {
		
		CSVReader obj1 =new CSVReader();
		String get = obj1.publishProfit(PubSymbol, Snumber, Profit);
		if(get.equals("0")) {
			WorkerList.put(PubSymbol,Profit);
		}
	}
	

	//Add Profit Subscriber with subscribed symbols to the HashMap
	public static void getSubSymbols(ServerSubscriber workerList,String sYM1, String sYM2, String sYM3) {
		// TODO Auto-generated method stub

		if(hm.containsKey(workerList)) {
			ArrayList<String> values;
			values = hm.get(workerList);
			values.add(sYM1);
			values.add(sYM2);
			values.add(sYM3);
			hm.put(workerList, values);
		}else {
			ArrayList<String> values = new ArrayList<String> ();
			values.add(sYM1);
			values.add(sYM2);
			values.add(sYM3);
			hm.put(workerList, values);
			values =null;
		}

	}
	
	//Add Bid Subscriber with subscribed symbols to the HashMap
	public void getSubBid(ServerSubscriber worker2, String bSYM1, String bSYM2, String bSYM3) {
		// TODO Auto-generated method stub
		if(cl.containsKey(worker2)) {
			ArrayList<String> val;
			val = cl.get(worker2);
			val.add(bSYM1);
			val.add(bSYM2);
			val.add(bSYM3);
			cl.put(worker2, val);
		}else {
			ArrayList<String> val = new ArrayList<String> ();
			val.add(bSYM1);
			val.add(bSYM2);
			val.add(bSYM3);
			cl.put(worker2, val);
			val =null;
		}
		
	}

	//Compare two map to check profit update symbol and subscribe symbol
	public void getData() throws IOException {
		// TODO Auto-generated method stub
		String msg4=null;
		for(Map.Entry<ServerSubscriber, ArrayList<String>> entry: hm.entrySet()) {
			ServerSubscriber key = entry.getKey();
			ArrayList<String> a = hm.get(key);
			for(String s: a) {
				 for(Map.Entry<String, String> entry2: WorkerList.entrySet()) {
					 String key2 = entry2.getKey();
					 if(s.equals(key2)) {
						 msg4= key2 + " "+" PRFT "+" " + entry2.getValue()+"\n";
						 ServerSubscriber b = new ServerSubscriber();
					     b.send(key,msg4);
					 }
				 }
			}

		}

	}
	
	//Compare two map to check Bid update symbol and subscribe symbol
	public void getCData() throws IOException {
		
    	for(Map.Entry<ServerSubscriber, ArrayList<String>> entry3: cl.entrySet()) {
    		ServerSubscriber key3 = entry3.getKey();
    		ArrayList<String> b = cl.get(key3);
    		for(String x: b) {
    			for(Map.Entry<String, String> entry4: ClientList.entrySet()) {
    				String key4 = entry4.getKey();
    				if(x.equals(key4)) {
    					String msg7= key4 + " "+" BID "+" " + entry4.getValue()+"\n";
    					ServerSubscriber c = new ServerSubscriber();
					     c.send(key3,msg7);
    				 }
    			}
    		}

    	}
	}
	
	public static HashMap<String, String> getPubList(){
        return WorkerList;
    }
	
	public static Map<ServerSubscriber, ArrayList<String>> getSubList(){
        return hm;
    }

	public static HashMap<String, String> getClList(){
        return ClientList;
    }
	
	public static Map<ServerSubscriber, ArrayList<String>> getCSubList(){
        return cl;
    }


	
}