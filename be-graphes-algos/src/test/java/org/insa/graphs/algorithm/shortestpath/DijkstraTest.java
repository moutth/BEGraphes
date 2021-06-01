package org.insa.graphs.algorithm.shortestpath;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import org.insa.graphs.model.Graph;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;

public class DijkstraTest {
	private static String mapAdress = "/home/riffard/Bureau/commetud/3eme Annee MIC/Graphes-et-Algorithmes/maps/toulouse.mapgr";
	
	private static GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapAdress))));
	
	private static Graph mapToulouse = reader.read();
	
	private static ArcInspector inspector1 = ArcInspectorFactory.getAllFilters().get(0);
	private static ArcInspector inspector2 = ArcInspectorFactory.getAllFilters().get(0);
	private static ArcInspector inspector3 = ArcInspectorFactory.getAllFilters().get(0);
	private static ArcInspector inspector4 = ArcInspectorFactory.getAllFilters().get(0);
	private static ArcInspector inspector5 = ArcInspectorFactory.getAllFilters().get(0);
	
	//trajet simple à travers Toulouse
	private static ShortestPathData data1 = new ShortestPathData(mapToulouse, mapToulouse.get(24116), mapToulouse.get(554), inspector1);
	private static ShortestPathSolution testDijkstra1 = new DijkstraAlgorithm(data1).doRun(); 
	//trajet impossible en voiture
	private static ShortestPathData data2 = new ShortestPathData(mapToulouse, mapToulouse.get(39539), mapToulouse.get(19068), inspector2);
	//trajet statique
	private static ShortestPathData data3 = new ShortestPathData(mapToulouse, mapToulouse.get(1093), mapToulouse.get(1093), inspector3);
	//point inexistant
	private static ShortestPathData data4 = new ShortestPathData(mapToulouse, mapToulouse.get(156321), mapToulouse.get(11), inspector4);
	
	public void testValide() throws Exception {
		assertTrue(testDijkstra1.getPath().isValid());
	}
	
}
