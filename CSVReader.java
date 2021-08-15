import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;


public class CSVReader {
	//Get CSV file
    private String filename = "stocks.csv";
  
    private static HashMap<String,String> item_map = new HashMap<String,String>();
    private static HashMap<String,String> item_map1 = new HashMap<String,String>();
    private static HashMap<String,String> item_map2 = new HashMap<String,String>();
    
    public void read(){

    	// TODO : Implement method to read CSV file

        try{
            FileReader freader = new FileReader(filename); 
            BufferedReader breader = new BufferedReader(freader);
            
            String [] tokens;
            breader.readLine();
            
            //Read CSV file line by line removing ',' and assign to tokens
            for(String line = breader.readLine(); line != null; line = breader.readLine()){
                tokens = line.split(",");
                String Symbol = tokens[0];
                String price = tokens[1]; 
                String security = tokens[2];
                String profit = tokens[3];
                
                //Assign key and values to HashMap
                item_map.put(Symbol,price); 
                item_map1.put(Symbol,security);
                item_map2.put(Symbol,profit);

            }
            
            if(freader != null) freader.close();
            if(breader != null) breader.close(); 
      
        }catch(Exception e){ 
            System.out.println(e);
        }
       
    }
   
    //To Get Current price of the Symbol
    public static String getItem(String Symbol){
    	String Cprice;
    	
        if(item_map.get(Symbol) != null) {
        	return Cprice = item_map.get(Symbol);
        }else {
        	return Cprice = "-1";
        }
    }
    
    //To Update Price of the Symbol
    public static String updatePrice(String Symbol,String Price){
    	String sent;
    	String getCprice = item_map.get(Symbol);
    	
    	if(getCprice != null){
    		if(Float.parseFloat(Price)>Float.parseFloat(getCprice)) {
    			item_map.replace(Symbol, Price);
                sent =  item_map.get(Symbol);
    		}else{
                sent = "-2";
            }
    			
        }else{
            sent = "null";
        }
		return sent;
    	
     }

    //To Update Profit of the Symbol
    public static String publishProfit(String Symbol,String Snumber, String profit) {
    	String response=null;
    	String check = item_map.get(Symbol);
    	String checkCode = item_map1.get(Symbol);

    	if(check != null) {
    		if(checkCode.equals(Snumber)) {
        		item_map2.replace(Symbol, profit);
        		item_map2.get(Symbol);
        		return response = "0";
	        }else {
	        	return response = "-1";        	
	        }
    	}else {
    		return response = "-1";
    	}
        

    }
    
    //To Get Current Profit of the Symbol
    public static String getProft(String Symbol){
    	String Cprofit;
    	
        if(item_map2.get(Symbol) != null) {
        	return Cprofit = item_map2.get(Symbol);
        }else {
        	return Cprofit = "-1";
        }
    }

    //To Check Input Symbol valid or Not
	public static String checkSymbol(String sYM1, String sYM2, String sYM3) {
		
		boolean isKeyExit1 = item_map2.containsKey(sYM1);
		boolean isKeyExit2 = item_map2.containsKey(sYM2);
		boolean isKeyExit3 = item_map2.containsKey(sYM3);
		
		String checkKey=null;
		if(isKeyExit1 == true) {
			if(isKeyExit2 == true) {
				if(isKeyExit3 == true) {
					return checkKey  = "0";
				}else {
					return checkKey  = "-1";
				}
				
			}else {
				return checkKey  = "-1";
			}

		}else {
			return checkKey  = "-1";
		}
		
	}    

}

