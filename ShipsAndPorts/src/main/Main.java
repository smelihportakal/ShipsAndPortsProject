
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import containers.*;
import ships.*;
import ports.*;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		
		//
		// Main receives two arguments: path to input file and path to output file.
		// You can assume that they will always be provided, so no need to check them.
		// Scanner and PrintStream are already defined for you.
		// Use them to read input and write output.
		// 
		// Good Luck!
		// 
		
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		
		ArrayList<Container> allcontainers = new ArrayList<Container>();
		ArrayList<Ship> ships = new ArrayList<Ship>();		
		ArrayList<Port> ports = new ArrayList<Port>();
		
		int N;
		
		N = in.nextInt();
		
		
		// Input loop
		for (int i = 0 ; i < N; i ++) {		
			int event = in.nextInt();
			
			if (event == 1) {
				// Creating Container
				int portID = in.nextInt();
				int weight = in.nextInt();
				String containerCode = in.nextLine().strip();

				if (containerCode.contentEquals("R")) {

					RefrigeratedContainer rcontainer = new RefrigeratedContainer(allcontainers.size(), weight);
					allcontainers.add(rcontainer);
					ports.get(portID).addContainer(rcontainer);
					//out.println(rcontainer.consumption());
					
				} else if (containerCode.contentEquals("L")) {
					
					LiquidContainer lcontainer = new LiquidContainer(allcontainers.size(), weight);
					allcontainers.add(lcontainer);
					ports.get(portID).addContainer(lcontainer);
							
				} else if (weight <= 3000) {
					
					BasicContainer bcontainer = new BasicContainer(allcontainers.size(),weight);
					allcontainers.add(bcontainer);

					ports.get(portID).addContainer(bcontainer);
					
				}  else {
					HeavyContainer hcontainer = new HeavyContainer(allcontainers.size(), weight);
					allcontainers.add(hcontainer);
					ports.get(portID).addContainer(hcontainer);
				}
				
				
			} else if (event == 2) {
				// Creating Ship
				int portID = in.nextInt();
				int maxWeight = in.nextInt();
				int maxContainers = in.nextInt();
				int maxHcontainers = in.nextInt();
				int maxRcontainers = in.nextInt();
				int maxLcontainers = in.nextInt();
				double fuelConsumption = in.nextDouble();
				
				Ship ship = new Ship(ships.size(), ports.get(portID) , maxWeight, maxContainers, maxHcontainers, maxRcontainers, maxLcontainers, fuelConsumption);
				
				ships.add(ship);

				
			} else if (event == 3) {
				// Creating Port
				double x = in.nextDouble();
				double y = in.nextDouble();
				
				Port port = new Port(ports.size(), x, y);
				
				ports.add(port);
				
			} else if (event == 4) {
				// Loading Container
				int shipID = in.nextInt();
				int containerID = in.nextInt();
				
				Ship ship = ships.get(shipID);
				Container cont = allcontainers.get(containerID);
				
				if (ship.load(cont));	
				
			} else if (event == 5) {
				// Unloading Container
				int shipID = in.nextInt();
				int containerID = in.nextInt();
				
				Ship ship = ships.get(shipID);
				Container cont = allcontainers.get(containerID);
				
				if (ship.unLoad(cont));
				
			} else if (event == 6) {
				// Sailing to another port
				int shipID = in.nextInt();
				int portID = in.nextInt();
				
				Ship ship = ships.get(shipID);
				Port port = ports.get(portID);
				
				if (ship.sailTo(port));
				
			} else if (event == 7) {
				// Refueling
				int shipID = in.nextInt();
				double fuelAmount = in.nextDouble();
				
				
				//out.println(shipID + " refueled " + fuelAmount + " first has " + ships.get(shipID).getFuel());
				ships.get(shipID).reFuel(fuelAmount);
				
			}
		}
		
		
		// Output loop		
		for (Port port: ports) {
			// For each port
			out.println("Port " + port.getID() + ": (" + String.format("%.2f", port.getX()) + ", " + String.format("%.2f", port.getY()) + ")");
			String bc = "  BasicContainer:";
			String hc = "  HeavyContainer:";
			String rc = "  RefrigeratedContainer:";
			String lc = "  LiquidContainer:";
			for (Container cont: port.getContainers())
				// For each container in a port
				if (cont instanceof BasicContainer) {
					bc += " " + cont.getID();
				} else if (cont instanceof RefrigeratedContainer) {
					rc += " " + cont.getID();
				} else if (cont instanceof LiquidContainer) {
					lc += " " + cont.getID();
				} else {
					hc += " " + cont.getID();
				}
			if (bc != "  BasicContainer:") {
				out.println(bc);
			}
			
			if (hc != "  HeavyContainer:") {
				out.println(hc);
			}
			
			if (rc != "  RefrigeratedContainer:") {
				out.println(rc);
			}
			
			if (lc != "  LiquidContainer:") {
				out.println(lc);
			}
			
			for (Ship ship: port.getCurrent()) {
				// For each ship in a port
				out.println("  Ship " + ship.getID() + ": " + String.format("%.2f", ship.getFuel()));
				
				String bc1 = "    BasicContainer:";
				String hc1 = "    HeavyContainer:";
				String rc1 = "    RefrigeratedContainer:";
				String lc1 = "    LiquidContainer:";
				
				for (Container cont: ship.getCurrentContainers())
					// For each container in a ship
					if (cont instanceof BasicContainer) {
						bc1 += " " + cont.getID();
					} else if (cont instanceof RefrigeratedContainer) {
						rc1 += " " + cont.getID();
					} else if (cont instanceof LiquidContainer) {
						lc1 += " " + cont.getID();
					} else {
						hc1 += " " + cont.getID();
					}
				if (bc1 != "    BasicContainer:") {
					out.println(bc1);
				}
				
				if (hc1 != "    HeavyContainer:") {
					out.println(hc1);
				}
				
				if (rc1 != "    RefrigeratedContainer:") {
					out.println(rc1);
				}
				
				if (lc1 != "    LiquidContainer:") {
					out.println(lc1);
				}
			}
		}
		
		
		in.close();
		out.close();
	}
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

