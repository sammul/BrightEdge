/**
* This Assignment implements a a robust text scraper that will connect to a page on www.walmart.com and return results about a given keyword. There are two queries that will be performed:
* Query 1: Total number of results
* Given a keyword, such as "digital camera", return the total number of results found.
* Query 2: Result Object
* Given a keyword (e.g. "digital cameras") and page number (e.g. "1"), return the results in a result object and then print results on screen. For each result, return the following information:
* Title/Product Name (e.g. "Samsung TL100 Digital Camera")
* Price of the product
* For "digital cameras", there should be either 16 or 32 results that are returned for page 1. (You can work with either number of results for the assignment)
*  
* @author Xia Wu
* @Time 10/13/2014
*
*/

import java.io.*; 
import java.net.*; 
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WalmartAssignment {
	public static void main(String[] args){
		URL url;
	    InputStream is = null;
	    BufferedReader br;
	    String line;
	    int queryType = 1;
	    
	    //Build the query url
	    String queryadd = "http://www.walmart.com/search/?query=";
	    if(args.length>=1){
	    	queryType = 1;
	    	String key = args[0];
	    	String[] keywords = key.split(" ");
	    	if(keywords.length==0){
	    		System.out.println("Illegal Input Keywords");
	    		return;
	    	}
	    	queryadd += keywords[0];
	    	for(int i=1; i<keywords.length; i++)
	    		queryadd += "%20"+keywords[i];
	    }
	    if(args.length==2){
	    	queryType = 2;
	    	//with page
	    	String pagenum = args[1];
	    	queryadd += "&page="+pagenum;
	    }
	
	    try {
	        url = new URL(queryadd);
	        is = url.openStream();  // throws an IOException
	        br = new BufferedReader(new InputStreamReader(is));
	        String fulltext = "";
	
	        while ((line = br.readLine()) != null) {
	            fulltext += line;
	        }
	        //Use jsoup to build the document
	        Document doc = Jsoup.parse(fulltext);
	        
	        if(queryType==1)//Query Type 1
	        	System.out.println(getResultNumber(doc));
	        else{ //Query Type 2
	        	ArrayList<Result> rarr = getResults(doc);
	        	for(Result r: rarr){
	        		System.out.println(r.title+": "+r.price);
	        	}
	        }
	    } catch (MalformedURLException mue) {
	         mue.printStackTrace();
	    } catch (IOException ioe) {
	         ioe.printStackTrace();
	    } finally {
	        try {
	            if (is != null) is.close();
	        } catch (IOException ioe) {
	            // nothing to see here
	        }
	    }
	}
	/**
	 * Get total number of the results from the query
	 * @param object of Document
	 * @return total number of the results
	 */
	public static String getResultNumber(Document doc){
		Element searchContainer = doc.getElementById("search-container");
        Element resultSum = searchContainer.getElementsByClass("result-summary-container").first();
        String text = resultSum.text();
        String[] tparts = text.split(" ");
        return tparts[3];
	}
	
	/**
	 * Get an ArrayList of the results from the query
	 * @param object of Document
	 * @return ArrayList of the results found
	 */
	public static ArrayList<Result> getResults(Document doc){
		ArrayList<Result> res = new ArrayList<Result>();
		Element tileContainer = doc.getElementById("tile-container");
		for(Element e: tileContainer.children()){
			if(e.hasAttr("data-item-id")){
				String title = "";
				String price = "";
				Element titleE = e.getElementsByClass("js-product-title").first();
				title = titleE.text();
				Element priced = e.getElementsByClass("tile-row").first();
				price += priced.text();
				res.add(new Result(title,price));
			}
		}
		return res;
	}
}

class Result{
	String title;
	String price;
	Result(String t, String p){
		title = t;
		price = p;
	}
}
