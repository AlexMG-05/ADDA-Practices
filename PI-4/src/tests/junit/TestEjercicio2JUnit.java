package tests.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import ejercicio2.SolucionCursos;

public class TestEjercicio2JUnit extends BaseTestsEjercicio2 {

	@ParameterizedTest
	@CsvSource({"ple,1", "ple,2", "ple,3", "ag,1", "ag,2", "ag,3",})
	void testOutput(String method, int testCase) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
		Class<?>[] paramTypes = { String.class };
		String packageName = "ejercicio2";
		Class<?> ejercicioClass = Class.forName(packageName+".Ejercicio2PLE");
		String inputFile = "resources/ejercicio2/DatosEntrada"+testCase+".txt";
		String methodName = "solucion";
        if (method.equals("ag")) {
        	ejercicioClass = Class.forName(packageName+".Ejercicio2AG");
        }  
        Method ejercicioMethod = ejercicioClass.getMethod(methodName, paramTypes);
        SolucionCursos output 	= (SolucionCursos) ejercicioMethod.invoke(null, inputFile);
        Double tolerancia = 10.;
        assertEquals(BaseTestsEjercicio2.expectedOutput.get(testCase-1), output.getPuntuacionTotal(), tolerancia, "Expected output: " + expectedOutput + "-" + methodName + " output: " + output);
    }
}
