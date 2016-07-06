package com.tp.up.framework.test.model;

import java.util.ArrayList;

import com.tp.up.annotations.AccesMethod;

public class MyClassToTestMethods {

	@AccesMethod(type = "POST", path = "/save")
	public void save(){
		System.out.println("TEST POST WITHOUT PARAMS");
	}
	
	@AccesMethod(type = "POST", path = "/save")
	public void save(String a, Integer b, Boolean c){
		System.out.println("TEST POST WITH PARAMS");
	}
	
	@AccesMethod(type = "GET", path = "/get")
	public Integer get(String a){
		System.out.println("TEST GET - RETURN SIMPLE TYPE");
		return 1;
	}
	
	@AccesMethod(type = "GET", path = "/get/list")
	public ArrayList<Integer> getList(String a){
		System.out.println("TEST GET - RETURN COMPLEX TYPE");
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(1);
		return l;
	}
	
	@AccesMethod(type = "DELETE", path = "/delete")
	public void delete(String id){
		System.out.println("TEST DELETE");
	}
	
}
