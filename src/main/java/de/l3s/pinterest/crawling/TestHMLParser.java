package de.l3s.pinterest.crawling;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.HtmlNode;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.TagNodeVisitor;
import org.htmlcleaner.XPatherException;

public class TestHMLParser {
	static Logger logger = Logger.getLogger(TestHMLParser.class.getName());
	static CleanerProperties props = new CleanerProperties();
	
	public TestHMLParser() {
		// set some properties to non-default values
		props.setTranslateSpecialEntities(true);
		props.setTransResCharsToNCR(true);
		props.setOmitComments(true);
	}
	
	
	public static void main (String[] args) {
		 TestHMLParser.processBoardPage("http://www.pinterest.com/jwmoz/take-me-there/");
	}
    
	/**
	 * Get data from pin pages
	 * @param pinurl
	 */
	public static void processPinPage(String pinurl) {
		// do parsing
		try {
			//e.g., http://pinterest.com/pin/116319602848921944/
			TagNode node = new HtmlCleaner(props).clean(
			    new URL(pinurl)
			);
			
			Object[] cmtList = node.evaluateXPath("//div[@class='commenterNameCommentText']");
			if(cmtList == null || cmtList.length < 1) {
			    return;
			}
			
			for (Object cmtWrapper : cmtList) {
				TagNode cmtWrapperNode = (TagNode)cmtWrapper;
				Object[] cmtContent = cmtWrapperNode.evaluateXPath("/p[@class='commentDescriptionContent']");
				Object[] cmtCreater = cmtWrapperNode.evaluateXPath("/div[@class='commenterWrapper']/a[@class='commentDescriptionCreator']");
				
				System.out.println("User: " + ((TagNode)cmtCreater[0]).getText());
				System.out.println("Content: " + ((TagNode)cmtContent[0]).getText());
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPatherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param boardurl
	 */
	public static void processBoardPage(String boardurl) {
		try {
			//e.g., http://www.pinterest.com/jwmoz/take-me-there/
			TagNode node = new HtmlCleaner(props).clean(
				    new URL(boardurl)
				);

			Object[] pinList = node.evaluateXPath("//div[@class='pinHolder']");	
			if(pinList == null || pinList.length < 1) {
			    return;
			}
			for (Object pinWrapper : pinList) {
				TagNode pinWrapperNode = (TagNode) pinWrapper;
				Object[] img = pinWrapperNode.evaluateXPath("//img");
				//assume only one pin inside the node
				String imgsrc = ((TagNode)img[0]).getAttributeByName("src");
				Object[] pin = pinWrapperNode.evaluateXPath("/a[@class='pinImageWrapper']");
				//assume only one pin inside the node
				String pinurl = ((TagNode)pin[0]).getAttributeByName("href");
				
				System.out.println(imgsrc);
				System.out.println(pinurl);
				
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPatherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
