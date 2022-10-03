package langageNat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
//import java.net.URLConnection;
import java.nio.file.Paths;

public class ImportSite {
	
	//Fonction qui vérifie que l'URL rentrée existe bien
	public static boolean urlExists(String url)
	   {
	        try {
	            URL site = new URL(url);
	            try {
	                site.openStream();
	                return true;
	            } catch (IOException ex) {
	                return false;
	            }
	        } catch (MalformedURLException ex) {
	            return false;
	        }
	   }
	
	//Fonction qui récupère le code de la page de l'URL
	public static String getCode(String url)
	   {
	       String code = "";

	        if(urlExists(url))
	        {
	                BufferedReader in = null;

	                try
	                {
	                    URL site = new URL(url);
	                    
	                    in = new BufferedReader(new InputStreamReader(site.openStream(), "ISO-8859-1"));

	                    String inputLine;
	                    while ((inputLine = in.readLine()) != null)
	                    {
	                        code = code + "\n" + (inputLine);
	                    }

	                    in.close();

	                }
	                catch (IOException ex)
	                {
	                    System.out.println("Erreur dans l'ouverture de l'URL : " + ex);
	                    
	                }
	                finally
	                {
	                    try
	                    {
	                        in.close();
	                    }
	                    catch (IOException ex)
	                    {
	                        System.out.println("Erreur dans la fermeture du buffer : " + ex);
	                    }
	                }
	        }
	        else
	        {
	           System.out.println("Le site n'existe pas !");
	        }
	       return code;
	    }
	
	//Fonction qui nettoie le fichier texte pour ne garder que les relations
	public static boolean cleanRelation(String fileName) {
		
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
 
            StringBuffer sb = new StringBuffer();
            String line;    
                 
            while ((line = reader.readLine()) != null) {
            	// Si la ligne commence par r (et pas par rt) on la garde
            	int index = line.indexOf("r");
            	int indexRT = line.indexOf("rt");
            	if(index == 0 && indexRT < 0) {
            		sb.append(line + "\n");
            	}
            }
            reader.close();
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            out.write(sb.toString());
            out.close();
            System.out.println("Fichier relation nettoyé");
 
        } catch (Exception e) {
            return false;
        }
        return true;
    }
	
	//Fonction qui nettoie le fichier texte pour ne garder que les mots
	public static boolean cleanMot(String fileName) {
		
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
 
            StringBuffer sb = new StringBuffer();
            String line;    
                 
            while ((line = reader.readLine()) != null) {
            	
            	int index = line.indexOf("e");
            	
            	if(index == 0) {
            		sb.append(line + "\n");
            	}
            }
            reader.close();
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            out.write(sb.toString());
            out.close();
            System.out.println("Fichier mots nettoyé");
 
        } catch (Exception e) {
            return false;
        }
        return true;
    }

	
	public static void main(String[] args) throws IOException {
		
		//Récupérer le contenu de l'URL
		InputStreamReader isr3 = new InputStreamReader(System.in); //Pour pouvoir utiliser l'entrée clavier
		BufferedReader in3 = new BufferedReader(isr3);
		
		System.out.println("Donnez le mot:");
		String mot = in3.readLine();
		
		String ficTxt = mot + ".txt";
		String ficRelTxt = mot + "Rel.txt";
		String ficRelCSV = mot + "Rel.csv";
		String ficMotTxt = mot + "Mot.txt";
		String ficMotCSV = mot + "Mot.csv";
		
		System.out.println("Donnez l'URL:");
		String url = in3.readLine();
	
		String code = getCode(url);
		
		if (code != "") {
			//L'enregistrer dans un fichier txt
			File outFile1 = new File(ficTxt);
			File outFile2 = new File(ficRelTxt);
			File outFile3 = new File(ficMotTxt);
			
			
			FileWriter fileTxt = new FileWriter(outFile1);
			FileWriter fileRelTxt = new FileWriter(outFile2);
			FileWriter fileMotTxt = new FileWriter(outFile3);
			
			fileTxt.write(code);
			fileRelTxt.write(code);
			fileMotTxt.write(code);
			
			fileTxt.close();
			fileRelTxt.close();
			fileMotTxt.close();
			
			System.out.println("Code complet enregistré dans un fichier .txt");
		
			cleanRelation(ficRelTxt);
			
			System.out.println("Fichier texte avec les relations créé");
			cleanMot(ficMotTxt);
			
			System.out.println("Fichier texte avec les mots créé");
			
			
			//L'enregistrer dans un fichier CSV
			
			//Délimiteurs qui doivent être dans le fichier CSV
			//final String DELIMITER = ";";
		    final String SEPARATOR = "\n";
		    
		    //En-tête de fichier
		    final String HEADERR = "r;rid;node1;node2;type;w";
		    final String HEADERM = "e;eid;'name';type;w;'formated name'";
		    
		    
			String contenuRelTxt = new String(Files.readAllBytes(Paths.get(ficRelTxt)));
			
			File outFile = new File(ficRelCSV);
			FileWriter fileCSV = new FileWriter(outFile);
			
			//Ajouter l'en-tête
	        fileCSV.append(HEADERR);
	        //Ajouter une nouvelle ligne après l'en-tête
	        fileCSV.append(SEPARATOR);
	        
			fileCSV.write(contenuRelTxt);
			
			fileCSV.close();
			
			System.out.println("Fichier csv avec les relations créé");
			
			//Créer fichier avec les mots
			String contenuMotTxt = new String(Files.readAllBytes(Paths.get(ficMotTxt)));
			
			File outFile4 = new File(ficMotCSV);
			FileWriter fileCSVMot = new FileWriter(outFile4);
			
			//Ajouter l'en-tête
	        fileCSVMot.append(HEADERM);
	        //Ajouter une nouvelle ligne après l'en-tête
	        fileCSVMot.append(SEPARATOR);
	        
			fileCSVMot.write(contenuMotTxt);
			
			fileCSVMot.close();
			
			System.out.println("Fichier csv avec les mots créé");
		
		}
		
	}

}
