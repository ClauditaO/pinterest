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
	public static void main (String[] args) {
		CleanerProperties props = new CleanerProperties();
		 
		// set some properties to non-default values
		props.setTranslateSpecialEntities(true);
		props.setTransResCharsToNCR(true);
		props.setOmitComments(true);
		
		// do parsing
		try {
			TagNode node = new HtmlCleaner(props).clean(
			    new URL("http://pinterest.com/pin/116319602848921944/")
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

}
