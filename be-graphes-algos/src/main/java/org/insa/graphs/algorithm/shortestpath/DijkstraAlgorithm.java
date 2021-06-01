package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.utils.ElementNotFoundException;
import org.insa.graphs.algorithm.utils.EmptyPriorityQueueException;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    public Label addLabel(int id) {
    	return new Label(id);
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
        	labelList.add(addLabel(currentNode.getId()));
        }
        
        labelList.get(origin.getId()).setCost(0);
        labelHeap.insert(labelList.get(origin.getId()));
        
        boolean notFound = true;
        
        Label currentLabel = null;
        while(!labelList.get(data.getDestination().getId()).isMark() && notFound) {
        	try {
        		currentLabel = labelHeap.deleteMin();
        	} catch (EmptyPriorityQueueException e) {
        		notFound = false;
        	}
        	currentLabel.setMark(true);
        	notifyNodeReached(graph.getNodes().get(currentLabel.getid()));
        	// Parcours des successeurs de la node actuelle
        	for (Arc arcSuccessor : graph.getNodes().get(currentLabel.getid()).getSuccessors()) {
        		// Small test to check allowed roads...
                if (!data.isAllowed(arcSuccessor)) {
                    continue;
                }
                
                int successorId = arcSuccessor.getDestination().getId();
                Label successorLabel = labelList.get(successorId);
                
                if (!successorLabel.isMark()) {
                	if (successorLabel.getCost() > currentLabel.getCost()+data.getCost(arcSuccessor)) {
                		try {
                    		labelHeap.remove(successorLabel);
                    		successorLabel.setCost(currentLabel.getCost()+data.getCost(arcSuccessor));
                    		successorLabel.setPreviousArc(arcSuccessor);
                    		labelHeap.insert(successorLabel);
                		} catch(ElementNotFoundException e) {
                			successorLabel.setCost(currentLabel.getCost()+data.getCost(arcSuccessor));
                    		successorLabel.setPreviousArc(arcSuccessor);
                    		labelHeap.insert(successorLabel);
                		}
                	}
                }
        	}
        }
        
        
        // The destination has been found, notify the observers.
        notifyDestinationReached(data.getDestination());
        
        // Create the path from the array of predecessors...
        ArrayList<Arc> arcs = new ArrayList<>();
        
        while (currentLabel.getPreviousArc() != null) {
        	arcs.add(currentLabel.getPreviousArc());
        	currentLabel = labelList.get(currentLabel.getPreviousArc().getOrigin().getId());
        }
        
        // Reverse the path...
        Collections.reverse(arcs);

        // Create the final solution.
        if (!notFound)
        	solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        else
        	solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        
        return solution;
    }

}
