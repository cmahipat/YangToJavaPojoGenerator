package org.adtran.conv.YangToJavaPojoGenerator.pojo.main;

import java.io.File;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.adtran.conv.YangToJavaPojoGenerator.parser.tools.Util;
import org.adtran.conv.YangToJavaPojoGenerator.parser.yang2java.jyang;


/**
 * This entry point for this yang2javapojo converter.
 * @author brampras
 *
 */
public class MainApp {

	public static void main(String[] args) {
		String[] inputArgs= validateArgs(args);
		String [] argsToParser = {"-o", "combinedData.yin", "-p", inputArgs[1], "-f", "yin", inputArgs[0]};
		jyang parser = new jyang(argsToParser);
		parser.parse(argsToParser);
		// do convert pojo using the xml transformer
		if(parser.isParsingOk()){
			MainApp app = new MainApp();
			app.generateJavasFile(parser.getOutputFileName(), inputArgs[2]);
		}
		
	}
	
	private static String[] validateArgs(String[] args) {
		// Parsing arguments
		if (args.length != 2) {
			System.err.println("Usage: java –jar YangToJavaPojoGenerator.jar <input_yang_dir> <out_dir>");
			System.exit(-1);
		}
		String inputDirFile = args[0].trim();
		String outPutDirFile = args[1].trim();
		
		File inputDir = new File(inputDirFile);
		File outputDir = new File(outPutDirFile);
		if(!inputDir.isDirectory() || inputDir.list().length ==0){
			System.err.println("invalid Input directory or empty directory");
			System.err.println("Usage: java –jar YangToJavaPojoGenerator.jar <input_yang_dir> <out_dir>");
			System.exit(-1);
		}
		if(!outputDir.isDirectory()){
			System.err.println("invalid output directory");
			System.err.println("Usage: java –jar YangToJavaPojoGenerator.jar <input_yang_dir> <out_dir>");
			System.exit(-1);
		}
		String inputYangFile = inputDirFile + "/" + inputDir.list()[0];
		
		String inputArgs[] = new String[]{inputYangFile, inputDirFile, outPutDirFile};
		return inputArgs;
	}

	private void generateJavasFile(String outputfilename, String outputDir) {
		try {
			File javasFile = new File("outFile.java");
			File generatedYinFile = new File(outputfilename);
			File updatedFilePath = new File("updatedYinFile.yin");
			Util.removeModuleNameSpaces(generatedYinFile, updatedFilePath);
			Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(this.getClass().getClassLoader().getResourceAsStream("yang2javaXSL.xsl")));
			transformer.transform(new StreamSource(updatedFilePath), new StreamResult(javasFile));
			Util.splitJavaContent(javasFile, outputDir);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
	}


}
