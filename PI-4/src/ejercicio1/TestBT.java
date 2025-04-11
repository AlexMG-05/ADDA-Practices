package ejercicio1;

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
			
			for(int file = 1; file <= 3; file ++) {
				System.out.println("\n------------------------- ↓↓↓ Entry "+ file +" ↓↓↓ -------------------------");
				DatosAlmacenes.iniDatos("resources/ejercicio1/DatosEntrada"+file+".txt");

				WarehouseVertex vInicial = WarehouseVertex.initialVertex();
				
				EGraph<WarehouseVertex, WarehouseEdge> graph =  
						EGraph.virtual(vInicial)
						.pathType(PathType.Sum)
						.type(Type.Max)
						.heuristic(WarehouseHeuristic::heuristic)
						.build();

				GreedyOnGraph<WarehouseVertex, WarehouseEdge> alg_voraz = GreedyOnGraph.of(graph);		
				GraphPath<WarehouseVertex, WarehouseEdge> path = alg_voraz.path();
				path = alg_voraz.isSolution(path)? path: null;

				path = null;
				
				BT<WarehouseVertex,WarehouseEdge,SolucionAlmacen> alg_bt = path==null? BT.of(graph):
					BT.of(graph, null, path.getWeight(), path, true);
				
				var res = alg_bt.search().orElse(null);
				var outGraph = alg_bt.outGraph();
				
				if(outGraph!=null) {
					Predicate<WarehouseVertex> vs = v -> res.getVertexList().contains(v);
					Predicate<WarehouseEdge> es = e -> res.getEdgeList().contains(e);
					GraphColors.toDot(outGraph, "generated/ejercicio1BT/DatosSalida"+file+".gv", 
							v -> v.toGraph(),
							e -> e.action().toString(), 
							v -> GraphColors.colorIf(Color.red, vs.test(v)),
							e -> GraphColors.colorIf(Color.red, es.test(e)));
				}	
				
				
				if(res!=null) System.out.println("Solucion BT: " + SolucionAlmacen.of(res) + "\n");
				else System.out.println("BT no obtuvo solucion\n");
			}
		}
}