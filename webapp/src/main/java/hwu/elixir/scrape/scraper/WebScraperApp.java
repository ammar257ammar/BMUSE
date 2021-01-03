package hwu.elixir.scrape.scraper;

import org.json.simple.JSONArray;

import hwu.elixir.scrape.exceptions.FourZeroFourException;
import hwu.elixir.scrape.exceptions.JsonLDInspectionException;
import hwu.elixir.scrape.exceptions.MissingHTMLException;
import hwu.elixir.scrape.exceptions.SeleniumException;
import hwu.elixir.scrape.scraper.ScraperUnFilteredCore;

public class WebScraperApp {

	public static void main(String[] args) throws FourZeroFourException, JsonLDInspectionException, MissingHTMLException, SeleniumException, Exception {
		
		String url = "http://localhost:8086/PSnpBind/protein/2c3i";
		
		//ScraperOutput outputType = ScraperOutput.JSONLD; 

		//WebScraper scraper = new WebScraper();
		ScraperUnFilteredCore scrapperUnfiltered = new ScraperUnFilteredCore();
		
		//JSONArray result = new JSONArray();

		//result = scraper.scrape(url, outputType);
		
		//System.out.println(result);
		
		//String resultUnfiltered = scrapperUnfiltered.scrapeUnfilteredMarkupAsJsonLDFromUrl(url);
		String resultUnfiltered = scrapperUnfiltered.scrapeUnfilteredMarkupAsNTriplesFromUrl(url);
		System.out.println(resultUnfiltered);
		
	}

}
