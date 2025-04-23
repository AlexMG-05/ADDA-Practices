package ejercicio3;

import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestBT {
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		for(int file = 1; file <= 2; file ++) {
			System.out.println("\n------------------------- ↓↓↓ Entry "+ file +" ↓↓↓ -------------------------");
			DatosFestival.iniDatos("resources/ejercicio3/DatosEntrada"+file+".txt");

			FestivalVertex vInicial = FestivalVertex.initialVertex();
			
			EGraph<FestivalVertex, FestivalEdge> graph =  
					EGraph.virtual(vInicial)
					.pathType(PathType.Sum)
					.type(Type.Min)
					.heuristic(FestivalHeuristic::heuristic)
					.build();

			GreedyOnGraph<FestivalVertex, FestivalEdge> alg_voraz = GreedyOnGraph.of(graph);		
			GraphPath<FestivalVertex, FestivalEdge> path = alg_voraz.path();
			path = alg_voraz.isSolution(path)? path: null;

			path = null;
			
			BT<FestivalVertex,FestivalEdge,SolucionFestival> alg_bt = path==null? BT.of(graph):
				BT.of(graph, null, path.getWeight(), path, true);
			
			var res = alg_bt.search().orElse(null);
			var outGraph = alg_bt.outGraph();
			
			if(outGraph!=null) {
				Predicate<FestivalVertex> vs = v -> res.getVertexList().contains(v);
				Predicate<FestivalEdge> es = e -> res.getEdgeList().contains(e);
				GraphColors.toDot(outGraph, "generated/ejercicio3BT/DatosSalida"+file+".gv", 
						v -> v.toGraph(),
						e -> e.action().toString(), 
						v -> GraphColors.colorIf(Color.red, vs.test(v)),
						e -> GraphColors.colorIf(Color.red, es.test(e)));
			}	
			
			
			if(res!=null) System.out.println("Solucion BT:\n"+ SolucionFestival.of(res) + "\n");
			else System.out.println("BT no obtuvo solucion\n");
		}
		

        long endTime = System.nanoTime();
        long duration = endTime - startTime; // in nanoseconds

        double seconds = duration / 1_000_000_000.0;

        System.out.println("Execution time: " + seconds + " seconds");
	}
}
