package ejercicio2;

import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.alg.PDR;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestPDR {
	public static void main(String[] args) {
		for(int file = 1; file <= 3; file ++) {
			System.out.println("\n------------------------- ↓↓↓ Entry "+ file +" ↓↓↓ -------------------------");
			DatosCursos.iniDatos("resources/ejercicio2/DatosEntrada"+file+".txt");

			CourseVertex vInicial = CourseVertex.initialVertex();
			
			EGraph<CourseVertex, CourseEdge> graph = 
				EGraph.virtual(vInicial)
						.pathType(PathType.Sum)
						.type(Type.Max)
						.heuristic(CourseHeuristic::heuristic)
						.build();

			GreedyOnGraph<CourseVertex, CourseEdge> alg_voraz = GreedyOnGraph.of(graph);		
			GraphPath<CourseVertex, CourseEdge> path = alg_voraz.path();
			path = alg_voraz.isSolution(path)? path: null;

			PDR<CourseVertex,CourseEdge,SolucionCursos> alg_pdr = path==null? PDR.of(graph):
				PDR.of(graph, null, path.getWeight(), path, true);
			
			var res = alg_pdr.search().orElse(null);
			var outGraph = alg_pdr.outGraph();
			if(outGraph!=null) {
				Predicate<CourseVertex> vs = v -> res.getVertexList().contains(v);
				Predicate<CourseEdge> es = e -> res.getEdgeList().contains(e);
				GraphColors.toDot(outGraph, "generated/ejercicio3PDR/DatosSalida"+file+".gv", 
						v -> v.toGraph(),
						e -> e.action().toString(), 
						v -> GraphColors.colorIf(Color.red, vs.test(v)),
						e -> GraphColors.colorIf(Color.red, es.test(e)));

			}	

			
			if(res!=null)
				System.out.println("Solucion PDR: " + SolucionCursos.of(res) + "\n");
			else 
				System.out.println("PDR no obtuvo solucion\n");
		}
	}
}
