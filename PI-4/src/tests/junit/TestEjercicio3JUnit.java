package tests.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import ejercicio3.SolucionFestival;

public class TestEjercicio3JUnit extends BaseTestsEjercicio3 {

	@ParameterizedTest
	@CsvSource({"ple,1", "ple,2", "ple,3", "ag,1", "ag,2", "ag,3",})
	void testOutput(String method, int testCase) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
		Class<?>[] paramTypes = { String.class };
		String packageName = "ejercicio3";
		Class<?> ejercicioClass = Class.forName(packageName+".Ejercicio3PLE");
		String inputFile = "resources/ejercicio3/DatosEntrada"+testCase+".txt";
		String methodName = "solucion";
        if (method.equals("ag")) {
        	ejercicioClass = Class.forName(packageName+".Ejercicio3AG");
        }  
        Method ejercicioMethod = ejercicioClass.getMethod(methodName, paramTypes);
        SolucionFestival output = (SolucionFestival) ejercicioMethod.invoke(null, inputFile);
        Double tolerancia = 20.;
        assertEquals(BaseTestsEjercicio3.expectedOutput.get(testCase-1), output.getCosteTotal(), tolerancia, "Expected output: " + expectedOutput + "-" + methodName + " output: " + output);
        
    }
}
