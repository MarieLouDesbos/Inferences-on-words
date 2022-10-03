package langageNat;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Brouillon {
	
	
	// Met les infos du fichier csv rentré en paramètre dans un tableau
	public static String[][] putRelationsInArray(File f){
		Scanner sc = null;
		try {
			sc = new Scanner(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    sc.useDelimiter(";");
	   
        //Initialise un compteur de lignes
	    int compteurLigne = 0;
        
	    
        int nbLigne = 0;
        while(sc.hasNextLine()) {
        	nbLigne++;
        }
        
        final int nbColonnes = 6;
        
        
        // Initialise un tableau pour mettre les infos qui nous interressent
        String[][] infosRelationMot = new String[nbLigne][nbColonnes];
	    
        while(sc.hasNextLine()) {
	    	String csvLigne = sc.nextLine();
	    	
	    	if (!csvLigne.isEmpty()) {
	    		// Initialise un scanner pour interpréter la ligne.
                Scanner ligneScan = new Scanner(csvLigne);
                ligneScan.useDelimiter(";");
             
                
                // Lis chaque champ de la ligne
                String relation = ligneScan.next().trim();
                String rid = ligneScan.next().trim();
                String node1 = ligneScan.next().trim();
                String node2 = ligneScan.next().trim();
                String type = ligneScan.next().trim();
                String w = ligneScan.next().trim();
                
                // Rentre les infos dans un tableau
                infosRelationMot[compteurLigne][0] = relation;
                infosRelationMot[compteurLigne][1] = rid;
                infosRelationMot[compteurLigne][2] = node1;
                infosRelationMot[compteurLigne][3] = node2;
                infosRelationMot[compteurLigne][4] = type;
                infosRelationMot[compteurLigne][5] = w;
                
                
                compteurLigne++;
                
                
                ligneScan.close();
                          
	    	}
	    	
	    }
        
	    sc.close();
	    
	    return infosRelationMot;
	}
	
	
	
	// Met les infos du fichier csv rentré en paramètre dans un tableau
	public static String[][] putMotsInArray(File f){
		Scanner sc = null;
		try {
			sc = new Scanner(f);			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    sc.useDelimiter(";");
		   
	    //Initialise un compteur de lignes
	    int compteurLigne = 0;
	        	    
	    int nbLigne = 0;
	    while(sc.hasNextLine()) {
        	nbLigne++;
        }
	        
	    final int nbColonnes = 6;
	        
	        
	    
        // Initialise un tableau pour mettre les infos qui nous interressent
	    String[][] infosMot = new String[nbLigne][nbColonnes];
		    
	    while(sc.hasNextLine()) {
	    	String csvLigne = sc.nextLine();
	    	
		   	if (!csvLigne.isEmpty()) {
		   		// Initialise un scanner pour interpréter la ligne.
		   		Scanner ligneScan = new Scanner(csvLigne);
		   		ligneScan.useDelimiter(";");
	             
	                
	            // Lis chaque champ de la ligne
	            String element = ligneScan.next().trim();
	            String eid = ligneScan.next().trim();
	            String name = ligneScan.next().trim();
	            String type = ligneScan.next().trim();
	            String w = ligneScan.next().trim();
	            String formatedname = ligneScan.next().trim();
	                
	            // Rentre les infos dans un tableau
	            infosMot[compteurLigne][0] = element;
	            infosMot[compteurLigne][1] = eid;
	            infosMot[compteurLigne][2] = name;
	            infosMot[compteurLigne][3] = type;
	            infosMot[compteurLigne][4] = w;
	            infosMot[compteurLigne][5] = formatedname;
	                
	                
	            compteurLigne++;
	                
	                
	            ligneScan.close();
	                	          
		    }
		   	
	    }
	        
	    sc.close();
		    
	    return infosMot;
	}

}
