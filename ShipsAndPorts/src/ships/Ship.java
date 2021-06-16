
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package ships;

import java.util.ArrayList;

import containers.Container;
import interfaces.IShip;
import ports.Port;
import containers.*;

public class Ship implements IShip {

	private int ID;
	private double fuel = 0;
	private Port currentPort;
	private ArrayList<Container> CurrentContainers = new ArrayList<Container>();
	private double fuelConsumptionPerKM;
	private int totalWeightCapacity;
	private int maxNumberOfAllContainers;
	private int maxNumberOfHeavyContainers;
	private int maxNumberOfLiquidContainers;
	private int maxNumberOfRefrigeratedContainers;

	private int numberOfBasicContainers = 0;
	private int numberOfHeavyContainers = 0;
	private int numberOfLiquidContainers = 0;
	private int numberOfRefrigeratedContainers = 0;
	
	/**
	 * @param ID int value
	 * @param p Port class
	 * @param totalWeightCapacity int value
	 * @param maxNumberOfAllContainers int value
	 * @param maxNumberOfHeavyContainers int value
	 * @param maxNumberOfRefrigeratedContainers int value
	 * @param maxNumberOfLiquidContainers int value
	 * @param fuelConsumptionPerKM int value 
	 */
	public Ship(int ID, Port p, int totalWeightCapacity, int maxNumberOfAllContainers, int maxNumberOfHeavyContainers, int maxNumberOfRefrigeratedContainers, int maxNumberOfLiquidContainers, double fuelConsumptionPerKM) {
		
		this.ID = ID;
		this.currentPort = p;
		this.totalWeightCapacity = totalWeightCapacity;
		this.maxNumberOfAllContainers = maxNumberOfAllContainers;
		this.maxNumberOfHeavyContainers = maxNumberOfHeavyContainers;
		this.maxNumberOfLiquidContainers = maxNumberOfLiquidContainers;
		this.maxNumberOfRefrigeratedContainers = maxNumberOfRefrigeratedContainers;
		this.fuelConsumptionPerKM = fuelConsumptionPerKM;
				
		this.currentPort.getCurrent().add(this);
	}
	
	/**
	 * This method controls whether this ship can sail to port p
	 * If it can, it travel the port 
	 * @param p Port
	 */
	@Override
	public boolean sailTo(Port p) {
		
		if (this.getFuel() >= this.calculateFuelConsumption(p)) {
			this.travel(p);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method refuels the ship
	 * @param newFuel double value
	 */
	@Override
	public void reFuel(double newFuel) {
		fuel += newFuel;
	}

	/**
	 * This method controls whether this ship can load the given container
	 * If it can, it loads the container to ship storage
	 * @param cont Container 
	 * @return boolean
	 */
	@Override
	public boolean load(Container cont) {
		boolean isLoadable = false;
		if ((currentPort.getContainers().contains(cont)) & (this.getTotalWeight() + cont.getWeight() <= totalWeightCapacity) & (CurrentContainers.size() < maxNumberOfAllContainers)) {					
			if (cont instanceof HeavyContainer) {
				if (numberOfHeavyContainers < maxNumberOfHeavyContainers) {
					if (cont instanceof RefrigeratedContainer) {
						if(numberOfRefrigeratedContainers < maxNumberOfRefrigeratedContainers)  {
							numberOfHeavyContainers += 1;
							numberOfRefrigeratedContainers += 1;
							isLoadable = true;
						}
				
					} else if (cont instanceof LiquidContainer) {
						if(numberOfLiquidContainers < maxNumberOfLiquidContainers)  {
							numberOfHeavyContainers += 1;
							numberOfLiquidContainers += 1;
							isLoadable = true;
						}
					} else {
						numberOfHeavyContainers += 1;
						isLoadable = true;
					} 
				}
					
			} else {
				numberOfBasicContainers += 1;
				isLoadable = true;		
			}
		}
		
		if (isLoadable) {
			this.getCurrentContainers().add(cont);
			this.getCurrentPort().getContainers().remove(cont);
			return true;
		} else {
			return false;
		}
			
	}

	/**
	 * This method controls whether this ship can unload the given container
	 * If it can, it unloads the container to ship's port storage
	 * @param cont Container
	 * @return boolean
	 */
	@Override
	public boolean unLoad(Container cont) {
		if (this.getCurrentContainers().contains(cont))  {
			if (cont instanceof RefrigeratedContainer) {
				numberOfHeavyContainers -= 1;
				numberOfRefrigeratedContainers -= 1;
			} else if (cont instanceof LiquidContainer) {
				numberOfHeavyContainers -= 1;
				numberOfLiquidContainers -= 1;
			} else if (cont instanceof HeavyContainer) {
				numberOfHeavyContainers -= 1;
			} else {
				numberOfBasicContainers -= 1;
			}
			this.getCurrentContainers().remove(cont);
			this.getCurrentPort().addContainer(cont);
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * This method calculates the total weight of containers in the ship
	 * @return totalWeight int value
	 */
	public int getTotalWeight() {
		int totalWeigth = 0;
		for (Container cont : this.getCurrentContainers()) {
			totalWeigth += cont.getWeight();
		}
		return totalWeigth;
	}
	
	
	/**
	 * This method changes the ship's current port and adds the ships to last port's history
	 * @param p Port travelled
	 */
	public void travel(Port p) {
		this.fuel -= this.calculateFuelConsumption(p);
		this.currentPort.outgoingShip(this);
		this.currentPort.getCurrent().remove(this);
		p.incomingShip(this);
		this.currentPort = p;
	}
	
	/**
	 * This method calculates the fuel consumption of ship while travelling to other port
	 * @param port Port travelled
	 * @return total fuel consumption
	 */
	private double calculateFuelConsumption(Port port) {
		double fuelPerKm = 0;
		for (Container cont : CurrentContainers) {
			fuelPerKm += cont.consumption();
		}
		fuelPerKm += fuelConsumptionPerKM;
		return fuelPerKm*currentPort.getDistance(port);
	}
	
	/**
	 * This method sorts the containers of the ship by container Ids
	 */
	private void bubblesortContainers() {
		int n = CurrentContainers.size();
		
		for (int i = 0; i < n - 1 ; i++) 
			for(int j = 0; j < n-i-1 ;j++)
				if(CurrentContainers.get(j).getID() > CurrentContainers.get(j+1).getID()) {
					Container a = CurrentContainers.get(j);
					CurrentContainers.set(j, CurrentContainers.get(j+1));
					CurrentContainers.set(j+1, a);
				}
		
	}
	
	
	/**
	 * Getter method of currentPort
	 * @return currentPort Port
	 */
	public Port getCurrentPort() {
		return currentPort;
	}
	
	/**
	 * Getter method of fuel
	 * @return fuel double value
	 */
	public double getFuel() {
		return fuel;
	}
	
	/**
	 * Getter method of ship ID
	 * @return ID int value
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * This method gets sorted current containers of ship 
	 * @return CurrentContainers ArrayList of Container
	 */
	public ArrayList<Container> getCurrentContainers() {
		bubblesortContainers();
		return CurrentContainers;
	}
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

