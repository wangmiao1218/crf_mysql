package com.gennlife.crf.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;


public class JsonCompareJson {
	
	public static boolean JsonCompareJson(String str1,String str2){
		Gson gson1 = new GsonBuilder().create();//or new Gson()   
        JsonElement e1 = gson1.toJsonTree(str1);//or new Gson()   
          
        Gson gson2 = new GsonBuilder().create();  
        JsonElement e2 = gson2.toJsonTree(str2);  
        
        return e1.equals(e2);
	}
	

}
