package tests.junit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;

public class BaseTestsEjercicio4 {

    public static List<List<String>> expectedOutput;
    private static String outputPath = "/salida/ejercicio4/";
    private static String outputFileName = "DatosSalida";

    private static Class<TestEjercicio1JUnit> thisClass = TestEjercicio1JUnit.class;

    @BeforeAll
    private static void readOutput() {
    	expectedOutput = new ArrayList<>();
    	for(int i=1;i<=3;i++) {
    		String fileName = outputPath + outputFileName+i+".txt";
	        InputStream is = thisClass.getResourceAsStream(fileName);
	        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
	        BufferedReader reader = new BufferedReader(streamReader);
	        String line;
			try {
				line = reader.readLine();
			    List<String> elements = Arrays.asList(line.split(","));
			    expectedOutput.add(elements);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
}
