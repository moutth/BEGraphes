package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData;

public class LabelStar extends Label {
	
	private double distToDest;
	
	public LabelStar(int id, ShortestPathData data) {
        super(id);
        if (data.getMode().equals(AbstractInputData.Mode.LENGTH))
        	distToDest = data.getDestination().getPoint().distanceTo(data.getGraph().getNodes().get(id).getPoint());
        else
        	distToDest = data.getDestination().getPoint().distanceTo(data.getGraph().getNodes().get(id).getPoint())/data.getGraph().getGraphInformation().getMaximumSpeed();
    }
	
	public double getDistToDest() {
		return this.distToDest;
	}

	public double getTotalCost() {
		return this.getCost() + this.getDistToDest();
	}
	
	public int compareTo(LabelStar other) {
		int returnValue;
		if (this.getTotalCost() < other.getTotalCost())
			returnValue = -1;
		else if (this.getTotalCost() > other.getTotalCost())
			returnValue = 1;
		else {
			if (this.getDistToDest() < other.getDistToDest())
				returnValue = -1;
			else if (this.getDistToDest() > other.getDistToDest())
				returnValue = 1;
			else
				returnValue = 0;
		}
        return returnValue;
    }
}
