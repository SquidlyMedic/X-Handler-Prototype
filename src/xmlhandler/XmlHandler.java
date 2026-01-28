package xmlhandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.File;



public class XmlHandler 
{
	protected int stationIdIndex;  //Current available index for new station
	protected int busIdIndex;  //Current available index for new bus
	protected NodeList stationList;
	protected NodeList busList;
	
	public XmlHandler() throws Exception
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document stations = builder.parse(new File("resources/busstation.xml"));
		Document busses = builder.parse(new File("resources/busses.xml"));
		
		stations.getDocumentElement().normalize();
		busses.getDocumentElement().normalize();
		
		stationList = stations.getElementsByTagName("busStation");
		busList = busses.getElementsByTagName("bus");
		
		//Reading each element of the station list for testing only rn, will move
		for(int i = 0; i < stationList.getLength(); i++)
		{
			Element station = (Element) stationList.item(i);
			
			int stationId = Integer.parseInt(station.getAttribute("id"));
			String name = station.getElementsByTagName("name").item(0).getTextContent();
			double latitude = Double.parseDouble(station.getElementsByTagName("latitude").item(0).getTextContent());
			double longitude = Double.parseDouble(station.getElementsByTagName("longitude").item(0).getTextContent());
			System.out.println("Station " + stationId + " ; " + name + " ; Lat: " + latitude + " Long: " + longitude);
		}
		//Reading each element of the bus list for testing only rn, will move
		for(int i = 0; i < busList.getLength(); i++)
		{
			Element bus = (Element) busList.item(i);
					
			int busId = Integer.parseInt(bus.getAttribute("id"));
			String makemodel = bus.getElementsByTagName("makemodel").item(0).getTextContent();
			String type = bus.getElementsByTagName("type").item(0).getTextContent();
			int tankSize = Integer.parseInt(bus.getElementsByTagName("fuelsize").item(0).getTextContent());
			int fuelBurn = Integer.parseInt(bus.getElementsByTagName("fuelburn").item(0).getTextContent());
			int cruiseSpeed = Integer.parseInt(bus.getElementsByTagName("cruisespeed").item(0).getTextContent());
			System.out.println("Station " + busId + " ; " + makemodel + " ; type: " + 
			type + " tanksize: " + tankSize + " fuelburn: " + fuelBurn + " cruisespeed: " + cruiseSpeed);
		}
		
		stationIdIndex = getStationIdIndex();  //Method to find the current available index
		System.out.println("Current station index: " + stationIdIndex);
		busIdIndex = getBusIdIndex();  //Method to find the current available index
		System.out.println("Current bus idex: " + busIdIndex);
	}
	
	private int getStationIdIndex()
	{
		int maxId = -1;
		
		
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
	
//	public boolean XMLBusStationWrite(String station, double latitude, double longitude) throws Exception
//	{
//		
//		
//		return true;
//	}
}
