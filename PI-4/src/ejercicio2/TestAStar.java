package ejercicio2;

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
			DatosCursos.iniDatos("resources/ejercicio2/DatosEntrada"+file+".txt");

			CourseVertex start = CourseVertex.initialVertex();

			// Algoritmo A*
			EGraph<CourseVertex, CourseEdge> graph =
						EGraph.virtual(start)
						.pathType(PathType.Sum)
						.type(Type.Max)
						.edgeWeight(x -> x.weight())
						.heuristic(CourseHeuristic::heuristic)
						.build();
						
			AStar<CourseVertex, CourseEdge,?> aStar = AStar.ofGreedy(graph);
				
			GraphPath<CourseVertex, CourseEdge> gp = aStar.search().get();
				
			List<Integer> gp_as = gp.getEdgeList().stream().map(x -> x.action())
						.collect(Collectors.toList()); // getEdgeList();
		
			SolucionCursos s_as = SolucionCursos.of(gp);

			System.out.println(s_as);
			System.out.println(gp_as);

			GraphColors.toDot(aStar.outGraph(), "generated/ejercicio2AStar/DatosSalida"+file+".gv", 
						v -> v.toGraph(),
						e -> e.action().toString(), 
						v -> GraphColors.colorIf(Color.red,v.goal()),
						e -> GraphColors.colorIf(Color.red, gp.getEdgeList().contains(e)));
		}
	}	
}
