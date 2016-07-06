package com.tp.up.framework.test;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

import com.tp.up.framework.GenerateAccessPoint;

import static org.junit.Assert.*;

public class GenerateAccessPointTest {

	@Test(expected = RuntimeException.class)
	public void withoutArguments() throws Exception {
      System.out.println("Test without arguments...");
      GenerateAccessPoint.main(null);
    }
	
	/**
		Este test para ejecutarlo primero hay que sacarle el @Ignore y completarle el path
		completo donde va a generar nuestras clases, las mismas que le pasamos al GenerateAccessPoint
		por argumentos..
	**/
	@Test
	@Ignore
	public void createFilesSuccessfully() throws Exception {
		System.out.println("Test with arguments...");
		String controllerPath = "/home/cmedina/workspace/Tp-Backend/src/main/java/com/tp/up";
		String proxyPath = "/home/cmedina/workspace/Tp-Client/src/main/java/com/tp/up/client";
		String[] a = {controllerPath,
						proxyPath,
						"com.tp.up.client",
						"com.tp.up.client.model"};

		GenerateAccessPoint.main(a);
		
		File root = new File(controllerPath + "/HospitalController.java");
        File proxyRoot = new File(proxyPath + "/Proxy.java");
        
        assertTrue("File Controller was created successfully", root.exists());
        assertTrue("File Proxy was created successfully", proxyRoot.exists());
	}
	
}
