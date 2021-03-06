package preprocessor;

import model.Feature;
import model.Response;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Arces
 */
public class DescriptorIO {
	/*
	public void readQuestions(ArrayList<Feature> varQuestions, ArrayList<Feature> valQuestions, String variableLabel, String valuesLabel) {
        BufferedReader varBr, valBr;
        String line;
        String[] lineSplit;
        Feature question = null;
        Response response;
        boolean readResponses = false;

        try {
            varBr = new BufferedReader(new FileReader(new File(variableLabel)));
            valBr = new BufferedReader(new FileReader(new File(valuesLabel)));

            //Get Questions from variable file
            while ((line = varBr.readLine()) != null) {
                if (line.contains("=")) {
                    lineSplit = line.split("="); 
                    question = new Feature(); //Make a new variable
                    question.setCode(lineSplit[0].trim());//Set the variable code
                    
                    question.setDescription(lineSplit[1].trim().replace("\"", "").replace(",", ";"));//Set variable meaning
                    
                    varQuestions.add(question);//Add to list of questions from variable file               
                }
            }
            
            //Get Questions from value file
            while ((line = valBr.readLine()) != null) {
            	if(line.contains("value"))
            	{            		
            		question = new Feature(); //Make a new variable
                    valQuestions.add(question);//Add to list of questions from values file                   
                    lineSplit = line.split(" ");      
                    question.setCode(lineSplit[1]);//Set the variable code
                    System.out.println("value " + question.getCode());
                    readResponses = true;//Start reading the possible responses to this variable. 
            	} else if (readResponses) {
            		response = new Response();//Make a new response
                    //line = line.replace("\t", "");//Remove tab indentation
            		line = line.trim();
            		
                    System.out.println("Line before: " + line);
                    lineSplit = line.split(":");//Split the line by its delimiter, ":"
                    String newLine = "";
                    for(int i = 0; i < lineSplit.length; i++)
                    {
                    	newLine = newLine + ", " + lineSplit[i];
                    }
                    System.out.println("Line after: " + newLine);
                    
                    response.setGroup(lineSplit[0].trim());//Set group code   

                    
                    
                    if (line.contains(";")) {//If all responses are read
                        readResponses = false;//Stop reading responses for that variable 
                    }
                    
                    
                    
                    /*
                     * 
                     * 
                     * 
                    
                    
                           
                    //Split the rest of the string by = to get the response code and meaning
                    String[] lineSplit2 = lineSplit[1].split("=");
                    
                    
                    response.setKey(lineSplit2[0].trim());//Set response code       
                    //Set response meaning
                    response.setDescription(lineSplit2[1].replace("\"", "").replace(";", "").replace(",", ";"));              
                    
                    question.addResponse(response);//Add response to the variable it responds to
                    
                    
                    
            	}
            }
            
            varBr.close();
            valBr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
	*/
    
    public void readQuestions(ArrayList<Feature> varQuestions, ArrayList<Feature> valQuestions, String variableLabel, String valuesLabel) {
        BufferedReader varBr, valBr;
        String line;
        String[] lineSplit;
        Feature question = null;
        Response response;
        boolean readResponses = false;

        try {
            varBr = new BufferedReader(new FileReader(new File(variableLabel)));
            valBr = new BufferedReader(new FileReader(new File(valuesLabel)));

            //Get Questions from variable file
            while ((line = varBr.readLine()) != null) {
                if (line.contains("=")) {
                    lineSplit = line.split("="); 
                    question = new Feature(); //Make a new variable
                    question.setCode(lineSplit[0].trim());//Set the variable code
                    
                    question.setDescription(lineSplit[1].trim().replace("\"", "").replace(",", ";"));//Set variable meaning
                    
                    varQuestions.add(question);//Add to list of questions from variable file               
                } 
            }
            
            //Get Questions from value file
            while ((line = valBr.readLine()) != null) {
            	System.out.println(line);
                if (line.contains("value")) {//If the line contains the variable code which is written after the word "value"
                	
                	
                	question = new Feature(); //Make a new variable
                    valQuestions.add(question);//Add to list of questions from values file
                    
                    question.setCode(line.split(" ")[1]);//Set the variable code
//                    System.out.println("value " + question.getCode());
                    readResponses = true;//Start reading the possible responses to this variable. 
                } else if (readResponses) {//If responses are being read within a variable
                    response = new Response();//Make a new response
                    line = line.replace("\t", "");//Remove tab indentation
                    lineSplit = line.split(":");//Split the line by its delimiter, ":"
         
                    response.setGroup(lineSplit[0].trim());//Set group code   
                    
                    //Split the rest of the string by = to get the response code and meaning
                    String[] lineSplit2 = lineSplit[1].split("=");
                    
                    response.setKey(lineSplit2[0].trim());//Set response code       
                    //Set response meaning
                    response.setDescription(lineSplit2[1].replace("\"", "").replace(";", "").replace(",", ";"));              
                    
                    question.addResponse(response);//Add response to the variable it responds to
                    if (line.contains(";")) {//If all responses are read
                        readResponses = false;//Stop reading responses for that variable 
                    }
                }
            }
            
            varBr.close();
            valBr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void exportVariables(ArrayList<Feature> questionList, String filePath, String varMark) {
     //   System.out.println("\n********\nEXPORTING VARIABLES\n*******");
        //String export = "";
        PrintWriter pw;
        try {
            pw = new PrintWriter(new File(filePath));
        
	        for (Feature q : questionList) {
	       //     System.out.println("Exporting " + q.getCode() + ". . .");        	
//	        	export += 
	        	pw.write(varMark+"," + q.getCode() + "," + q.getDescription() + "\n");
	            for (Response r : q.getResponseList()) {
	                //export += 
	            	pw.write(r.getGroup() + "," + r.getKey() + "," + r.getDescription() + "\n");
	            }
	        }
        
            //pw.write(export);

            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
