package ejercicio1;

import java.io.IOException;
import java.util.Locale;

import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Ejercicio1PLE { 
	
	public static void solucion(String file) throws IOException {
		DatosAlmacenes.iniDatos(file);
		
		AuxGrammar.generate(DatosAlmacenes.class, "models/ex1.lsi", "gurobi_models/ex1-2.lp");
		GurobiSolution solution = GurobiLp.gurobi("gurobi_models/ex1-2.lp");
		Locale.setDefault(Locale.of("en", "US"));
		
		System.out.println(solution.toString((s,d)->d>0.));
	}
	
	public static void main(String[] args) {
		try {
        	solucion("resources/ejercicio1/DatosEntrada3.txt");
        } catch (IOException e) {
        	e.printStackTrace();
        }

	}
}
