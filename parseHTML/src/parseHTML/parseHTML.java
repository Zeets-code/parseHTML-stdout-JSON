package parseHTML;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class parseHTML {
	

	public static String parseJSON(String source) throws IOException{
		//Connect to source HTML
        Document doc = Jsoup.connect(source).get();
        
        JSONArray list = new JSONArray();

        List<Element> article = doc.select("article").first().children();
        String heading = "";
        //Loop through article for tableOrH2
        for (Element tableOrH2: article) {
        	//If heading tag is h2 continue
            if (tableOrH2.tag().toString().equals("h2")) {
                heading = tableOrH2.text();
                //A way to skip the monitoring table
                if(heading.equals("Monitoring") ) {
                	break;
                }else{
                continue;
                }
            }
            //Create JSON object & array
            JSONObject obj = new JSONObject();
            JSONArray arr = new JSONArray();
            //Loops through first item in rows add them to JSONArray
            for(Element row: tableOrH2.select("tbody").select("tr")) {
                Element tds = row.select("td").first();
                arr.add(tds.text());
            }
            //Puts the h2 and array in JSONObject
            obj.put(heading, arr);
            //Adds object to another array
            list.add(obj);
        }
        return list.toString();
	}



	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		System.out.println(parseJSON("https://github.com/egis/handbook/blob/master/Tech-Stack.md"));

			}
		  
	}


