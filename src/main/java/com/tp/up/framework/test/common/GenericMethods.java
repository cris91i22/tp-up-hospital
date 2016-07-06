package com.tp.up.framework.test.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class GenericMethods {

	public static String getCleanPath(Class<?> clazz) {
		String internalPath = clazz.getName().replace(".", File.separator);
	    String externalPath = System.getProperty("user.dir") + File.separator+"src";
	    String workDir = externalPath + File.separator + "main/java" + File.separator + internalPath.substring(0, internalPath.lastIndexOf(File.separator));
	    return workDir;
	}
	
	public static void generateFiles(String filePathName, String classSource, String className) throws Exception{
    	// Save source in .java file.
        File root = new File(filePathName);
        File controllerFile = new File(root , className);
        controllerFile.createNewFile();
        BufferedWriter controllerBuffer = new BufferedWriter(new FileWriter(controllerFile));
        controllerBuffer.write(classSource);
        controllerBuffer.close();
        
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		compiler.run(null, null, null, controllerFile.getPath());
    }
	
	public static String generateMethodsInOne(ArrayList<String> methods){
    	String allMethodsInOne = "";
        for (String method : methods) {
        	allMethodsInOne = allMethodsInOne + "\n" + method;
		}
        return allMethodsInOne;
    }
	
}
