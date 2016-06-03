package com.tp.up.framework;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.ArrayList;

import com.tp.up.annotations.AccesMethod;
import com.tp.up.hospital.HospitalService;

public class GenerateAccessPoint {

    // LInKS PARA USAR
    //  http://stackoverflow.com/questions/2946338/how-do-i-programmatically-compile-and-instantiate-a-java-class
    // https://wink.apache.org/1.0/html/JAX-RS%20Getting%20Started.html

    // FALTA CREAR EL PROXY

    public static void main(String [] args) throws IOException, MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException {

        Class<HospitalService> clazz = HospitalService.class;

        ArrayList<String> methods = new ArrayList<String>();
        
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(AccesMethod.class)) {
                MethodGenerator mehthodGenerator = new MethodGenerator();
                String stringMethod = mehthodGenerator.getStringClass(method);
                methods.add(stringMethod);
            }
        }
        
        String allMethodsInOne = "";
        
        for (String method : methods) {
        	allMethodsInOne = allMethodsInOne + "\n" + method;
		}
        
        String classSource = generateClass(allMethodsInOne);

        // Save source in .java file.
        File root = new File("/home/cmedina/workspace/Tp-Backend/src/main/java/com/tp/up"); //Main argument agregarlo!!
        File root2 = new File(root, "HospitalController.java");
        BufferedWriter out = null;
        root2.createNewFile();
        out = new BufferedWriter(new FileWriter(root2));
        out.write(classSource);
        out.close();


        //JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        //compiler.run(null, null, null, root2.getPath());

        //Load and instantiate compiled class.
        //URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { root2.toURI().toURL() });
        //Class<?> cls = Class.forName("com.framework.HospitalController", true, classLoader); // Should print "hello".
        //Object instance = cls.newInstance(); // Should print "world".


        //System.out.println(instance);
    }

    public static String generateClass(String stringMethod){
    	return 	   "package com.tp.up;\n" +
                   "\n" +
                   "import com.tp.up.hospital.HospitalService;\n" +
                   "import javax.ws.rs.*;\n" +
                   "import javax.ws.rs.core.MediaType;\n" +
                   "@Path(\"/service\")\n" +
                   "public class HospitalController {\n" +
                   "	private static HospitalService c = new HospitalService();\n" +
                   stringMethod +
                   "}";
    }

}
