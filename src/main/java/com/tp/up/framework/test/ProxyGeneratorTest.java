package com.tp.up.framework.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Test;

import com.tp.up.annotations.AccesMethod;
import com.tp.up.framework.ProxyGenerator;
import com.tp.up.framework.test.common.GenericMethods;
import com.tp.up.framework.test.model.MyClassToTestMethods;

public class ProxyGeneratorTest {

	@Test
	public void multiplesParamTypes(){
		ArrayList<String> methods = createStringMethods();
        assertTrue("Check non empty proxy methods", methods.size() > 0);
        assertTrue("Check proxy methods quantity", methods.size() == 5);
	}
	
	@Test
	public void verifyAndCompileClass(){
		ArrayList<String> methods = createStringMethods();
		String stringClass = getStringClass(methods);

		try {
			String path = GenericMethods.getCleanPath(this.getClass());
			GenericMethods.generateFiles(path , stringClass, "/ProxyTest.java");
			File root = new File(path);
			File controllerFile = new File(root , "/ProxyTest.java");
			assertTrue("Verify created class with methods", controllerFile.exists());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getStringClass(ArrayList<String> methods) {
		String stringClass = "package com.tp.up.framework.test;\n" +
				                "\n" +
				                "import java.io.BufferedReader;\n"+
								"import java.io.InputStreamReader;\n"+
								"import java.util.ArrayList;\n"+
								"import org.apache.http.HttpResponse;\n"+
								"import org.apache.http.client.HttpClient;\n"+
								"import org.apache.http.client.methods.HttpGet;\n"+
								"import org.apache.http.client.methods.HttpPost;\n"+
								"import org.apache.http.client.methods.HttpDelete;\n"+
								"import org.apache.http.impl.client.HttpClientBuilder;\n"+
								"import com.fasterxml.jackson.core.type.TypeReference;\n"+
								"import com.fasterxml.jackson.databind.ObjectMapper;\n"+
				                "\n" + 
								"public class ProxyTest {\n" +
								"\n" +
				                "	private ObjectMapper mapper;\n" +
				                "	public ProxyTest(){mapper = new ObjectMapper();}\n"+
				                "\n" +
				                GenericMethods.generateMethodsInOne(methods) +
				                "}";
		return stringClass;
	}
	
	private ArrayList<String> createStringMethods(){
		Class<MyClassToTestMethods> clazz = MyClassToTestMethods.class;
        ArrayList<String> methods = new ArrayList<String>();
        ProxyGenerator proxyGenerator = new ProxyGenerator();
        
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(AccesMethod.class)) {
                String stringMethod = proxyGenerator.getProxyMethods(method, "java.lang");
                methods.add(stringMethod);
            }
        }
        return methods;
	}
	
}
