package com.tp.up.framework;

import com.tp.up.annotations.AccesMethod;
import com.tp.up.hospital.HospitalService;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class GenerateAccessPoint {


    // LInKS PARA USAR
    //  http://stackoverflow.com/questions/2946338/how-do-i-programmatically-compile-and-instantiate-a-java-class
    // https://wink.apache.org/1.0/html/JAX-RS%20Getting%20Started.html

    // PREGUNTAS: COMO LEVANTO EL SERVIDOR CON ESTE FRAMEWORK????
    // FALTA CREAR EL PROXY

    // INSTANCIAR LA CLASE LO HACEMOS DE OTRO LADO

    public static void main(String [] args) throws IOException, MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException {

        Class<HospitalService> clazz = HospitalService.class;

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(AccesMethod.class)) {
                MethodGenerator mehthodGenerator = new MethodGenerator();
                String stringMethod = mehthodGenerator.getStringClass(method);

                // Prepare source somehow.
                String classSource = generateClass(stringMethod);

                // Save source in .java file.
                File root = new File("/home/cmedina/workspace/Tp-Backend/src/main/java/com/tp/up");
                File root2 = new File(root, "HospitalController.java");
                BufferedWriter out = null;
                root2.createNewFile();
                out = new BufferedWriter(new FileWriter(root2));
                out.write(classSource);
                out.close();


                //JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
                //compiler.run(null, null, null, root2.getPath());

// Load and instantiate compiled class.
                //URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { root2.toURI().toURL() });
                //Class<?> cls = Class.forName("com.framework.HospitalController", true, classLoader); // Should print "hello".
                //Object instance = cls.newInstance(); // Should print "world".


                //System.out.println(instance);


                System.out.print("AESES");
            }

        }

    }

    public static String generateClass(String stringMethod){
    	return 	   "package com.tp.up;\n" +
                   "\n" +
                   "import com.tp.up.hospital.HospitalService;\n" +
                   "import javax.ws.rs.*;\n" +
                   "import javax.ws.rs.core.MediaType;\n" +
                   "public class HospitalController {\n" +
                   stringMethod +
                   "}";
    }

}
