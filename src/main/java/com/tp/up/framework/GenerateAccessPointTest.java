package com.tp.up.framework;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.Assert.*;

public class GenerateAccessPointTest {

	@Test(expected = RuntimeException.class)
	public void testNoArguments() throws Exception {
      System.out.println("Test without arguments...");
      GenerateAccessPoint.main(null);
    }
	
	@Test
	public void testArguments() throws Exception {
		System.out.println("Test with arguments...");
		String[] a = {"/home/cmedina/workspace/Tp-Backend/src/main/java/com/tp/up",
						"/home/cmedina/workspace/Tp-Client/src/main/java/com/tp/up/client",
						"com.tp.up.client",
						"com.tp.up.client.model"};

		GenerateAccessPoint.main(a);
	}
	
}
