package ejercicio2;

import java.io.IOException;
import java.util.Locale;

import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Ejercicio2PLE {
    
    public static void solucion(String file) throws IOException {
    	DatosCursos.iniDatos(file);
    	
    	AuxGrammar.generate(DatosCursos.class, "models/ex2.lsi", "gurobi_models/ex2-3.lp");
    	GurobiSolution solution = GurobiLp.gurobi("gurobi_models/ex2-3.lp");
		Locale.setDefault(Locale.of("en", "US"));
		
		System.out.println(solution.toString((s,d)->true));
    }
    
    public static void main(String[] args) throws IOException {	
    	try {
    		solucion("resources/ejercicio2/DatosEntrada3.txt");
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
	}
}
