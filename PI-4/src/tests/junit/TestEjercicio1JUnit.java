package tests.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import ejercicio1.SolucionAlmacen;

public class TestEjercicio1JUnit extends BaseTestsEjercicio1 {

	@ParameterizedTest
	@CsvSource({"ple,1", "ple,2", "ple,3", "ag,1", "ag,2", "ag,3",})
	void testOutput(String method, int testCase) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
		Class<?>[] paramTypes = { String.class };
		String packageName = "ejercicio1";
		Class<?> ejercicioClass = Class.forName(packageName+".Ejercicio1PLE");
		String inputFile = "resources/ejercicio1/DatosEntrada"+testCase+".txt";
		String methodName = "solucion";
        if (method.equals("ag")) {
        	ejercicioClass = Class.forName(packageName+".Ejercicio1AG");
        }  
        Method ejercicioMethod = ejercicioClass.getMethod(methodName, paramTypes);
        SolucionAlmacen output 	= (SolucionAlmacen) ejercicioMethod.invoke(null, inputFile);
        Double tolerancia = 0.;
        assertEquals(expectedOutput.get(testCase-1), output.getNumProductos(), tolerancia, "Expected output: " + expectedOutput + "-" + methodName + " output: " + output);
    }
}
