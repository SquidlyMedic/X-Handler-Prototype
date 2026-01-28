import xmlhandler.XmlHandler;

public class main {

	public static void main(String[] args) throws Exception
	{
		System.out.println("XML Handler Prototype");
		XmlHandler handler = new XmlHandler();
		System.out.println("---------------------------");
		System.out.println("Bus Stations:");
		handler.printBusStationList();
		System.out.println("\n---------------------------");
		System.out.println("Busses:");
		handler.printBusList();
		System.out.println("\n---------------------------");
	}

}
