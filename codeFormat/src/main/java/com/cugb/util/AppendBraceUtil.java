package com.cugb.util;

import java.beans.DefaultPersistenceDelegate;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppendBraceUtil {
	public static String AppendBrace(String string, String type) {
		 Matcher slashMatcher = Pattern.compile(type).matcher(string);
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
		             char c = string.charAt(i);
		             if(c == ')'&&tem2.length()==1){
		               i++;
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
	         String tmp = string.substring(indexHome+1,i); //获取后括号前面的数据(包括')')
	         sb.append(tmp+" {");
	         int tempi = i;
	         while(true) {	//从后括号开始往后寻找分号
	             if(string.length()<=i) break;
	        	 if(string.charAt(i)==';'){
	                 String tmp2 = string.substring(tempi,i+1);//
	                 String splitString=string.substring(tempi,string.length());
	                 int bracePosition = singleLine(splitString, "for", tmp2, tempi);
	                 bracePosition = singleLine(splitString, "else", tmp2, tempi);
	                 if(bracePosition==-1) {
	                	sb.append(tmp2 + " }");
	                	indexHome=i;
	                 }
	                 else {
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
	     //加上最后一个引号的后面的字符串
	    //sb.append(string.substring(indexHome+1,string.length()));
	    return string;
	}
	
	//找字串中是否有关键字
	public static int doubleLine(String string,String type,String type2,String substring,int pointi) {
		return pointi;
		
	}
	public static int singleLine(String string,String type,String substring,int pointi) {
		Matcher slashMatcher = Pattern.compile(type).matcher(substring);
		int indexHome = -1; //开始截取下标

		while(slashMatcher.find()) {
			int indexEnd = slashMatcher.start();
			indexHome = indexEnd + type.length();
			break;
		}
		if(indexHome==-1) return -1;
		pointi += indexHome;	//pointi为字串中关键子在原串中的位置
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
			tempStack.push("(");
			String substring2="";
		//	int indexEnd2=-1;
			slashMatcher = Pattern.compile("[(,)]").matcher(string);
			while(slashMatcher.find()) {
				int indexEnd = slashMatcher.start();
				if(string.charAt(indexEnd)=='(') {
					tempStack.push(string.charAt(indexEnd));
				}
				if(string.charAt(indexEnd)==')') {
					tempStack.pop();
				}
				if(tempStack.isEmpty()) {
					substring2=string.substring(indexEnd, string.length());
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
				return subP2;
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
						return indexEnd;
					}
				}
			}
		}
		
		return -1;
	}
}
