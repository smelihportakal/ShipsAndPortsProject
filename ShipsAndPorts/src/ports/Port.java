
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package ports;

import java.util.ArrayList;

import containers.Container;
import interfaces.IPort;
import ships.Ship;

public class Port implements IPort {
	
	private int ID;
	private double X;
	private double Y;
	private ArrayList<Container> containers = new ArrayList<Container>();
	private ArrayList<Ship> history = new ArrayList<Ship>();
	private ArrayList<Ship> current =  new ArrayList<Ship>();

	/**
	 * @param ID int value
	 * @param X double value
	 * @param Y double value
	 */
	public Port(int ID,double X,double Y) {
		this.ID = ID;
		this.X = X;
		this.Y = Y;
				
	}
	
	/**
	 * This method adds Container to storage of port
	 * @param container Container
	 */
	public void addContainer(Container container)  {
		this.containers.add(container);
	}
	
	/**
	 * This method calculates and gets the distance between two ports
	 * @param other Port
	 * @return distance double
	 */
	public double getDistance(Port other) {
		return Math.sqrt(Math.pow(X-other.X,2)+Math.pow(Y-other.Y,2));
	}

	/**
	 * This method adds the ship to port
	 * @param s Ship
	 */
	@Override
	public void incomingShip(Ship s) {
		current.add(s);
	}

	/**
	 * This method adds the ship to the history of the port
	 * @param s Ship
	 */
	@Override
	public void outgoingShip(Ship s) {
		if (!history.contains(s)) {
			history.add(s);
		}

	}
	
	/**
	 * This method sorts the ships by IDs
	 * 
	 */
	private void bubblesortShips() {
		int n = current.size();
		
		for (int i = 0; i < n - 1 ; i++) 
			for(int j = 0; j < n-i-1 ;j++)
				if(current.get(j).getID() > current.get(j+1).getID()) {
					Ship a = current.get(j);
					current.set(j, current.get(j+1));
					current.set(j+1, a);
				}
		
	}
	
	/**
	 * This method sorts the containers by IDs
	 */
	private void bubblesortContainers() {
		int n = containers.size();
		
		for (int i = 0; i < n - 1 ; i++) 
			for(int j = 0; j < n-i-1 ;j++)
				if(containers.get(j).getID() > containers.get(j+1).getID()) {
					Container a = containers.get(j);
					containers.set(j, containers.get(j+1));
					containers.set(j+1, a);
				}
		
	}
	
	/**
	 * This method sorts and gets the container
	 * @return containers ArrayList
	 */
	public ArrayList<Container> getContainers() {
		this.bubblesortContainers();
		return containers;
	}
	
	/**
	 * This method sorts and gets the ships
	 * @return current ArrayList
	 */
	public ArrayList<Ship> getCurrent() {
		this.bubblesortShips();
		return current;
	}
	
	/**
	 * Getter method of Id
	 * @return ID int value
	 */
	public int getID() {
		return ID;
	}
	
	/** 
	 * Getter method of X coordinat
	 * @return X double
	 */
	public double getX() {
		return X;
	}
	
	/**
	 * Getter method of Y coordinat
	 * @return Y double
	 */
	public double getY() {
		return Y;
	}
	
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

