package parseHTML;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ParseHtml {
	
	public static String parseJSON(String source) throws IOException {
		// Connect to source HTML
		Document doc = Jsoup.connect(source).get();
		// Enable pretty printing
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JSONArray parentArray = new JSONArray();

		List<Element> article = doc.select("article").first().children();
		String heading = "";

		// Loop through article for tableOrH2
		for (Element tableOrH2 : article) {
			// If heading tag is h2 continue
			if (tableOrH2.tag().toString().equals("h2")) {
				heading = tableOrH2.text();
				// A way to skip the monitoring table
				if (heading.equals("Monitoring")) {
					break;
				} else {
					continue;
				}
			}
			// Create JSON object & array
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			// Loops through first item in rows add them to JSONArray
			for (Element row : tableOrH2.select("tbody").select("tr")) {
				Element tds = row.select("td").first();
				jsonArray.add(tds.text());
			}
			// Puts the h2 and array in JSONObject
			jsonObject.put(heading, jsonArray);
			// Adds object to another array
			parentArray.add(jsonObject);
		}
		return gson.toJson(parentArray);
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		System.out.println(parseJSON("https://github.com/egis/handbook/blob/master/Tech-Stack.md"));

	}

}
