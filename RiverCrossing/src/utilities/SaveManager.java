package utilities;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import model.ICrosser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
 
public class SaveManager {
 
    private static final String xmlFilePath = "saved.xml";
 
    public static void execute(GameState gameState) {
 
        try {
 
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
 
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
 
            Document document = documentBuilder.newDocument();
 
            // root element
            Element root = document.createElement("GameState");
            document.appendChild(root);
 
            Element leftCrossersElement = document.createElement("LeftCrossers");
            Element rightCrossersElement = document.createElement("RightCrossers");
            Element raftsElement = document.createElement("Rafts");
 
            root.appendChild(leftCrossersElement);
            root.appendChild(rightCrossersElement);
            root.appendChild(raftsElement);
            
            writeCrossersFromListToXMLElement(gameState.leftBank, leftCrossersElement, document);
            writeCrossersFromListToXMLElement(gameState.rightBank, rightCrossersElement, document);
            
			raftsElement.appendChild(document.createTextNode(String.valueOf(gameState.rafts)));
            
 
            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));
            transformer.transform(domSource, streamResult);
 
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

	private static void writeCrossersFromListToXMLElement(
			List<ICrosser> crossers, Element element, Document document) {
		for (ICrosser crosser : crossers) {
			 Element crosserElement = document.createElement("Crosser");
			 Element crosserClassName = document.createElement("ClassName");
			 crosserClassName.appendChild(document.createTextNode(crosser.getClass().getName()));
			 crosserElement.appendChild(crosserClassName);
			 Element canSail = document.createElement("CanSail");
			 canSail.appendChild(document.createTextNode(String.valueOf(crosser.canSail())));
			 crosserElement.appendChild(canSail);
			 Element eatinRank = document.createElement("EatinRank");
			 eatinRank.appendChild(document.createTextNode(String.valueOf(crosser.getEatingRank())));
			 crosserElement.appendChild(eatinRank);
			 Element weight = document.createElement("Weight");
			 weight.appendChild(document.createTextNode(String.valueOf(crosser.getWeight())));
			 crosserElement.appendChild(weight);
	         element.appendChild(crosserElement);
		}
	}

	
}
