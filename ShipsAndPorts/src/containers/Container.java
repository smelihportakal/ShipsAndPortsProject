
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;

public abstract class Container {
	private int ID;
	private int weight;
	protected String type;
	protected double fuelConsumptionperWeight;
	
	/** 
	 * constractor
	 * @param ID Container's int ID
	 * @param weight Container's int weight
	 */
	public Container(int ID, int weight) {
		this.ID = ID;
		this.weight = weight;
	}
	
	/**
	 * This methods calculate and return fuel consumption of a container
	 * @return fuelConsumptionperWeight * weight
	 */
	public double consumption() {
		return fuelConsumptionperWeight * weight;
	}
	
	/**
	 * This methods control whether these two containers are same or not
	 * @param other second container
	 * @return boolean
	 */
	public boolean equals(Container other) {
		if ((this.type == other.getType()) & (this.ID == other.getID()) & (this.weight == other.getWeight())) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Getter method of ID
	 * @return ID
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * Getter method of weight
	 * @return weight
	 */
	public int getWeight() {
		return weight;
	}
	
	/**
	 * Getter method of type of a container
	 * 
	 * @return type String
	 */
	public String getType() {
		return type;
	}

}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

