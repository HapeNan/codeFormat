package com.cugb.util;

import java.beans.DefaultPersistenceDelegate;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppendBraceUtil {
	public static String AppendBrace(String string, String type) {
		 String slashMatcherString = "[^A-Z|^a-z|^0-9|^$|^.]"+type+"[^A-Z|^a-z|^0-9|^$]";
		 Matcher slashMatcher = Pattern.compile(slashMatcherString).matcher(string);
	     StringBuilder sb = new StringBuilder();
	     int indexHome = -1; //开始截取下标
	     Boolean flag=false;
	     while(slashMatcher.find()) {
	    	 indexHome=-1;
	         int indexEnd = slashMatcher.start();
	         int i=indexEnd+type.length();
	         if("for".equals(type)||"if".equals(type)) {
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
	         if(string.length()<i+1)    //最后一句
	           break;
	         if(string.charAt(i)=='{'||string.charAt(i+1)=='{')
	           continue;
	         flag=true;
	         String tmp = string.substring(indexHome+1,i+1); //获取后括号前面的数据(包括')')
	         sb.append(tmp+" {");
	         int tempi = i+1;
	         while(true) {	//从关键字后开始往后寻找分号
	             if(string.length()<=i) break;
	        	 if(string.charAt(i)==';'){
	                 String tmp2 = string.substring(tempi,i+1);//
	                 String splitString=string.substring(tempi,string.length());
	                 int bracePosition = singleLine(splitString, "for", tmp2);
	                 int bracePosition2 = singleLine(splitString, "else", tmp2);
	                 int bracePosition3= doubleLine(splitString, "if","else", tmp2);
	                 if(bracePosition==-1&&bracePosition2==-1&&bracePosition3==-1) {
	                	sb.append(tmp2 + " }");
	                	indexHome=i;
	                 }
	                 else {
	                	 if(bracePosition==-1) {
	                		 if(bracePosition2==-1)
	                		 bracePosition=bracePosition3;
	                		 else
	                			 bracePosition=bracePosition2;
	                	 }
	                	 bracePosition=bracePosition+tempi;
	                	 tmp2=string.substring(tempi,bracePosition);
	                	 sb.append(tmp2+" }");
	                	 indexHome=bracePosition;	
	                 }
	              //   indexHome = i;
	                 break;
	             }
	             i++;
	         }
	         if(flag==true) {
	         sb.append(string.substring(indexHome+1,string.length()));
	         string=sb.toString();
	         sb.delete(0, sb.length());
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
		//typeString从type语句块结束 ')'位置开始,temp为type在string中结束符的位置
		String typeString = string.substring(temp+1, string.length());
		int flagnum=temp+1;
		int i = 0;
		while(true) {	//从后括号开始往后寻找分号
			if(typeString.length()<=i) break;
			if(typeString.charAt(i)==';'){
				String tmp2 = typeString.substring(0,i);//
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
		return string.length();
	}
	
	/*
	*@      返回指定关键字语句块的结束位置 (添加'}'的位置)
	*@Param string '{'括号开始往后的字串 
	*@Param type 寻找语句块的关键字
	*@Param substring 语句块开始的部分: (  ')'之后到第一个';'的字串  )
	*@Param pointi substring在string中的索引位置
	*/
	public static int singleLine(String string,String type,String substring) {
		String slashMatcherString="[^A-Z|^a-z|^0-9|^$|^.]"+type+"[^A-Z|^a-z|^0-9|^$]";
		Matcher slashMatcher = Pattern.compile(slashMatcherString).matcher(substring);
		int indexHome = -1; //开始截取下标

		while(slashMatcher.find()) {
			int indexEnd = slashMatcher.start();
			indexHome = indexEnd + type.length();
			break;
		}
		if(indexHome==-1) return -1;
		slashMatcher = Pattern.compile("[(,;,{]").matcher(string);
		int subP = 0;
		while(slashMatcher.find()) {
			subP = slashMatcher.start();
			break;
		}
		if(string.charAt(subP)==';') {
			return subP;
		}
		else if(string.charAt(subP)=='{') {
			//寻找与第一个"{"匹配的"}"并返回该位置
			Stack tempStack = new Stack<String>();
			tempStack.push("{");
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
					return indexEnd;
				}
			}
		}
		else if(string.charAt(subP)=='(') {
			Stack tempStack = new Stack<String>();
			String substring2="";
		//	int indexEnd2=-1;
			slashMatcher = Pattern.compile("[(,)]").matcher(string);
			int flagnum=-1;
			while(slashMatcher.find()) {
				int indexEnd = slashMatcher.start();
				if(string.charAt(indexEnd)=='(') {
					tempStack.push(string.charAt(indexEnd));
				}
				if(string.charAt(indexEnd)==')') {
					tempStack.pop();
				}
				if(tempStack.isEmpty()) {
					substring2=string.substring(indexEnd+1, string.length());
					flagnum=indexEnd;
					break;
				}
			}
			slashMatcher = Pattern.compile("[;,{]").matcher(substring2);
			int subP2 = 0;
			while(slashMatcher.find()) {
				subP2 = slashMatcher.start();
				break;
			}
			if(string.charAt(subP2)==';') {
				return subP2+flagnum;
			}
			else if(string.charAt(subP2)=='{') {
				//寻找与第一个"{"匹配的"}"并返回该位置
				tempStack = new Stack<String>();
				tempStack.push("{");
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
						return indexEnd+flagnum;
					}
				}
			}
		}
		return -1;
	}
}
