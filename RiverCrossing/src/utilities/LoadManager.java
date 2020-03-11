package utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import model.ICrosser;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import controller.GameEngine;

public class LoadManager {

	public static void main(String[] args) {
		execute();
	}
	public static final String xmlFilePath = "saved.xml";

	public static void execute() {
		List<ICrosser> leftCrossers = new ArrayList<ICrosser>();
		List<ICrosser> rightCrossers = new ArrayList<ICrosser>();
		int rafts = 0;
		try {

			File fXmlFile = new File(xmlFilePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			populateList(leftCrossers, "LeftCrossers", doc);
			populateList(rightCrossers, "RightCrossers", doc);
			rafts = Integer.parseInt(doc.getElementsByTagName("Rafts").item(0).getTextContent());
			GameEngine.getInstance().setGameState(new GameState(rafts, leftCrossers, rightCrossers));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private static void populateList(List<ICrosser> list, String xmlTag, Document doc) throws ClassNotFoundException, DOMException, InstantiationException, IllegalAccessException {
		Element crossers = (Element) doc.getElementsByTagName(xmlTag).item(0);
		NodeList nList = crossers.getElementsByTagName("Crosser");
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				Class<ICrosser> CrosserClass = (Class<ICrosser>) Class.forName(eElement.getElementsByTagName("ClassName")
								.item(0).getTextContent());
				list.add(CrosserClass.newInstance());
			}
		}
	}
}
