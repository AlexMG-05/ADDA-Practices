package ejercicio4;

import java.util.*;
import java.util.stream.Collectors;

import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.common.String2;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;



public class DatosTren {

	
	 public static List<Estacion> stations = new LinkedList<>();
	 public static List<Tramo> sections = new LinkedList<>();

	 public static Integer getNumStations() {
	        return stations.size();
	    }
	 
	 public static Integer getNumSections() {
	        return sections.size();
	    }
	 
 private static SimpleWeightedGraph<Estacion, Tramo> graph = Graphs2.simpleWeightedGraph(); //忝い、Angelo
	 
	 public static void iniData (String file) {
			SimpleWeightedGraph<Estacion, Tramo> g = GraphsReader.newGraph(
					file,
					Estacion::ofFormat,
					Tramo::ofFormat,
					Graphs2::simpleWeightedGraph
				);
			
			stations = g.vertexSet().stream().toList();
			sections = g.edgeSet().stream().toList();
			graph = g;
	    }
	 
	 public static SimpleWeightedGraph<Estacion, Tramo> getGraph() {
	        return graph;
	    }
	 
	 public static Estacion getStation(Integer i) {
	        return stations.get(i);
	    }
	 
	 public static Tramo getSection(Integer i) {
	        return sections.get(i);
	    }
	 public static List<Estacion> getStations() {
	        return stations;
	    }
	 
	 public static Integer getDailyPassengers (Integer i) {
			return getStation(i).pasajerosDiarios();
		}
	 
	 public static Integer getNumEmployees (Integer i) {
			return getStation(i).numeroEmpleados();
		}
	 
	 public static Double getClientsSatisfaction (Integer i) {
			return getStation(i).satisfaccionClientes();
		}
	 
	 public static Double getAverageTimeSection (Integer i, Integer j) {
		 try {
			return graph.getEdge(getStation(i), getStation(j)).tiempo();
		 } catch(Exception e) {
			 return 0.0;
		 }
		}
		
		public static Double sectionCost (Integer i, Integer j) {
			try {
			return graph.getEdge(getStation(i), getStation(j)).costeBillete();
			} catch(Exception e) {
				return 0.0;
			}
		}
		
		public static Double costAllSections () {
			return graph.edgeSet().stream().collect(Collectors.summingDouble(x->x.costeBillete()));
		}
		
		public static Boolean areConnected (Integer i, Integer j) {
			return graph.containsEdge(getStation(i), getStation(j)) || graph.containsEdge(getStation(j), getStation(i));
		}
	 
	
	 
	 public static void toConsole() {
	        String2.toConsole(stations, "Stations: ");
	        String2.toConsole(sections, "Sections: ");
	        String2.toConsole(String2.linea());
	    }

	
}