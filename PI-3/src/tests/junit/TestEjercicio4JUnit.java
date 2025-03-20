package tests.junit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import ejercicio4.Estacion;

public class TestEjercicio4JUnit extends BaseTestsEjercicio4 {

	@ParameterizedTest
	@ValueSource(ints = {1,2,3})
	void testOutput(Integer testCase) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
		Class<?>[] paramTypes = { String.class };
		Class<?> ejercicioClass = Class.forName("ejercicio4.Ejercicio4AG");
		String inputFile = "resources/ejercicio4/DatosEntrada"+testCase+".txt";
		String methodName = "solucion";
        Method ejercicioMethod = ejercicioClass.getMethod(methodName, paramTypes);
        @SuppressWarnings("unchecked")
        List<Estacion> 	outputEstacion = (List<Estacion>) ejercicioMethod.invoke(null, inputFile);
		List<String>	output	= outputEstacion.stream().map(s->s.nombre()).toList();
        assertTrue(areCircularListsEqual(BaseTestsEjercicio4.expectedOutput.get(testCase-1), output),"Expected output: " + BaseTestsEjercicio4.expectedOutput.get(testCase-1) + "- output: " + output);
    }
	
	public static boolean areCircularListsEqual(List<String> list1, List<String> list2) {
        // If the sizes are not the same, they can't be circularly equal
        if (list1.size() != list2.size()) {
            return false;
        }
        
        // Check if any rotation of list1 matches list2
        for (int i = 0; i < list1.size(); i++) {
            if (isEqualAfterRotation(list1, list2, i)) {
                return true;
            }
            if (isEqualAfterRotation(reverseList(list1), list2, i)) {
                return true;
            }
        }
        
        return false;
    }

    // Check if list2 is equal to list1 after rotating list1 by `rotations` positions
    private static boolean isEqualAfterRotation(List<String> list1, List<String> list2, int rotations) {
        List<String> rotatedList = new ArrayList<>(list1);
        Collections.rotate(rotatedList, rotations);
        return rotatedList.equals(list2);
    }

    // Reverse the list
    private static List<String> reverseList(List<String> list) {
        List<String> reversedList = new ArrayList<>(list);
        Collections.reverse(reversedList);
        return reversedList;
    }
	
	
}
