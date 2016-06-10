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

    public static void main(String [] args) throws IOException, MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
    	String filePathName = "";
    	String proxyPathName = "";
    	String proxyPackageRoute = "";
    	String modelProxyPackageRoute = "";
    	if (args.length == 4){
    		filePathName = args[0];
    		proxyPathName = args[1];
    		proxyPackageRoute = args[2];
    		modelProxyPackageRoute = args[3];
    	} else {
    		throw new RuntimeException("File path name don't set...");
    	}
    	
        Class<HospitalService> clazz = HospitalService.class;

        ArrayList<String> methods = new ArrayList<String>();
        ArrayList<String> proxyMethods = new ArrayList<String>();
        
        MethodGenerator mehthodGenerator = new MethodGenerator();
        ProxyGenerator proxyGenerator = new ProxyGenerator();
        
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(AccesMethod.class)) {
                String stringMethod = mehthodGenerator.getStringMethod(method);
                String stringProxy = proxyGenerator.getProxyMethods(method, modelProxyPackageRoute);
                methods.add(stringMethod);
                proxyMethods.add(stringProxy);
            }
        }
        
        String allMethodsInOne = "";
        
        for (String method : methods) {
        	allMethodsInOne = allMethodsInOne + "\n" + method;
		}
        
        String allProxyMethodsInOne = "";
        
        for (String method : proxyMethods) {
        	allProxyMethodsInOne = allProxyMethodsInOne + "\n" + method;
		}
        
        String classSource = generateClass(allMethodsInOne);
        String classProxy = generateProxyClass(allProxyMethodsInOne, proxyPackageRoute);

        // Save source in .java file.
        File root = new File(filePathName);
        File proxyRoot = new File(proxyPathName);
        
        File controllerFile = new File(root, "HospitalController.java");
        File proxyFile = new File(proxyRoot, "Proxy.java");
        
        controllerFile.createNewFile();
        proxyFile.createNewFile();
        
        BufferedWriter controllerBuffer = new BufferedWriter(new FileWriter(controllerFile));
        BufferedWriter proxyBuffer = new BufferedWriter(new FileWriter(proxyFile));
        
        controllerBuffer.write(classSource);
        proxyBuffer.write(classProxy);
        
        controllerBuffer.close();
        proxyBuffer.close();
    }

    public static String generateClass(String stringMethod){
    	return 	   "package com.tp.up;\n" +
                   "\n" +
                   "import com.tp.up.hospital.HospitalService;\n" +
                   "import javax.ws.rs.*;\n" +
                   "import javax.ws.rs.core.MediaType;\n" +
                   "import java.util.ArrayList;\n" +
                   "\n" +
                   "@Path(\"/service\")\n" +
                   "public class HospitalController {\n" +
                   "	private static HospitalService c = new HospitalService();\n" +
                   stringMethod +
                   "}";
    }
    
    public static String generateProxyClass(String proxyMethods, String proxyRoute){
    	return "package " + proxyRoute + ";\n" +
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
				"public class Proxy {\n" +
				"\n" +
                "	private ObjectMapper mapper;\n" +
                "	public Proxy(){mapper = new ObjectMapper();}\n"+
                "\n" +
                proxyMethods +
                "}";
    }

}
