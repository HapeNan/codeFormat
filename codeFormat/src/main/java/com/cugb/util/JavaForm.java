package com.cugb.util;


import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @说明 ： java格式化
* @作者 ：WangXL
* @时间 ：2018 11 22
**/

public class JavaForm {
 
   /**
    * @说明 ：格式化java代码 
    * @参数 ：@param dataTmp
    * @参数 ：@return
    * @返回 ：String
    * @作者 ：WangXL
    * @时间 ：2018 11 22
    **/
   public static String formJava(String data) {
       String dataTmp = replaceStrToUUid(data,"\"");
       dataTmp = replaceStrToUUid(dataTmp,"'");
       
       
       dataTmp = seperateByBlank(dataTmp, "for");
       dataTmp = seperateByBlank(dataTmp, "if");
       dataTmp = dataTmp.trim();
       dataTmp = replaceForSegmentToUUid(dataTmp,"for \\(");
       
       dataTmp = repalceHHF(dataTmp, "){", ") {");
       
//       dataTmp = appendCurlyBrace(dataTmp, "for");
//       dataTmp = appendCurlyBrace(dataTmp, "if");
//       dataTmp = appendCurlyBrace(dataTmp, "else if");
       
       dataTmp = repalceHHF(dataTmp,"\r\n","");
       
       dataTmp = repalceHHF(dataTmp,"{","{\n");
       dataTmp = repalceHHF(dataTmp,"}","}\n");
       dataTmp = repalceHHF(dataTmp,"/*","\n/*\n");
       dataTmp = repalceHHF(dataTmp,"* @","\n* @");
       dataTmp = repalceHHF(dataTmp,"*/","\n*/\n");
  
       dataTmp = repalceHHF(dataTmp,";",";\n");
       dataTmp = repalceHHF(dataTmp,"//","\n//");
       
        
       //最后处理缩进
       dataTmp = repalceHHFX(dataTmp,"\n");
       
       for(Map.Entry<String, String> r : mapZY.entrySet()){
           dataTmp = dataTmp.replace(r.getKey(),r.getValue());
       }
       if(dataTmp==null)
           return data;
       return dataTmp;
   }
   
   /**
    * @说明 ：在关键字的前后添加分隔空格
    * @参数 ：@param string 字符串  
    * @参数 ：@param type 关键字类型  
    * @时间 ：2019 5 2
    **/
   public static String seperateByBlank(String string,String type) {
       Matcher slashMatcher = Pattern.compile("[^A-Z|^a-z|^0-9|^$]"+type).matcher(string);
       StringBuilder sb = new StringBuilder(); 
       int indexHome = -1; //开始截取下标
       while(slashMatcher.find()) {
           int indexEnd = slashMatcher.start();
           String tmp = string.substring(indexHome+1,indexEnd+1); //获取"if"前面的数据
           sb.append(tmp);
           sb.append(" "+type+" ");
           indexHome = indexEnd+type.length();
       }
       //加上最后一个引号的后面的字符串
      sb.append(string.substring(indexHome+1,string.length()));
      return sb.toString();
   }
   
   /**
    * @说明 ：补全关键字后的花括号 
    * @参数 ：@param string 字符串  
    * @参数 ：@param type 关键字类型  
    * @时间 ：2019 5 2
    **/
   public static String appendCurlyBrace(String string,String type){
     Matcher slashMatcher = Pattern.compile(type).matcher(string);
     StringBuilder sb = new StringBuilder();
     int indexHome = -1; //开始截取下标
     while(slashMatcher.find()) {
         int indexEnd = slashMatcher.start();
         int i=indexEnd+type.length();
         String tem2 = "";
         while(true){    //寻找当前关键字后匹配的后括号
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
         if(string.length()<i+2)    //最后一句
           break;
         if(string.charAt(i+2)=='{')
           continue;
         String tmp = string.substring(indexHome+1,i+1); //获取后括号前面的数据(包括')')
         sb.append(tmp+" {");
         int tempi = i+1;//记录内部语句开始位置
         while(true){
             i++;
             if(string.charAt(i)==';'){
                 String tmp2 = string.substring(tempi,i+1);
                 sb.append(tmp2 + " }");
                 indexHome = i;
                 break;
             }
         }
     }
     //加上最后一个引号的后面的字符串
    sb.append(string.substring(indexHome+1,string.length()));
    return sb.toString();
 }
   
   /**
    * @说明 ： 将for（）语句的内部替换为uuid, 
    * 由于for语句内部出现由';'符,影响语法换行判断,故而将for内部替换为uuid
    * @参数 ：@param string
    * @时间 ：2019 5 2
    **/
   public static String replaceForSegmentToUUid(String string,String type){
       Matcher slashMatcher = Pattern.compile(type).matcher(string);
       StringBuilder sb = new StringBuilder();
       int indexHome = -1; //开始截取下标
       while(slashMatcher.find()) {
          int indexEnd = slashMatcher.start();
          String tmp = string.substring(indexHome+1,indexEnd); //获取"for ("前面的数据
          sb.append(tmp);
          String tem2 = "";    //记录前括号
          //找到与for 匹配的后括号
          int i=indexEnd+1;
          for(;i<string.length();i++ ){
            char c = string.charAt(i);
            if(c == ')'&&tem2.length()==1){
              break;
            }else if(c == ')'){
              tem2 = tem2.substring(0,tem2.length()-1);
            }else if(c == '('){
              tem2 += c;
            }
          }
          if(i==string.length()) break;
          String tmp3 = string.substring(indexEnd,i+1);
          String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
          uuid = "for ("+uuid+")";
          mapZY.put(uuid, tmp3);
          sb.append(uuid);
          indexHome = i;
       }
       //加上最后一个引号的后面的字符串
       sb.append(string.substring(indexHome+1,string.length()));
       return sb.toString();
   }
   
   public static Map<String,String> mapZY = new HashMap<String,String>();
   /**
    * @说明 ： 循环替换指定字符为随机uuid  并将uuid存入全局map:mapZY   
    * @参数 ：@param string   字符串
    * @参数 ：@param type    指定字符
    * @作者 ：WangXL
    * @时间 ：2018 11 23
    **/
   public static String replaceStrToUUid(String string,String type){
       Matcher slashMatcher = Pattern.compile(type).matcher(string);
       boolean bool = false;
       StringBuilder sb = new StringBuilder();
       int indexHome = -1; //开始截取下标
       while(slashMatcher.find()) {
          int indexEnd = slashMatcher.start();
          String tmp = string.substring(indexHome+1,indexEnd); //获取号前面的数据
          if(indexHome == -1 ||bool == false){
              sb.append(tmp);
              bool = true;
              indexHome = indexEnd;
          }else{
              if(bool){
                  String tem2 = "";
                  for( int i=indexEnd-1 ; i>-1 ; i-- ){
                       char c = string.charAt(i);
                       if(c == '\\'){
                           tem2 += c;
                       }else{
                           break;
                       }
                  }
                  int tem2Len = tem2.length();
                  if(tem2Len>-1){
                       //结束符前有斜杠转义符 需要判断转义个数奇偶   奇数是转义了  偶数才算是结束符号   
                       if(tem2Len % 2==1){ 
                           //奇数 非结束符
                       }else{
                           //偶数才算是结束符号
                          String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
                          uuid = type+uuid+type;
                          mapZY.put(uuid, type+tmp+type);
                          sb.append(uuid);
                          bool = false;
                          indexHome = indexEnd;
                       }
                   }
              }
          }
       }
       sb.append(string.substring(indexHome+1,string.length()));
       return sb.toString();
    }
   
   
   
   //处理换行
   public static String repalceHHF(String data,String a,String b){
       try{
           data = data.replace(a, "<<yunwangA>><<yunwangB>>");
           String  arr[] = data.split("<<yunwangA>>");
           StringBuilder result = new StringBuilder();
           if(arr != null){
               for(int i=0;i<arr.length;i++){
                   String t = arr[i];
                   result.append(t.trim());
                   //不能去除注释行后面的"\n"
                   if(t.indexOf("//")!=-1 && "\r\n".equals(a)){
                     System.out.println("sss");  
                     result.append("\r\n"); 
                   }
               }
           }
           String res = result.toString();
           res = res.replace("<<yunwangB>>", b);
           res = res.replace("<<yunwangA>>", "");
           return res;
       }catch(Exception e){
       }
       return null;
   }
   
   //处理缩进
   public static String repalceHHFX(String data,String a){
       try{
           String  arr[] = data.split(a);
           StringBuilder result = new StringBuilder();
           if(arr != null){
               String zbf = "\t";
               Stack<String> stack = new Stack<String>();
               for(int i=0;i<arr.length;i++){
                   String tem = arr[i].trim();
                   if(tem.indexOf("{")!=-1){
                       String kg = getStack(stack,false);
                       if(kg == null){
                           result.append((tem+"\n"));
                           kg = "";
                       }else{
                           kg = kg + zbf;
                           result.append(kg+tem+"\n"); 
                       }
                       stack.push(kg);
                   }else if(tem.indexOf("}")!=-1){
                       String kg = getStack(stack,true);
                       if(kg == null){
                           result.append(tem+"\n");
                       }else{
                           result.append(kg+tem+"\n");
                       }
                   }else{
                       String kg = getStack(stack,false);
                       if(kg == null){
                           result.append(tem+"\n");
                       }else{
                           result.append(kg+zbf+tem+"\n");
                       }
                   }
               }
           }
           String res = result.toString();
           return res;
       }catch(Exception e){}
       return null;
   }
   
   /**
    * @说明 ： 获得栈数据
    * @参数 ：@param stack 
    * @参数 ：@param bool true 弹出  false 获取
    * @时间 ：2018 11 22
    **/
   public static String getStack(Stack<String> stack,boolean bool){
       String result = null;
       try{
           if(bool){
               return stack.pop();
           }
           return stack.peek();
       }catch(EmptyStackException  e){
       }
       return result;
   }
   
}
