package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label> {
	private final int id;
	private float cost = Float.MAX_VALUE;
	private boolean mark = false;
	private Arc previousArc = null;
	
	public Label(int id) {
		this.id = id;
	}
	
	public float getCost() {
		return this.cost;
	}
	
	public boolean isMark() {
		return mark;
	}

	public void setMark(boolean mark) {
		this.mark = mark;
	}

	public Arc getPreviousArc() {
		return previousArc;
	}

	public void setPreviousArc(Arc previousArc) {
		this.previousArc = previousArc;
	}

	public int getid() {
		return id;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}
	
	public int compareTo(Label other) {
		int returnValue;
		if (this.cost < other.cost)
			returnValue = -1;
		else if (this.cost > other.cost)
			returnValue = 1;
		else
			returnValue = 0;
        return returnValue;
    }
	
}
