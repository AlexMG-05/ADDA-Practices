package ejercicio1;

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
		for(int file = 1; file <= 3; file ++) {
			System.out.println("\n------------------------- ↓↓↓ Entry "+ file +" ↓↓↓ -------------------------");
			DatosAlmacenes.iniDatos("resources/ejercicio1/DatosEntrada"+file+".txt");

			WarehouseVertex start = WarehouseVertex.initialVertex();

			// Algoritmo A*
			EGraph<WarehouseVertex, WarehouseEdge> graph =
						EGraph.virtual(start)
						.pathType(PathType.Sum)
						.type(Type.Max)
						.edgeWeight(x -> x.weight())
						.heuristic(WarehouseHeuristic::heuristic)
						.build();
						
			AStar<WarehouseVertex, WarehouseEdge,?> aStar = AStar.ofGreedy(graph);
				
			GraphPath<WarehouseVertex, WarehouseEdge> gp = aStar.search().get();
				
			List<Integer> gp_as = gp.getEdgeList().stream().map(x -> x.action())
						.collect(Collectors.toList()); // getEdgeList();
		
			SolucionAlmacen s_as = SolucionAlmacen.of(gp);

			System.out.println(s_as);
			System.out.println(gp_as);

			GraphColors.toDot(aStar.outGraph(), "generated/ejercicio1AStar/DatosSalida"+file+".gv", 
						v -> v.toGraph(),
						e -> e.action().toString(), 
						v -> GraphColors.colorIf(Color.red,v.goal()),
						e -> GraphColors.colorIf(Color.red, gp.getEdgeList().contains(e)));
		}
	}
}