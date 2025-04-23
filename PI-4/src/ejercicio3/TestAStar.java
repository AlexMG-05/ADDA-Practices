package ejercicio3;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestAStar {
	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		for(int file = 1; file <= 1; file ++) {
			System.out.println("\n------------------------- ↓↓↓ Entry "+ file +" ↓↓↓ -------------------------");
			DatosFestival.iniDatos("resources/ejercicio3/DatosEntrada"+file+".txt");

			FestivalVertex start = FestivalVertex.initialVertex();

			// Algoritmo A*
			EGraph<FestivalVertex, FestivalEdge> graph =
						EGraph.virtual(start)
						.pathType(PathType.Sum)
						.type(Type.Min)
						.edgeWeight(x -> x.weight())
						.heuristic(FestivalHeuristic::heuristic)
						.build();
						
			AStar<FestivalVertex, FestivalEdge,?> aStar = AStar.ofGreedy(graph);
				
			GraphPath<FestivalVertex, FestivalEdge> gp = aStar.search().get();
				
			List<Integer> gp_as = gp.getEdgeList().stream().map(x -> x.action())
						.collect(Collectors.toList()); 
		
			SolucionFestival s_as = SolucionFestival.of(gp);

			System.out.println(s_as);
			System.out.println(gp_as);

			GraphColors.toDot(aStar.outGraph(), "generated/ejercicio3AStar/DatosSalida"+file+".gv", 
						v -> v.toGraph(),
						e -> e.action().toString(), 
						v -> GraphColors.colorIf(Color.red,v.goal()),
						e -> GraphColors.colorIf(Color.red, gp.getEdgeList().contains(e)));
		}
	}
}
