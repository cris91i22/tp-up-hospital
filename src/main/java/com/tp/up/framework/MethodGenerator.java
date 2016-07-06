package com.tp.up.framework;

import com.tp.up.annotations.AccesMethod;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class MethodGenerator {

    public String getStringMethod(Method method){
        Annotation annotation = method.getAnnotation(AccesMethod.class);
        AccesMethod myAnnotation = (AccesMethod) annotation;
        String type = myAnnotation.type();
        String pathForMethod = myAnnotation.path();
        
        ArrayList<String> paramsTypes = getParamTypes(method);

        String methodName = method.getName();
        String path = "";
        String newMethod = "";
        String toCallMethodParams = "";
        
        if (paramsTypes.size() > 0){

        	for (int i = 0; i < paramsTypes.size(); i++) {
	            path = path + "{" + "param" + i + "}/";
	            toCallMethodParams = toCallMethodParams + "param" + i + ",";
	        }
	        path = path.substring(0,path.length() - 1);
	        toCallMethodParams = toCallMethodParams.substring(0, toCallMethodParams.length() - 1);
	
	        path = "@Path(\"" + pathForMethod + "/" + path + "\")\n";
	        newMethod = createNewMethod(paramsTypes, newMethod);
        } else {
        	path = "@Path(\"" + pathForMethod + "\")\n";
        }
        
        String methodReturnType = getReturnType(method);
        
        String returnOrNot = "";
        if (methodReturnType != "void"){
        	returnOrNot = "return ";
        }
        
        String conumerOrProducer = getConsumerOrProducer(type, methodReturnType);
        
        return getFinnalyMethod(type, conumerOrProducer, methodName, path,
				newMethod, toCallMethodParams, methodReturnType, returnOrNot);
    }

	private String createNewMethod(ArrayList<String> paramsTypes, String newMethod) {
		for (int i = 0; i < paramsTypes.size(); i++){
		    newMethod = newMethod + "@PathParam(\"param" + i + "\") "+ paramsTypes.get(i) + " param" + i + ", ";
		}
		newMethod = newMethod.substring(0, newMethod.length() - 2);
		return newMethod;
	}

	private ArrayList<String> getParamTypes(Method method) {
		ArrayList<String> paramsTypes = new ArrayList<String>();

        for (Class<?> obj : method.getParameterTypes()) {
            String paramType = obj.getName();
            paramsTypes.add(paramType);
        }
		return paramsTypes;
	}

	private String getConsumerOrProducer(String type, String methodReturnType) {
		String conumerOrProducer = "";
		
        if (methodReturnType != "void" && (type.equals("POST") | type.equals("GET"))){
        	conumerOrProducer = "@Produces(MediaType.APPLICATION_JSON)\n";
        }
		return conumerOrProducer;
	}

	private String getReturnType(Method method) {
		String methodReturnType = method.getReturnType().getName();

        if(method.getReturnType().equals(ArrayList.class)){
        	Type returnType = method.getGenericReturnType();
        	if(returnType instanceof ParameterizedType){
        	    ParameterizedType tp = (ParameterizedType) returnType;
				Class typeArgClass = (Class) tp.getActualTypeArguments()[0];
        	    methodReturnType = "ArrayList<" + typeArgClass.getName() + ">";
        	}
        }
		return methodReturnType;
	}
	
	private String getFinnalyMethod(String type, String conumerOrProducer,
			String methodName, String path, String newMethod,
			String toCallMethodParams, String methodReturnType,
			String returnOrNot) {
		String meth = "    @"+type+"\n" +
                      "    " + path +
                      "    " + conumerOrProducer +
                      "    public " + methodReturnType + " " + methodName + "(" + newMethod + ") {\n" +
                      "        "+returnOrNot+"c."+methodName+"(" + toCallMethodParams +");\n" +
                      "    }\n";

        return meth;
	}

}