package com.tp.up.framework.test;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Test;

import com.tp.up.annotations.AccesMethod;
import com.tp.up.framework.MethodGenerator;
import com.tp.up.framework.test.common.GenericMethods;
import com.tp.up.framework.test.model.MyClassToTestMethods;

import static org.junit.Assert.*;

public class MethodGeneratorTest {

	@Test
	public void multiplesParamTypes(){
		ArrayList<String> methods = createStringMethods();
        assertTrue("Check non empty methods", methods.size() > 0);
        assertTrue("Check methods quantity", methods.size() == 5);
	}
	
	@Test
	public void verifyAndCompileClass(){
		ArrayList<String> methods = createStringMethods();
		String stringClass = getStringClass(methods);

		try {
			String path = GenericMethods.getCleanPath(this.getClass());
			GenericMethods.generateFiles(path , stringClass, "/ControllerTest.java");
			File root = new File(path);
			File controllerFile = new File(root , "/ControllerTest.java");
			assertTrue("Verify created class with methods", controllerFile.exists());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getStringClass(ArrayList<String> methods) {
		String stringClass = "package com.tp.up.framework.test;\n" +
				        		"\n" +
				                "import javax.ws.rs.*;\n" +
				                "import javax.ws.rs.core.MediaType;\n" +
				                "import java.util.ArrayList;\n" +
				                "\n" +
				                "public class ControllerTest{\n" +
				                "	ControllerTest c = new ControllerTest();\n" +
				                GenericMethods.generateMethodsInOne(methods) +
				        		"}";
		return stringClass;
	}
	
	private ArrayList<String> createStringMethods(){
		Class<MyClassToTestMethods> clazz = MyClassToTestMethods.class;
        ArrayList<String> methods = new ArrayList<String>();
        MethodGenerator mehthodGenerator = new MethodGenerator();
        
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(AccesMethod.class)) {
                String stringMethod = mehthodGenerator.getStringMethod(method);
                methods.add(stringMethod);
            }
        }
        return methods;
	}
	
}
