package com.tp.up.framework;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.tp.up.annotations.AccesMethod;

@SuppressWarnings("rawtypes")
public class ProxyGenerator {

	public String getProxyMethods(Method method, String modelProxyPackageRoute){
		Annotation annotation = method.getAnnotation(AccesMethod.class);
        AccesMethod myAnnotation = (AccesMethod) annotation;
        String type = myAnnotation.type();
        String pathForMethod = myAnnotation.path();
		
        String url = "http://localhost:8080/service" + pathForMethod;
        
        String headers = getHeaders(type);
        String client = getClientType(type);
        
        ArrayList<String> paramsTypes = getParamTypes(method);

        String methodName = method.getName();
        String path = "";
        String methodParams = "";
        
        if (paramsTypes.size() > 0){

        	for (int i = 0; i < paramsTypes.size(); i++) {
	            path = path + " + \"/\" + " + "param" + i + ".toString()";
	        }       
	
	        methodParams = getMethodParams(paramsTypes, methodParams);
        }
        
        String methodReturnType = method.getReturnType().getSimpleName();
        String packageModel = "";
		if (methodReturnType != "void" & methodReturnType != "Integer" | methodReturnType != "String"){
			packageModel = modelProxyPackageRoute + ".";
		}
        
        if(method.getReturnType().equals(ArrayList.class)){
        	Type returnType = method.getGenericReturnType();
        	if(returnType instanceof ParameterizedType){
        	    ParameterizedType tp = (ParameterizedType) returnType;
				Class typeArgClass = (Class) tp.getActualTypeArguments()[0];
        	    methodReturnType = "ArrayList<" + packageModel + typeArgClass.getSimpleName() + ">";
        	}
        }
        
        String returnOrNot = getReturn(methodReturnType);
        
        return getFinnalyMethod(url, headers, client, methodName, path,
				methodParams, methodReturnType, returnOrNot);
	}

	private ArrayList<String> getParamTypes(Method method) {
		ArrayList<String> paramsTypes = new ArrayList<String>();

        for (Class<?> obj : method.getParameterTypes()) {
            String paramType = obj.getName();
            paramsTypes.add(paramType);
        }
		return paramsTypes;
	}

	private String getClientType(String type) {
		String client = "";
        if (type.equals("GET")){
        	client = "HttpGet";
        } else if(type.equals("POST")) {
        	client = "HttpPost";
        	
        } else if(type.equals("DELETE")) {
        	client = "HttpDelete";
        }
		return client;
	}
	
	private String getHeaders(String type) {
		String headers = "";
        if (type.equals("POST")){
        	headers = "request.addHeader(\"Content-Type\", \"application/json\");";
        }
		return headers;
	}
	
	private String getMethodParams(ArrayList<String> paramsTypes,
			String methodParams) {
		for (int i = 0; i < paramsTypes.size(); i++){
			methodParams = methodParams + paramsTypes.get(i) + " param" + i + ", ";
		}
		methodParams = methodParams.substring(0, methodParams.length() - 2);
		return methodParams;
	}

	private String getReturn(String methodReturnType) {
		String returnOrNot = "\n";
        if (methodReturnType != "void"){
        	returnOrNot = 	"		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));\n" +
        					"		StringBuffer result = new StringBuffer();\n" +
		        			"		String line = \"\";\n" +
							"		while ((line = rd.readLine()) != null) {\n" +
							"			result.append(line);\n" +
							"		}\n" +
							"		" + methodReturnType + " myObjects = mapper.readValue(result.toString(), new TypeReference<" + methodReturnType + ">(){});\n" + 
		        			"		return myObjects;\n";
        	
        }
		return returnOrNot;
	}
	
	private String getFinnalyMethod(String url, String headers, String client,
			String methodName, String path, String methodParams,
			String methodReturnType, String returnOrNot) {
		String valid = 
        		"	public " + methodReturnType + " " + methodName + "(" + methodParams + ") throws Exception {\n" +
        		"		String url = \"" + url + "\"" + path + ";\n" +
        		"		HttpClient client = HttpClientBuilder.create().build();\n"+
        		"		" + client + " request = new " + client + "(url);\n" +
        		"		" + headers + "\n" +
        		"		HttpResponse response = client.execute(request);\n"+
    			"		System.out.println(\"Response Code : \" + response.getStatusLine().getStatusCode());\n" +
    			"		" + returnOrNot +
    			"	}\n";
        
		return valid;
	}
}
