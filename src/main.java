import java.util.Scanner;

import xmlhandler.XmlHandler;

public class main {

	public static void main(String[] args) throws Exception
	{
		System.out.println("XML Handler Prototype");
		XmlHandler handler = new XmlHandler();
		Scanner scanner = new Scanner(System.in);
		
		int userIn = -1;
		
		do
		{
			System.out.println("\n---------------------------");
			System.out.println("Select option...");
			System.out.println("---------------------------");
			System.out.println("1: List Busses");
			System.out.println("2. List Bus Stations");
			System.out.println("3. Add Bus");
			System.out.println("4. Add Bus Station");
			System.out.println("5. Remove Bus");
			System.out.println("6. Remove Bus Station");
			System.out.println("\n0. Exit");
			
			System.out.print("\n\nInput: ");
			userIn = Integer.parseInt(scanner.nextLine());
			
			switch(userIn)
			{
				case 1:
					System.out.println("\n---------------------------");
					System.out.println("Busses:");
					handler.printBusList();
					break;
				case 2:
					System.out.println("\n---------------------------");
					System.out.println("Bus Stations:");
					handler.printBusStationList();
					break;
				case 3:
					System.out.println("\n---------------------------");
					System.out.println("Add Bus:");
					System.out.print("*Enter make and model: ");
					String inMakemodel = scanner.nextLine();
					System.out.print("*Enter type (City OR Long Distance): ");
					String inType = scanner.nextLine();
					System.out.print("*Enter fuel tank size (in gallons): ");
					int inFuelsize = Integer.parseInt(scanner.nextLine());
					System.out.print("*Enter fuel burn rate per hour (in gallons): ");
					int inFuelburn = Integer.parseInt(scanner.nextLine());
					System.out.print("*Enter cruise speed in MPH: ");
					int inCruisespeed = Integer.parseInt(scanner.nextLine());
					
					if(handler.addBus(inMakemodel, inType, inFuelsize, inFuelburn, inCruisespeed))
						System.out.println("\nBus successfully created!");
					else
						System.out.println("\nBus creation error!");
					break;
				case 4:
					System.out.println("\n---------------------------");
					System.out.println("Add Bus Station:");
					System.out.print("*Enter name: ");
					String inName = scanner.nextLine();
					System.out.print("*Enter latitude: ");
					double inLatitude = Double.parseDouble(scanner.nextLine());
					System.out.print("*Enter longitude: ");
					double inLongitude = Double.parseDouble(scanner.nextLine());
					
					if(handler.addBusStation(inName, inLatitude, inLongitude))
						System.out.println("\nBus Station successfully created!");
					else
						System.out.println("\nBus Station creation error!");
					break;
				case 5:
					System.out.println("\n---------------------------");
					System.out.println("Remove Bus:");
					System.out.print("*Enter bus ID to remove: ");
					int inBusId = Integer.parseInt(scanner.nextLine());
					System.out.print("\nAre you sure you want to remove bus with an ID of " + inBusId + "? (y/n) ");
					if(scanner.nextLine().equals("y"))
					{
						System.out.println("Removing bus...");
						if(handler.removeBus(inBusId))
							System.out.println("\nBus removed successfully!");
						else
							System.out.println("\nBus removal error!");
					}
					else
						System.out.println("Bus removal abandonded");
					break;
				case 6:
					System.out.println("\n---------------------------");
					System.out.println("Remove Bus:");
					System.out.print("*Enter Bus Station ID to remove: ");
					int inBusStationId = Integer.parseInt(scanner.nextLine());
					System.out.print("\nAre you sure you want to remove bus station with an ID of " + inBusStationId + "? (y/n) ");
					if(scanner.nextLine().equals("y"))
					{
						System.out.println("Removing bus station...");
						if(handler.removeBusStation(inBusStationId))
							System.out.println("\nBus Station removed successfully!");
						else
							System.out.println("\nBus Station removal error!");
					}
					else
						System.out.println("Bus Station removal abandonded");
					break;
				case 0:
					break;
				default:
					System.out.println("Unexpected value entered, please try again.");
			}
			
		}while(userIn != 0);
		
		System.out.println("---------------------------");
		System.out.println("Bus Stations:");
		handler.printBusStationList();
		System.out.println("\n---------------------------");
		System.out.println("Busses:");
		handler.printBusList();
		System.out.println("\n---------------------------");
		
		System.out.println("Adding station");
		System.out.println(handler.addBusStation("test", 0.111111, 2.222222222));
		System.out.println("removing station 3");
		System.out.println(handler.removeBusStation(3));
	}

}
