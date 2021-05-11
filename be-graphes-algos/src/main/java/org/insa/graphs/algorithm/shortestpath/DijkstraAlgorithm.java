package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
    	// Retrieve the graph
    	final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        
        ShortestPathSolution solution = null;
        
        BinaryHeap<Label> labelHeap = new BinaryHeap<Label>();
        List<Label> labelList = new ArrayList<Label>();
        
        Node origin = data.getOrigin();
        
        // Initialize a list of labels of the nodes
        for (Node currentNode : graph.getNodes()) {
        	labelList.add(new Label(currentNode.getId()));
        }
        
        labelList.get(origin.getId()).setCost(0);
        labelHeap.insert(labelList.get(origin.getId()));
        
        // Create the path from the array of predecessors...
        ArrayList<Arc> arcs = new ArrayList<>();
        
        Label currentLabel;
        Label min;
        while(!labelList.get(data.getDestination().getId()).isMark()) {
        	min = labelHeap.findMin();
        	currentLabel = min;
        	arcs.add(min.getPreviousArc());
        	currentLabel.setMark(true);
        	
        	for (Arc arcSuccessor : graph.getNodes().get(currentLabel.getid()).getSuccessors()) {
        		// Small test to check allowed roads...
                if (!data.isAllowed(arcSuccessor)) {
                    continue;
                }
                
                int successorId = arcSuccessor.getDestination().getId();
                Label successorLabel = labelList.get(successorId);
                
                if (!successorLabel.isMark()) {
                	successorLabel.setCost(Math.min(successorLabel.getCost(),
                			currentLabel.getCost()+arcSuccessor.getLength()));
                	if (successorLabel.getCost() == currentLabel.getCost()+arcSuccessor.getLength()) {
                		successorLabel.setPreviousArc(arcSuccessor);
                		labelHeap.insert(successorLabel);
                	}
                }
        	}
        }
        
        // The destination has been found, notify the observers.
        notifyDestinationReached(data.getDestination());

        // Reverse the path...
        Collections.reverse(arcs);
        arcs.remove(arcs.size());

        // Create the final solution.
        solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        
        return solution;
    }

}
