package hwu.elixir.scrape.scraper;

import java.io.ByteArrayOutputStream;
import java.util.StringTokenizer;

import org.apache.any23.source.DocumentSource;
import org.apache.any23.source.StringDocumentSource;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hwu.elixir.scrape.exceptions.FourZeroFourException;
import hwu.elixir.scrape.exceptions.HtmlExtractorServiceException;
import hwu.elixir.scrape.exceptions.JsonLDInspectionException;
import hwu.elixir.scrape.exceptions.MissingContextException;
import hwu.elixir.scrape.exceptions.MissingHTMLException;
import hwu.elixir.utils.GetHTMLFromNode;

public class WebScraper extends ScraperCore {

	private static final Logger logger = LoggerFactory.getLogger(System.class.getName());

	public JSONArray scrape(String url) throws HtmlExtractorServiceException, FourZeroFourException,
			JsonLDInspectionException, MissingContextException, MissingHTMLException {

		if (url.endsWith("/") || url.endsWith("#"))
			url = url.substring(0, url.length() - 1);

		String html = GetHTMLFromNode.getHtml(url);		
		
		html = injectId(html, url);
		
		DocumentSource source = new StringDocumentSource(html, url);
		String n3 = getTriplesInN3(source);
		
		if (n3 == null) {
			logger.error("n3 is null");
			throw new HtmlExtractorServiceException(url);
		}

		Model model = processTriplesLeaveBlankNodes(n3);
		if (model == null) {
			logger.error("Model is null");
			throw new HtmlExtractorServiceException(url);
		}
			
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		Rio.write(model, stream, RDFFormat.NTRIPLES);
		String quads = new String(stream.toByteArray());

		return writeN3ToJSONArray(quads);
	}

	private JSONArray writeN3ToJSONArray(String n3) {
		JSONArray array = new JSONArray();
		StringTokenizer st = new StringTokenizer(n3, "\n");
		while (st.hasMoreTokens()) {
			array.add(st.nextToken());
		}
		return array;
	}

}