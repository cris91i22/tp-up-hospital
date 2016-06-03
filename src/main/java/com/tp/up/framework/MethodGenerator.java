package com.tp.up.framework;

import com.tp.up.annotations.AccesMethod;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class MethodGenerator {

    public String getStringClass(Method method){

        Annotation annotation = method.getAnnotation(AccesMethod.class);
        AccesMethod myAnnotation = (AccesMethod) annotation;
        String type = myAnnotation.type();
        String pathForMethod = myAnnotation.path();
        String conumerOrProducer = "";

        /*if (type.equals("POST")){
            conumerOrProducer = "@Consumes(MediaType.APPLICATION_JSON)\n"; 
        } else if(type.equals("GET")) {
            conumerOrProducer = "@Produces(MediaType.APPLICATION_JSON)\n";
        }*/
        if (type.equals("POST") | type.equals("GET")){
        	conumerOrProducer = "@Produces(MediaType.APPLICATION_JSON)\n";
        }
        
        ArrayList<String> paramsTypes = new ArrayList<String>();

        for (Class<?> obj : method.getParameterTypes()) {
            String paramType = obj.getName();
            paramsTypes.add(paramType);
        }

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
	
	        for (int i = 0; i < paramsTypes.size(); i++){
	            newMethod = newMethod + "@PathParam(\"param" + i + "\") "+ paramsTypes.get(i) + " param" + i + ", ";
	        }
	        newMethod = newMethod.substring(0, newMethod.length() - 2);
        } else {
        	path = "@Path(\"" + pathForMethod + "\")\n";
        }
        
        String methodReturnType = method.getReturnType().getName();

        String returnOrNot = "";
        if (methodReturnType != "void"){
        	returnOrNot = "return ";
        } else {
        	conumerOrProducer = "";
    	}
        
        String meth = "    @"+type+"\n" +
                      "    " + path +
                      "    " + conumerOrProducer +
                      "    public " + methodReturnType + " " + methodName + "(" + newMethod + ") {\n" +
                      "        "+returnOrNot+"c."+methodName+"(" + toCallMethodParams +");\n" +
                      "    }\n";

        return meth;
    }

}