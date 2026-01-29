package xmlhandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;



public class XmlHandler 
{
	protected int stationIdIndex;  //Current available index for new station
	protected int busIdIndex;  //Current available index for new bus
	protected Document stations;
	protected Document busses;
	protected NodeList stationList;
	protected NodeList busList;
	
	public XmlHandler() throws Exception
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		stations = builder.parse(new File("resources/busstation.xml"));
		busses = builder.parse(new File("resources/busses.xml"));
		
		stations.getDocumentElement().normalize();
		busses.getDocumentElement().normalize();
		
		stationList = stations.getElementsByTagName("busStation");
		busList = busses.getElementsByTagName("bus");
		
		//Reading each element of the station list for testing only rn, will move
		
		//Reading each element of the bus list for testing only rn, will move
		
		
		stationIdIndex = getStationIdIndex();  //Method to find the current available index
		System.out.println("Current station index: " + stationIdIndex);
		busIdIndex = getBusIdIndex();  //Method to find the current available index
		System.out.println("Current bus idex: " + busIdIndex);
	}
	
	protected boolean saveXmlStations() throws Exception
	{
		removeWhitespaceNodes(stations.getDocumentElement());  //Removes white spaces with current DOM
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");  //Should fix the indent stupidity
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");  //See above
		DOMSource source = new DOMSource(stations);
		StreamResult result = new StreamResult(new File("resources/busstation.xml"));
		transformer.transform(source, result);
		return true;
	}
	protected boolean saveXmlBusses() throws Exception
	{
		removeWhitespaceNodes(busses.getDocumentElement());  //Removes white spaces with current DOM
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");  //Should fix the indent stupidity
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");  //See above
		DOMSource source = new DOMSource(busses);
		StreamResult result = new StreamResult(new File("resources/busses.xml"));
		transformer.transform(source, result);
		return true;
	}
	
	// Recursive method that walks the DOM tree and deletes any nodes that are whitespace only (tab, newline, etc)
	//Made because of an issue in testing where the lines would expand for every time the transformer wrote to the XML file
	private void removeWhitespaceNodes(Node node)
	{
		NodeList children = node.getChildNodes();  //Gets all children nodes
		for(int i = children.getLength() - 1; i >= 0; i--)  //Loops backward through children to avoid skipping problems
		{
			Node child = children.item(i);  //Gets current child node
			//tests if a text node and trims it to get only text
			if(child.getNodeType() == Node.TEXT_NODE && child.getTextContent().trim().isEmpty())
			{
				node.removeChild(child);   //if only white space, remove it
			}
			else if(child.hasChildNodes())  //Recursive call to traverse tree to get everything
			{
				removeWhitespaceNodes(child);
			}
			
		}
	}
	
	private int getStationIdIndex()
	{
		int maxId = -1;
		
		// Loops thru the XML file and finds the highest ID number
		for(int i = 0; i < stationList.getLength(); i++)
		{
			Element station = (Element) stationList.item(i);
			int stationId = Integer.parseInt(station.getAttribute("id"));
			if(stationId > maxId)
			{
				maxId = stationId;
			}
		}
		return maxId + 1;
	}
	private int getBusIdIndex()
	{
		int maxId = -1;
		
		// Loops thru the XML file and finds the highest ID number
		for(int i = 0; i < busList.getLength(); i++)
		{
			Element bus = (Element) busList.item(i);
			int stationId = Integer.parseInt(bus.getAttribute("id"));
			if(stationId > maxId)
			{
				maxId = stationId;
			}
		}
		return maxId + 1;
	}
	
	public void printBusList()
	{
		for(int i = 0; i < busList.getLength(); i++)
		{
			Element bus = (Element) busList.item(i);
					
			int busId = Integer.parseInt(bus.getAttribute("id"));
			String makemodel = bus.getElementsByTagName("makemodel").item(0).getTextContent();
			String type = bus.getElementsByTagName("type").item(0).getTextContent();
			int tankSize = Integer.parseInt(bus.getElementsByTagName("fuelsize").item(0).getTextContent());
			int fuelBurn = Integer.parseInt(bus.getElementsByTagName("fuelburn").item(0).getTextContent());
			int cruiseSpeed = Integer.parseInt(bus.getElementsByTagName("cruisespeed").item(0).getTextContent());
			System.out.println("ID " + busId + "\t" + makemodel + ":\tType: " + 
			type + "\tTank: " + tankSize + " gallons\t @ " + fuelBurn + "gal/hr\t @ " + cruiseSpeed + "mph");
		}
	}
	public void printBusStationList()
	{
		for(int i = 0; i < stationList.getLength(); i++)
		{
			Element station = (Element) stationList.item(i);
			
			int stationId = Integer.parseInt(station.getAttribute("id"));
			String name = station.getElementsByTagName("name").item(0).getTextContent();
			double latitude = Double.parseDouble(station.getElementsByTagName("latitude").item(0).getTextContent());
			double longitude = Double.parseDouble(station.getElementsByTagName("longitude").item(0).getTextContent());
			System.out.println("ID " + stationId + "\t" + name + ": \tLat: " + latitude + "  /  Long: " + longitude);
		}
	}
	
	public boolean addBusStation(String inName, double inLatitude, double inLongitude) throws Exception
	{
		// Updating the DOM tree only so far
		Element newStation = stations.createElement("busStation");
		newStation.setAttribute("id", String.valueOf(stationIdIndex));
		Element name = stations.createElement("name");
		name.setTextContent(inName);
		Element latitude = stations.createElement("latitude");
		latitude.setTextContent(String.valueOf(inLatitude));
		Element longitude = stations.createElement("longitude");
		longitude.setTextContent(String.valueOf(inLongitude));
		
		newStation.appendChild(name);
		newStation.appendChild(latitude);
		newStation.appendChild(longitude);
		
		stations.getDocumentElement().appendChild(newStation);
		
		// Now refresh the NodeList
		stationList = stations.getElementsByTagName("busStation");
		
		// Export/save to the XML file
		saveXmlStations();
		
		stationIdIndex++;  //Updates the working ID counter
		
		return true;
	}
	
	public boolean removeBusStation(int stationId) throws Exception
	{
		for(int i = 0; i < stationList.getLength(); i++)  //Loops through each element in DOM
		{
			Element station = (Element) stationList.item(i);  //Looks at one element
			
			if(Integer.parseInt(station.getAttribute("id")) == stationId) //If it matches the query then delete it
			{
				stations.getDocumentElement().removeChild(station);
				saveXmlStations();  //Updates the XML file
				return true;
			}
		}
		
		return false;
	}
}
