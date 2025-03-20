package ejercicio3;

import java.io.IOException;
import java.util.Locale;

import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Ejercicio3PLE {
    
    public static void solucion(String file) throws IOException {
    	DatosFestival.iniDatos(file);
    	
    	AuxGrammar.generate(DatosFestival.class, "models/ex3.lsi", "gurobi_models/ex3.lp");
    	GurobiSolution solution = GurobiLp.gurobi("gurobi_models/ex3.lp");
		Locale.setDefault(Locale.of("en", "US"));
		
		System.out.println(solution.toString((s,d)->d>0.));
    }
    public static void main(String[] args) throws IOException {	
    	try {
    		solucion("resources/ejercicio3/DatosEntrada1.txt");
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
	}
}
