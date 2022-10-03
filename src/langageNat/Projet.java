package langageNat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Projet {

	
	// Récupère le fichier csv des relations ou des mots reliés du mot indiqué par l'utilisateur
	public static File getFile(String mot, String type) {
		if(type == "relation") {
			String ficMotRel = mot + "Rel.csv";
			File CSVFileRel = new File("/Users/marie-lou/eclipse-workspace/langageNat/" + ficMotRel);

			if(CSVFileRel.length() == 0) { //Si le fichier est vide (le fichier n'existait pas avant
				System.out.println("Le fichier de ce mot n'existe pas !");
				System.exit(-1);
			}
			
			return CSVFileRel;
		}
		
		if(type == "mot") {
			String ficMot = mot + "Mot.csv";
			File CSVFileMot = new File("/Users/marie-lou/eclipse-workspace/langageNat/" + ficMot);
			
			if(CSVFileMot.length() == 0) { //Si le fichier est vide (le fichier n'existait pas avant
				System.out.println("Le fichier de ce mot n'existe pas !");
				System.exit(-1);

			}
			
			return CSVFileMot;
		}
		
		return null;
		
	}
	
	
		
	// Prend le fichier relation du mot 1 ou 2 et met l'id du mot et de la relation dans un tableau
	public static ArrayList<String> tri(File f, String id, int numMot) throws IOException{
		ArrayList<String> al = new ArrayList<String>();
		
		//Met l'id du mot et le numéro de la relation dans un tableau
	    BufferedReader CSVFile = new BufferedReader(new FileReader(f));
	    String dataRow = CSVFile.readLine();
	    while (dataRow != null)
	    {
	        String[] dataArray1 = dataRow.split(";");
	        
	        if(numMot == 1) { //Si c'est le mot 1
	        	//Si la 4ème colonne ne contient pas le mot 1
		        if(!dataArray1[3].equals(id)) {
		        	String node2 = dataArray1[3];
		        	String type = dataArray1[4];
		        	
		        	//Même si le tableau contient déjà le mot c'est pas grave parce qu'il peut avoir une relation différente
		            al.add(node2);
		            al.add(type);
		        	
		        }
	        }
	        
	        if(numMot == 2) {
	        	if(!dataArray1[2].equals(id)) {
		        	String node2 = dataArray1[2];
		        	String type = dataArray1[4];

		        	al.add(node2);
		        	al.add(type);
		        	
		        }
	        }

	        dataRow = CSVFile.readLine();
	    }

	    CSVFile.close();
		
		return al;
		
	}
	
	
	// Compare les 2 fichiers et donne la liste des id des mots et du type de relation en communs
	public static ArrayList<String> compare(ArrayList<String> al1, ArrayList<String> al2) throws IOException {
		//Tableau des mots en communs
		ArrayList<String> communs=new ArrayList<String>();
	     
	     for(String element:al1) { //Pour chaque mot dans la liste du mot 1
	    	 if(al2.contains(element) && !communs.contains(element) && (al1.indexOf(element)%2 == 0)) { //Si le deuxième fichier contient aussi cet élément et que c'est bien un node2
		    	 communs.add(element);
		     }
	     }
	     
	     //System.out.println("Tableau des mots communs : " + communs);
	     
	     return communs;
	     
	    	 
	}
	
	public static ArrayList<String> significationMots(ArrayList<String> alCommuns, File f) throws IOException {
		//Trier le fichier pour avoir l'id des mots et leur sigification
		ArrayList<String> mots = new ArrayList<String>();
		
		BufferedReader CSVFile = new BufferedReader(new FileReader(f));
	    String dataRow = CSVFile.readLine();
	    while (dataRow != null){
	        String[] dataArray = dataRow.split(";");
	        
	        String eid = dataArray[1];
        	String name = dataArray[2];
        	
        	if(name == null || name.contains(">")) { //Ne pas prendre le premier mot (eid)
        		name = dataArray[5];
        	}
        	
        	mots.add(eid);
        	mots.add(name);
        	
	        dataRow = CSVFile.readLine();
	    }
	    CSVFile.close();

	    //System.out.println("Tableau des mots du premier fichier : " + mots);
	     
	     
	    //Pour chaque mot de la liste des mots communs, faire une autre liste avec leur traduction
	    ArrayList<String> alTrad = new ArrayList<String>();
	     
	    for(String el: alCommuns) {
	    		int place = mots.indexOf(el);
	    		String trad = mots.get(place + 1);
	    		alTrad.add(trad);
	     }
	     //System.out.println("Tableau des mots communs : " + alCommuns);
	     //System.out.println("Traduction des mots communs : " + alTrad);
	     //System.out.println("Taille de la liste de mots communs : " + alCommuns.size());
	     //System.out.println("Taille de leur traduction : " + alTrad.size());

	     
	     return alTrad;
	}
	
	
	//Fonction qui trouve l'id d'un mot
	public static String findID(File f) throws IOException {
		
		BufferedReader CSVFile = new BufferedReader(new FileReader(f));
	    String dataRow = CSVFile.readLine();
	    
	    //On passe à la deuxième ligne parce que la première c'est l'entête
	    dataRow = CSVFile.readLine();
	    
	    //On fait un tableau avec la première ligne
	    String[] dataArray1 = dataRow.split(";");
        
        String id = dataArray1[1];
	    
		
		CSVFile.close();
		return id;
		
	}
	
	public static int analyseback(ArrayList<String> nomRel, String relation, File CSVFile1Rel, File CSVFile2Rel, File CSVFile2Mot, String id1, String id2, int compteurReponses, String mot1, String mot2) throws IOException {
		
		//System.out.println("Je suis dans l'analyse back");
		ArrayList<String> alMot1 = tri(CSVFile2Rel, id2, 1);
		
		//System.out.println("Tableau du premier mot : " + alMot1);
		
	    ArrayList<String> alMot2 = tri(CSVFile1Rel, id1, 2);
		
	    //System.out.println("Tableau du deuxième mot : " + alMot2);
	    
	    ArrayList<String> communs = compare(alMot1, alMot2);
	    
	    ArrayList<String> trad = significationMots(communs, CSVFile2Mot);
	    
	    int indexR = nomRel.indexOf(relation);
		String RelID = Integer.toString(indexR);
		
		for(String motCom:communs) { //Pour chaque mot commun
			//System.out.println("L'id du mot en commun est : " + motCom);


			if(compteurReponses < 50) {
				//Pour avoir la traduction du mot en commun
				int indexMotCom = communs.indexOf(motCom);
				String motTrad = trad.get(indexMotCom);
				//System.out.println("Le mot commun est : " + motTrad);



				int index1 = -1;
				int index2 = -1;

				ArrayList<String> alMot2copie = new ArrayList<String>();

				
				while(alMot1.contains(motCom)) { //Tant qu'il y a des occurences du mot en commun dans la liste des mots 1

					//On cherche l'index de ce mot commun dans le tableau du mot 1
					index1 = alMot1.indexOf(motCom);


					//On crée une copie de la liste du mot 2 pour pouvoir la réinitialiser à chaque tour
					alMot2copie = alMot2;

					while(alMot2copie.contains(motCom)) {
						//On cherche l'index de ce mot commun dans le tableau du mot 2
						index2 = alMot2.indexOf(motCom);
						//System.out.println("Index du mot en commun dans le tableau du mot 1 : " + index1);
						//System.out.println("Index du mot en commun dans le tableau du mot 2 : " + index2);


						//On regarde le type de relation que le mot en commun a avec les mots 1 et 2
						String RelID1 = alMot1.get(index1+1);
						String RelID2 = alMot2.get(index2+1);
						//System.out.println("id de la relation avec le mot 1 : " + RelID1);
						//System.out.println("id de la relation avec le mot 2 : " + RelID2);
					


						if(relation.equals("r_isa")) {
							/* 1ère manière : A r_isa B r_isa C (inférence transitive)*/
							if(RelID1.equals(RelID) && RelID2.equals(RelID)) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " " + relation + " " + motTrad + " " + relation + " " + mot2);
							}
							
							/* 2ème manière : A r_holo B r_isa C (inférence )
							if(RelID1.equals("10") && RelID2.equals(RelID)) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " r_holo " + motTrad + " " + relation + " " + mot2);
							}*/

						}
						
						if(relation.equals("r_holo")) {
							/* 1ère manière : A r_holo B r_holo C */
							if(RelID1.equals(RelID) && RelID2.equals(RelID)) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " " + relation + " " + motTrad + " " + relation + " " + mot2);
							}

						}
						
						if(relation.equals("r_hypo")) {
							/* 1ère manière : A r_holo B r_holo C */
							if(RelID1.equals(RelID) && RelID2.equals(RelID)) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " " + relation + " " + motTrad + " " + relation + " " + mot2);
							}
							
							if(RelID1.equals("6") && RelID2.equals(RelID)) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + "r_isa " + motTrad + " " + relation + " " + mot2);
							}

						}


						if(relation.equals("r_carac")) {
							/* 1ère manière : A r_isa B r_carac C */
							if(RelID1.equals("6") && RelID2.equals(RelID)) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " r_isa " + motTrad + " " + relation + " " + mot2);
							}

						}
						
						if(relation.equals("r_carac-1")) {
							/* 1ère manière : A r_isa B r_carac C */
							if(RelID1.equals(RelID) && RelID2.equals("6")) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " " + relation + " " + motTrad + " r_isa " + mot2);
							}

						}
						
						if(relation.equals("r_agent")) {
							/* 1ère manière : A r_agent B r_isa C (inférence déductive) */
							if(RelID1.equals(RelID) && RelID2.equals("6")) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " " + relation + " " + motTrad + " r_isa " + mot2);
							}
									
						}

						if(relation.equals("r_agent-1")) {
							/* 1ère manière : A r_isa B r_agent-1 C (inférence déductive) */
							if(RelID1.equals("6") && RelID2.equals(RelID)) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " r_isa " + motTrad + " " + relation + " " + mot2);
							}

						}

						if(relation.equals("r_patient")) {
							/* 1ère manière : A r_patient B r_isa C (inférence déductive) */
							if(RelID1.equals(RelID) && RelID2.equals("6")) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " " + relation + " " + motTrad +  " r_isa " + mot2);
							}

						}
						
						if(relation.equals("r_patient-1")) {
							/* 1ère manière : A r_patient B r_isa C (inférence déductive) */
							if(RelID1.equals("6") && RelID2.equals(RelID)) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " r_isa " + motTrad +  " " + relation + " " + mot2);
							}

						}
						
						if(relation.equals("r_lieu")) {
							/* 1ère manière : A r_lieu B r_lieu C (inférence transitive)*/
							if(RelID1.equals(RelID) && RelID2.equals(RelID)) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " " + relation + " " + motTrad + " " + relation + " " + mot2);
							}

							
						}
						
						if(relation.equals("r_lieu-1")) {
							/* 1ère manière : A r_lieu B r_lieu C (inférence transitive)*/
							if(RelID1.equals(RelID) && RelID2.equals(RelID)) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " " + relation + " " + motTrad + " " + relation + " " + mot2);
							}
							
						}
						
						if(relation.equals("r_domain")) {
							/* 1ère manière : A r_lieu B r_lieu C (inférence transitive)*/
							if(RelID1.equals(RelID) && RelID2.equals(RelID)) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " " + relation + " " + motTrad + " " + relation + " " + mot2);
							}
							
						}
						
						alMot2copie.set(index2, "x"); //On remplace le mot par un x pour qu'il ne le prenne plus en compte

					}

					//On supprime le mot de la liste pour voir s'il y en a d'autre d'identique
					alMot1.set(index1, "x");

				}
			}
			

		}
		
		return compteurReponses;
		
	}
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws IOException {
		
		InputStreamReader isr = new InputStreamReader(System.in); //Pour pouvoir utiliser l'entrée clavier
		BufferedReader in = new BufferedReader(isr);
		
		
		/* 1. Récupérer les 2 fichiers csv du premier mot */
		System.out.print("Donnez le premier mot: ");
		String mot1 = in.readLine();
		
		File CSVFile1Rel = getFile(mot1, "relation");
		File CSVFile1Mot = getFile(mot1, "mot");
		
		//System.out.println("~~ Fichiers du premier mot récupérés ~~");
		System.out.println("   ");

		
		
		/* 2. Connaitre la relation voulu entre les deux mots */	
		System.out.println("Donnez la relation parmis");
		System.out.println(" r_isa |  r_carac  |  r_agent  |  r_patient  |  r_lieu  |  r_domain | r_holo | r_can_eat");
		System.out.print("r_hypo | r_carac-1 | r_agent-1 | r_patient-1 | r_lieu-1 | r_domain-1 : ");

		String relation = in.readLine();
		if(!(relation.equals("r_isa") || relation.equals("r_hypo") || relation.equals("r_carac") || relation.equals("r_carac-1") || relation.equals("r_agent-1") || relation.equals("r_patient") || relation.equals("r_patient-1") || relation.equals("r_lieu") || relation.equals("r_lieu-1") || relation.equals("r_holo") || relation.equals("r_agent") || relation.equals("r_domain") || relation.equals("r_domain-1") || relation.equals("r_can_eat"))) {
			System.out.println("Choisissez une relation parmis la liste précédente !");
			System.exit(-1); //On sort du programme
		}
		//System.out.println(relation);
		System.out.println("   ");

		
		
		
		/* 3. Récupérer les 2 fichiers csv du deuxième mot */
 		System.out.print("Donnez le deuxième mot: ");
 		String mot2 = in.readLine();
 		
 		File CSVFile2Rel = getFile(mot2, "relation");
 		File CSVFile2Mot = getFile(mot2, "mot");
 	
		//System.out.println("~~ Fichiers du deuxième mot récupérés~~");
 		System.out.println("   ");
 		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("   ");

		
		
		/* 4. La liste des relations avec leur nom */
		ArrayList<String> nomRel = new ArrayList<String>();
		
		nomRel.add(0,"r_associated");
		nomRel.add(1,"r_raff_sem");
		nomRel.add(2, "r_raff_morpho");
		nomRel.add(3, "r_domain");
		nomRel.add(4, "r_pos");
		nomRel.add(5, "r_syn");
		nomRel.add(6, "r_isa");
		nomRel.add(7, "r_anto");
		nomRel.add(8, "r_hypo");
		nomRel.add(9, "r_has_part");
		nomRel.add(10, "r_holo");
		nomRel.add(11, "r_locution");
		nomRel.add(12, "r_flpot");
		nomRel.add(13, "r_agent");
		nomRel.add(14, "r_patient");
		nomRel.add(15, "r_lieu");
		nomRel.add(16, "r_instr");
		nomRel.add(17, "r_carac");
		nomRel.add(18, "r_data");
		nomRel.add(19, "r_lemma");
		nomRel.add(20, "r_has_magn");
		nomRel.add(21, "r_has_antimagn");
		nomRel.add(22, "r_family");
		nomRel.add(23, "r_carac-1");
		nomRel.add(24, "r_agent-1");
		nomRel.add(25, "r_instr-1");
		nomRel.add(26, "r_patient-1");
		nomRel.add(27, "r_domain-1");
		nomRel.add(28, "r_lieu-1");
		nomRel.add(29, "x");
		nomRel.add(30, "x");
		nomRel.add(31, "x");
		nomRel.add(32, "x");
		nomRel.add(33, "x");
		nomRel.add(34, "x");
		nomRel.add(35, "x");
		nomRel.add(36, "x");
		nomRel.add(37, "x");
		nomRel.add(38, "x");
		nomRel.add(39, "x");
		nomRel.add(40, "x");
		nomRel.add(41, "x");
		nomRel.add(42, "x");
		nomRel.add(43, "x");
		nomRel.add(44, "x");
		nomRel.add(45, "x");
		nomRel.add(46, "x");
		nomRel.add(47, "x");
		nomRel.add(48, "x");
		nomRel.add(49, "x");
		nomRel.add(50, "x");
		nomRel.add(51, "x");
		nomRel.add(52, "x");
		nomRel.add(53, "x");
		nomRel.add(54, "x");
		nomRel.add(55, "x");
		nomRel.add(56, "x");
		nomRel.add(57, "x");
		nomRel.add(58, "x");
		nomRel.add(59, "x");
		nomRel.add(60, "x");
		nomRel.add(61, "x");
		nomRel.add(62, "x");
		nomRel.add(63, "x");
		nomRel.add(64, "x");
		nomRel.add(65, "x");
		nomRel.add(66, "x");
		nomRel.add(67, "x");
		nomRel.add(68, "x");
		nomRel.add(69, "x");
		nomRel.add(70, "x");
		nomRel.add(71, "x");
		nomRel.add(72, "x");
		nomRel.add(73, "x");
		nomRel.add(74, "x");
		nomRel.add(75, "x");
		nomRel.add(76, "x");
		nomRel.add(77, "x");
		nomRel.add(78, "x");
		nomRel.add(79, "x");
		nomRel.add(80, "x");
		nomRel.add(81, "x");
		nomRel.add(82, "x");
		nomRel.add(83, "x");
		nomRel.add(84, "x");
		nomRel.add(85, "x");
		nomRel.add(86, "x");
		nomRel.add(87, "x");
		nomRel.add(88, "x");
		nomRel.add(89, "x");
		nomRel.add(90, "x");
		nomRel.add(91, "x");
		nomRel.add(92, "x");
		nomRel.add(93, "x");
		nomRel.add(94, "x");
		nomRel.add(95, "x");
		nomRel.add(96, "x");
		nomRel.add(97, "x");
		nomRel.add(98, "x");
		nomRel.add(99, "x");
		nomRel.add(100, "x");
		nomRel.add(101, "x");
		nomRel.add(102, "r_can_eat");
		
		/*
		int indexR = nomRel.indexOf(relation);
		String RelID = Integer.toString(indexR);
		
		//System.out.println("Le numéro de la relation est : " + RelID);
		
		*/
		
		/* 5. Récupérer l'id des mots */
		
		String id1 = findID(CSVFile1Mot);

		//System.out.println("Id du mot 1 : " + id1);
		
		String id2 = findID(CSVFile2Mot);

		//System.out.println("Id du mot 2 : " + id2);
		
		
		
		/* 6. Tableau qui contient id du mot + numéro de relation pour chaque mot */
		
		ArrayList<String> alMot1 = tri(CSVFile1Rel, id1, 1);
		
		//System.out.println("Tableau du premier mot : " + alMot1);
		
	    ArrayList<String> alMot2 = tri(CSVFile2Rel, id2, 2);
		
	    //System.out.println("Tableau du deuxième mot : " + alMot2);
	    
	    
	    
	    
	    
		/* 7. Trouver l'id des mots en communs aux deux fichiers */
		
		ArrayList<String> communs= compare(alMot1, alMot2);
		
		
		
		
		
		/* 8. Création du tableau qui contient le nom des mots d'une liste en français */
		
		ArrayList<String> trad = significationMots(communs, CSVFile1Mot);
		
		
		
		
		/* 9. Créer un compteur pour pouvoir afficher le nombre de réponses */
		
		int compteurReponses = 0;
		
		System.out.println(mot1 + " " + relation + " " + mot2 + " :");
		
		/*
		boolean fromRelationback = false;
		
		compteurReponses = analyse(communs, trad, alMot1, alMot2, relation, compteurReponses, mot1, mot2, fromRelationback);
		*/
			
		int indexR = nomRel.indexOf(relation);
		String RelID = Integer.toString(indexR);

		
		/* 10. Traitement des différents type de relations */
		
		
		// pour avoir le type de relation on cherche l'index du mot dans le tableau de base et on fait +1)
			
		for(String motCom:communs) { //Pour chaque mot commun
			//System.out.println("L'id du mot en commun est : " + motCom);

			if(compteurReponses < 25) {
				//Pour avoir la traduction du mot en commun
				int indexMotCom = communs.indexOf(motCom);
				String motTrad = trad.get(indexMotCom);
				//System.out.println("Le mot commun est : " + motTrad);



				int index1 = -1;
				int index2 = -1;

				ArrayList<String> alMot2copie = new ArrayList<String>();

				while(alMot1.contains(motCom)) { //Tant qu'il y a des occurences du mot en commun dans la liste des mots 1

					//On cherche l'index de ce mot commun dans le tableau du mot 1
					index1 = alMot1.indexOf(motCom);


					//On crée une copie de la liste du mot 2 pour pouvoir la réinitialiser à chaque tour
					alMot2copie = alMot2;

					while(alMot2copie.contains(motCom)) {
						//On cherche l'index de ce mot commun dans le tableau du mot 2
						index2 = alMot2.indexOf(motCom);
						//System.out.println("Index du mot en commun dans le tableau du mot 1 : " + index1);
						//System.out.println("Index du mot en commun dans le tableau du mot 2 : " + index2);


						//On regarde le type de relation que le mot en commun a avec les mots 1 et 2
						String RelID1 = alMot1.get(index1+1);
						String RelID2 = alMot2.get(index2+1);
						//System.out.println("id de la relation avec le mot 1 : " + RelID1);
						//System.out.println("id de la relation avec le mot 2 : " + RelID2);
					


						if(relation.equals("r_isa")) {
							/* 1ère manière : A r_isa B r_isa C (inférence transitive)*/
							if(RelID1.equals(RelID) && RelID2.equals(RelID)) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " " + relation + " " + motTrad + " " + relation + " " + mot2);
							}
							
							/* 2ème manière : A r_holo B r_isa C (inférence )
							if(RelID1.equals("10") && RelID2.equals(RelID)) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " r_holo " + motTrad + " " + relation + " " + mot2);
							}*/

						}
						
						if(relation.equals("r_holo")) {
							/* 1ère manière : A r_holo B r_holo C */
							if(RelID1.equals(RelID) && RelID2.equals(RelID)) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " " + relation + " " + motTrad + " " + relation + " " + mot2);
							}

						}
						
						if(relation.equals("r_hypo")) {
							/* 1ère manière : A r_holo B r_holo C */
							if(RelID1.equals(RelID) && RelID2.equals(RelID)) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " " + relation + " " + motTrad + " " + relation + " " + mot2);
							}
							
							if(RelID1.equals("6") && RelID2.equals(RelID)) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + "r_isa " + motTrad + " " + relation + " " + mot2);
							}

						}


						if(relation.equals("r_carac")) {
							/* 1ère manière : A r_isa B r_carac C */
							if(RelID1.equals("6") && RelID2.equals(RelID)) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " r_isa " + motTrad + " " + relation + " " + mot2);
							}

						}
						
						if(relation.equals("r_carac-1")) {
							/* 1ère manière : A r_isa B r_carac C */
							if(RelID1.equals(RelID) && RelID2.equals("6")) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " " + relation + " " + motTrad + " r_isa " + mot2);
							}

						}
						
						if(relation.equals("r_agent")) {
							/* 1ère manière : A r_agent B r_isa C (inférence déductive) */
							if(RelID1.equals(RelID) && RelID2.equals("6")) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " " + relation + " " + motTrad + " r_isa " + mot2);
							}
									
						}

						if(relation.equals("r_agent-1")) {
							/* 1ère manière : A r_isa B r_agent-1 C (inférence déductive) */
							if(RelID1.equals("6") && RelID2.equals(RelID)) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " r_isa " + motTrad + " " + relation + " " + mot2);
							}

						}

						if(relation.equals("r_patient")) {
							/* 1ère manière : A r_patient B r_isa C (inférence déductive) */
							if(RelID1.equals(RelID) && RelID2.equals("6")) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " " + relation + " " + motTrad +  " r_isa " + mot2);
							}

						}
						
						if(relation.equals("r_patient-1")) {
							/* 1ère manière : A r_patient B r_isa C (inférence déductive) */
							if(RelID1.equals("6") && RelID2.equals(RelID)) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " r_isa " + motTrad +  " " + relation + " " + mot2);
							}

						}
						
						if(relation.equals("r_lieu")) {
							/* 1ère manière : A r_lieu B r_lieu C (inférence transitive)*/
							if(RelID1.equals(RelID) && RelID2.equals(RelID)) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " " + relation + " " + motTrad + " " + relation + " " + mot2);
							}

							
						}
						
						if(relation.equals("r_lieu-1")) {
							/* 1ère manière : A r_lieu B r_lieu C (inférence transitive)*/
							if(RelID1.equals(RelID) && RelID2.equals(RelID)) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " " + relation + " " + motTrad + " " + relation + " " + mot2);
							}
							
						}
						
						if(relation.equals("r_domain")) {
							/* 1ère manière : A r_lieu B r_lieu C (inférence transitive)*/
							if(RelID1.equals(RelID) && RelID2.equals(RelID)) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " " + relation + " " + motTrad + " " + relation + " " + mot2);
							}
							
						}
						
						if(relation.equals("r_domain-1")) {
							/* 1ère manière : A r_lieu B r_lieu C (inférence transitive)*/
							if(RelID1.equals(RelID) && RelID2.equals(RelID)) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " " + relation + " " + motTrad + " " + relation + " " + mot2);
							}
							
						}
						
						if(relation.equals("r_can_eat")) {
							/* 1ère manière : A r_lieu B r_lieu C (inférence transitive)*/
							if(RelID1.equals(RelID) && RelID2.equals("6")) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " " + relation + " " + motTrad + " r_isa " + mot2);
							}
							
							if(RelID1.equals(RelID) && RelID2.equals("8")) {
								compteurReponses++;
								System.out.println(" -> " + mot1 + " " + relation + " " + motTrad + " r_hypo " + mot2);
							}
							
						}
						
						
						alMot2copie.set(index2, "x"); //On remplace le mot par un x pour qu'il ne le prenne plus en compte

					}

					//On supprime le mot de la liste pour voir s'il y en a d'autre d'identique
					alMot1.set(index1, "x");

				}
			}

			

		}
		
		if(relation.equals("r_agent")) {
			compteurReponses = analyseback(nomRel, "r_agent-1", CSVFile1Rel, CSVFile2Rel, CSVFile2Mot, id1, id2, compteurReponses, mot2, mot1);
		}
		
		if(relation.equals("r_agent-1")) {
			compteurReponses = analyseback(nomRel, "r_agent", CSVFile1Rel, CSVFile2Rel, CSVFile2Mot, id1, id2, compteurReponses, mot2, mot1);
		}
		
		if(relation.equals("r_lieu")) {
			compteurReponses = analyseback(nomRel, "r_lieu-1", CSVFile1Rel, CSVFile2Rel, CSVFile2Mot, id1, id2, compteurReponses, mot2, mot1);
		}
		
		if(relation.equals("r_lieu-1")) {
			compteurReponses = analyseback(nomRel, "r_lieu", CSVFile1Rel, CSVFile2Rel, CSVFile2Mot, id1, id2, compteurReponses, mot2, mot1);
		}
		
		if(relation.equals("r_hypo")) {
			compteurReponses = analyseback(nomRel, "r_isa", CSVFile1Rel, CSVFile2Rel, CSVFile2Mot, id1, id2, compteurReponses, mot2, mot1);
		}
		
		if(relation.equals("r_isa")) {
			compteurReponses = analyseback(nomRel, "r_hypo", CSVFile1Rel, CSVFile2Rel, CSVFile2Mot, id1, id2, compteurReponses, mot2, mot1);
		}
		
		if(relation.equals("r_carac")) {
			compteurReponses = analyseback(nomRel, "r_carac-1", CSVFile1Rel, CSVFile2Rel, CSVFile2Mot, id1, id2, compteurReponses, mot2, mot1);
		}
		
		if(relation.equals("r_carac-1")) {
			compteurReponses = analyseback(nomRel, "r_carac", CSVFile1Rel, CSVFile2Rel, CSVFile2Mot, id1, id2, compteurReponses, mot2, mot1);
		}
		
		if(relation.equals("r_patient-1")) {
			compteurReponses = analyseback(nomRel, "r_patient", CSVFile1Rel, CSVFile2Rel, CSVFile2Mot, id1, id2, compteurReponses, mot2, mot1);
		}
		
		if(relation.equals("r_patient")) {
			compteurReponses = analyseback(nomRel, "r_patient-1", CSVFile1Rel, CSVFile2Rel, CSVFile2Mot, id1, id2, compteurReponses, mot2, mot1);
		}

		System.out.println("      ");

		
		//Afficher le nombre de réponses
		if (compteurReponses == 0) {
			System.out.println("Il n'y a aucune réponse pour votre requête.");
		} else if (compteurReponses == 1) {
			System.out.println("Il y a une réponse.");
		} else {
			System.out.println("Il y a " + compteurReponses + " réponses.");
		}
 		
     
	}
	 

}
