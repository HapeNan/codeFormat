package com.cugb.util;

import java.beans.DefaultPersistenceDelegate;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppendBraceUtil {
	public static String AppendBrace(String string, String type) {
//		 String slashMatcherString = "[^A-Z|^a-z|^0-9|^$|^.]"+type+"[^A-Z|^a-z|^0-9|^$]";
//		 Matcher slashMatcher = Pattern.compile(slashMatcherString).matcher(string);
		 Matcher slashMatcher = Pattern.compile(type).matcher(string);
	     StringBuilder sb = new StringBuilder();
	     int indexHome = -1; //开始截取下标
	     Boolean flag=false;
	     while(slashMatcher.find()) {
	    	 indexHome=-1;
	         int indexEnd = slashMatcher.start();
	         int i=indexEnd+type.length()-1;
	         if("for".equals(type)||"if".equals(type)||"catch".equals(type)||"while".equals(type)) {
	        	 //对于while要判断是while(){}还是do{}while();
	        	 String tem2 = "";
		         while(true){    //寻找当前关键字后匹配的后括号
		         	if(i>=string.length()) {
		        	 i--;
		        	 break;
		        	 }
		             char c = string.charAt(i);
		             if(c == ')'&&tem2.length()==1){
		               break;
		             }else if(c == ')'){
		               tem2 = tem2.substring(0,tem2.length()-1);
		             }else if(c == '('){
		               tem2 += c;
		             }
		             i++;
		         }
	         }
	         
	         if((string.length()-1)==i)    //最后一个字符
	           break;
	         //如果while括号后直接接分号，则不进行，直接跳过
	         if("while".equals(type)) {
	        	 if(string.charAt(i+1)==';') {
	        		 break;
	        	 }
	        	 else if(string.charAt(i+1)==' '){
	        		 if((i+2)<string.length()&&string.charAt(i+2)==';') {
	        			 break;
	        		 }
	        	 }
	         }
	           if(string.charAt(i+2)=='{'||string.charAt(i+1)=='{')
	           continue;
	         
	         flag=true;
	         String tmp = string.substring(indexHome+1,i+1); //获取后括号前面的数据(包括')')
	         sb.append(tmp+" {");
	         //i为索引，tempi为位置
	         int tempi = i+1;
	         while(true) {	//从tempi位置后开始往后寻找分号
	             if(string.length()<=i) break;
	        	 if(string.charAt(i)==';'){
	                 String tmp2 = string.substring(tempi,i+1);//
	                 String splitString=string.substring(tempi,string.length());
	                 int bracePosition = singleLine(splitString, "for", tmp2);
	                 int bracePosition2 =singleLine(splitString,"while",tmp2);
	                 int bracePosition3= doubleLine(splitString, "if","else", tmp2);
	                 int bracePosition4= doubleLine(splitString,"do","while",tmp2);
	                 int bracePosition5= doubleLine(splitString,"try","catch",tmp2);
	                 int []brace= {bracePosition,bracePosition2,bracePosition3,bracePosition4,bracePosition5};
	                  Arrays.parallelSort(brace);
//	                  System.out.println(brace);
	                 if(brace[4]==-1) {
	                	sb.append(tmp2 + " }");
	                	indexHome=i;
	                 }
	                 else {
	                	 tmp2=string.substring(tempi,brace[4]+tempi+1);
	                	 sb.append(tmp2+" }");
	                	 indexHome=brace[4]+tempi;
	                 }
	                 break;
	             }
	             i++;
	         }
	         if(flag==true) {
	         if(indexHome!=(string.length()-1)) {
	         sb.append(string.substring(indexHome+1,string.length()));
//	         string=sb.toString();
//	         sb.delete(0, sb.length());
//	         slashMatcher = Pattern.compile(type).matcher(string);
	         }
	         string=sb.toString();
	         sb.delete(0, sb.length());
	         slashMatcher = Pattern.compile(type).matcher(string);
//	         string=sb.toString();
//	         sb.delete(0, sb.length());
	       //  sb=new StringBuilder
	         flag=false;
	         }
	     }
	  
	    return string;
	}
	
	//找字串中是否有关键字
	public static int doubleLine(String string,String type,String type2,String substring) {
		int temp = singleLine(string, type, substring);
		if(temp == -1) {
			return temp;
		}
		//typeString从type语句块结束 ,temp为type在string中结束符的索引
		String typeString = string.substring(temp+1, string.length());
		int flagnum=temp+1;
		int i = 0;
		//
		while(true) {	//从if语句块结束符往后寻找分号
			if(typeString.length()<=i) break;
			if(typeString.charAt(i)==';'){
				String tmp2 = typeString.substring(0,i+1);//
			//	String splitString=string.substring(0,string.length());
				int Position = singleLine(typeString, type2, tmp2);
				if(Position==-1) {	
					return temp;
				}
				else {
					return Position+flagnum;
				}
			}
			i++;
		}
		return temp;
	}
	
	/*
	*@      返回指定关键字语句块的结束符号索引
	*@Param string '{'括号开始往后的字串 
	*@Param type 寻找语句块的关键字
	*@Param substring 语句块开始的部分: (  ')'之后到第一个';'的字串  )
	*@Param pointi substring在string中的索引位置
	*/
	public static int singleLine(String string,String type,String substring) {
//		String slashMatcherString="[^A-Z|^a-z|^0-9|^$|^.]"+type+"[^A-Z|^a-z|^0-9|^$]";
//		Matcher slashMatcher = Pattern.compile(slashMatcherString).matcher(substring);
		Matcher slashMatcher = Pattern.compile(type).matcher(substring);
		int indexHome = -1; //开始截取下标

		while(slashMatcher.find()) {
			int indexEnd = slashMatcher.start();
		//	System.out.println(indexEnd);
			indexHome = indexEnd + type.length()-1;//indexHome为type的最后一个字符的索引
			break;
		}
		if(indexHome==-1) return -1;
		slashMatcher = Pattern.compile("[;,{]").matcher(string);
		int subP = -1;//记录结束索引
		while(slashMatcher.find()) {
			subP = slashMatcher.start();
			break;
		}
		
		//第一个就匹配到分号作为结束符 
		if(string.charAt(subP)==';') {
			//如果分号前有双语句，则继续寻找双语句的结束
			String substring1=string.substring(indexHome+1,string.length());
			String substring2=string.substring(indexHome+1,subP+1);
			int ifNum=doubleLine(substring1,"if","else",substring2);
			int trycNum=doubleLine(substring1,"try","catch",substring2);
			int dowNum=doubleLine(substring1,"do","while",substring2);
			 int []brace= {ifNum,trycNum,dowNum};
             Arrays.parallelSort(brace);
             System.out.println(subP+"  "+indexHome+"  "+brace[2]);
			if(brace[2]==-1)
			return subP;
			else
			return brace[2]+indexHome+1;
		}
		else if(string.charAt(subP)=='{') {
			//寻找与第一个"{"匹配的"}"并返回该位置
			Stack tempStack = new Stack<String>();
//			tempStack.push("{");
			slashMatcher = Pattern.compile("[{,}]").matcher(string);
			while(slashMatcher.find()) {
				int indexEnd = slashMatcher.start();
				if(string.charAt(indexEnd)=='{') {
					tempStack.push(string.charAt(indexEnd));
				}
				if(string.charAt(indexEnd)=='}') {
					tempStack.pop();
				}
				if(tempStack.isEmpty()) {
					String substring1=string.substring(indexHome+1,string.length());
					String substring2=string.substring(indexHome+1,subP+1);
					int ifNum=doubleLine(substring1,"if","else",substring2);
					int trycNum=doubleLine(substring1,"try","catch",substring2);
					int dowNum=doubleLine(substring1,"do","while",substring2);
					 int []brace= {ifNum,trycNum,dowNum};
		             Arrays.parallelSort(brace);
					if(brace[2]==-1)
					return indexEnd;
					else
					return brace[2]+indexHome+1;
				}
			}
		}
		return -1;
	}
}
