package org.adtran.conv.YangToJavaPojoGenerator.parser.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
	
	private static Pattern pattern = Pattern.compile("public.*class[ ]+([a-zA-Z0-9]+).*");
	
	private static Map<String, String> yangToJavaMapping = new HashMap<String, String>();
	
	static {
		yangToJavaMapping.put("boolean", "Boolean");
		yangToJavaMapping.put("empty", "Boolean");
		yangToJavaMapping.put("int8", "Byte");
		yangToJavaMapping.put("int16", "Short");
		yangToJavaMapping.put("int32", "Integer");
		yangToJavaMapping.put("int64", "Long");
		yangToJavaMapping.put("string", "String");
		yangToJavaMapping.put("decimal64", "Double");
		yangToJavaMapping.put("uint8", "Short");
		yangToJavaMapping.put("uint16", "Integer");
		yangToJavaMapping.put("uint32", "Long");
		yangToJavaMapping.put("uint64", "BigInteger");
		yangToJavaMapping.put("binary", "byte[]");
	}
	
	public static String capitalize(String methodName){
		String retVal =methodName.trim();
		if(retVal.length() >0){
			if(retVal.contains("-")) {
				retVal=convertCamelCase(retVal);
			}
			
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append(retVal.substring(0, 1).toUpperCase());
			strBuilder.append(retVal.substring(1).trim());
			retVal =strBuilder.toString();
			
		}
//		System.out.println("captilize() input: "+methodName+"\noutput: "+retVal);
		return retVal;
		
	}
	
	public static String convertCamelCase(String hypenatedInput){
		String retVal =hypenatedInput;
		StringBuilder strBuilder = new StringBuilder();
		StringTokenizer tokenizer =new StringTokenizer(hypenatedInput,"-");
		strBuilder.append(tokenizer.nextToken().trim());
		while(tokenizer.hasMoreTokens()){
			String token = tokenizer.nextToken().trim();
			if(token.length() >0){
			strBuilder.append(token.substring(0, 1).toUpperCase());
			strBuilder.append(token.substring(1).toLowerCase().trim());
			}
		}
		retVal =strBuilder.toString();
//		System.out.println("convertCamelCase() input:"+hypenatedInput+"output:"+retVal);
		return retVal;
	}
	
public static void splitJavaContent(File srcFile, String destDirPath) throws IOException {
		
		BufferedReader srcReader = new BufferedReader(new FileReader(srcFile));
		String line = null;
		boolean startedNewFile = false;
		StringBuilder strBuilder = null;
		String className = null;
		while ((line = srcReader.readLine()) != null) {
			
			if(startedNewFile || (startedNewFile = isContentStartPoint(line))) {
				if(strBuilder == null) {
					strBuilder = new StringBuilder();
				}
				
				line = formatUnknownContent(line);
				strBuilder.append(line + "\n");
				
				if(className == null) {
					className = extractClassName(line);
				}
				
				if(isContentEndPoint(line)) {
					startedNewFile = false;
				}
				
			}
			
			if(!startedNewFile && className != null) {
				writeContentToFile(strBuilder, className, destDirPath);
				className = null;
				strBuilder = null;
			}
			
		}
		
		srcReader.close();
		
	}
	
	private static boolean isContentStartPoint(String line) {
		return line.equals("//Beginning");
	}
	
	private static boolean isContentEndPoint(String line) {
		return line.equals("//Ending");
	}
	
	private static String extractClassName(String line) {
		String className = null;
		Matcher matcher = pattern.matcher(line);
		
		if(matcher.find()) {
			className = matcher.group(1);
		}
		
		return className;
	}
	
	private static String formatUnknownContent(String line) {
		
		line = line.replace("&lt;", "<");
		line = line.replace("&gt;", ">");
		
		return line;
	}
	
	private static void writeContentToFile(StringBuilder strBuilder, String className, String destDirPath) throws IOException {
		if(destDirPath.endsWith("/")) {
			destDirPath = destDirPath + className + ".java";			
		}else {
			destDirPath = destDirPath + "/" + className + ".java";
		}
		
		FileWriter destWriter = new FileWriter(destDirPath);
		destWriter.write(strBuilder.toString());
		destWriter.close();
	}
	
	public static void removeModuleNameSpaces(File srcFile, File destFilePath) throws IOException {
		BufferedReader srcReader = new BufferedReader(new FileReader(srcFile));
		
		FileWriter destWriter = new FileWriter(destFilePath);
		String line = null;
		boolean moduleNameSpaceReplaced = false;
		boolean moduleOpenTagStarted = false;
		while((line = srcReader.readLine()) != null) {
			if(!moduleNameSpaceReplaced) {
				if(!moduleOpenTagStarted && line.trim().contains("<module")) {
					moduleOpenTagStarted = true;
				}
				if(moduleOpenTagStarted) { 
					if(line.trim().contains(">")) {
						line = "<module>";
						moduleNameSpaceReplaced = true;
					}else {
						continue;
					}
				}
			}
			
			
			destWriter.write(line + "\n");
		}
		
		srcReader.close();
		destWriter.close();
	}
	
	public static String getJavaMappedDataType(String yangDataType) {
		String javaDataType = yangToJavaMapping.get(yangDataType);
		if(javaDataType == null) {
			
			if(yangDataType.contains(":")) {
				yangDataType = yangDataType.substring(yangDataType.lastIndexOf(":") + 1);
			}
			
			javaDataType = capitalize(yangDataType);
		}
		return javaDataType;
	}
	
}
