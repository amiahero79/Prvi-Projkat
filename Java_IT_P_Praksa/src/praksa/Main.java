package praksa;


import java.util.*;
import java.util.regex.*;

public class Main {

	public static void main(String[] args) {

		//definisao karaktere kao novi array
List<Character> characters = new ArrayList<>();
		
       //pročitaj meta fajl
		String metadata = FileHelper.loadMetaData();
		
		//podeli meta fajl na linije, izvuci svakum pojedinačnu liniju. Izvlači redove iz csv fajla
		String[] lines = metadata.split(System.lineSeparator());
		
		//kreni od prve linije, preskačemo 0 tj. naslove
		for(int i = 1; i < lines.length; i++) {
			
			//izvlači kolone, splituj po zarezu, svaki zarez kraj kolone
			String[] string = lines[i].split(",");
			
			
			//for(int j = 1; j < string.length; j++)
			String name = string[0].trim();
			String allegiance = string[1].trim();
			String messagesFile = string[2].trim();
			
			//za svaki red pravim novi objekat(po karakteru)
			Character character = new Character(name, allegiance, messagesFile);
			//dodaje svaki objekat u listu objekata
			characters.add(character);
		}
		//provera
		System.out.println("Provera:" + characters); 
		//hoću sve denerisine
		System.out.println("All Daenerys` meggases: ");
		
		for(Character character : characters) {
			//izvlačimo poruku sa imenom iz got_meta_data
			if(character.getName().contains("Daenerys")) {
				//da li fajl postoji
				for(int i = 1; i < character.getMessageLines().size(); i++)
					//štampaj metodu, vraćamo broj redova i get(i) je za jedan po jedan red prilikom štampe
					System.out.print(character.getMessageLines().get(i) + "\n");
			}
		}
		
		System.out.println();
        //listam osobe
		System.out.println("Number of messages by character: ");
		for(Character character : characters) {
			int msgC = 0;
			//brojim redove po fajlovima
			for(int i = 1; i < character.getMessageLines().size(); i++) {
				if(character.getMessageLines().get(i).isBlank() || character.getMessageLines().get(i).isEmpty())
					continue;
				else
					msgC++;
			}
			
			System.out.println(character.getName() + ": " + msgC);
		}
		
		System.out.println();
		
		for(Character character : characters) {			
			int happyCounter = 0;
			int sadCounter = 0;
			//u promenljivu message čitam fajlove i dodajem sadržaj fajla
			for(String message : character.getMessageLines()) {

				if(message.matches(".*[😄😊🙂😍].*"))// java matches metoda
					happyCounter++;				
				
				if(message.matches(".*[😢😭👿😞].*"))
					sadCounter++;				
			}
			
			character.setHappySmileys(happyCounter);
			character.setSadSmileys(sadCounter);
			
			if (character.disposition() > 0) 
				System.out.println(character.getName() + " is more happy than sad.");
			
			else if (character.disposition() < 0)
				System.out.println(character.getName() + " is more sad than happy.");
			
			else
				System.out.println(character.getName() + " is equally sad and happy.");	
		}
		
		Character happiest = new Character();		
		Character saddest = new Character();
		
		int max = happiest.disposition();
		int min = saddest.disposition();
		
		for(Character c : characters) {
			if (c.disposition() > max) {
				max = c.disposition();
				happiest = c;
			}
			
			if (c.disposition() < min) {
				min = c.disposition();
				saddest = c;
			}
		}
		
		System.out.println("\nThe happiest character is: " + happiest.getName() + " of " + happiest.getAllegiance() + ".");
		
		System.out.println("The saddest character is: " + saddest.getName() + " of " + saddest.getAllegiance() + ".");
		
		int jonLovesDanyCount = 0;
		int danyLovesJonCount = 0;
		
		for(Character character : characters) {
			
			// hoću da u Patternu sadržim Džon ili Deneris
			Pattern pattern = Pattern.compile("^Jon.*|^Daenerys.*"); //klasa Pattern
			
			for(String message : character.getMessageLines()) {
				
				Matcher matcher = pattern.matcher(message); //poruke u kojima su samo Džon i Deneris
				
				if(message.matches(".*[😍😘].*") && matcher.matches()) {		//samo one koje imaju ljubavne smajliće		
					if(character.getName().contains("Daenerys"))
						danyLovesJonCount++;
					if(character.getName().contains("Jon"))
							jonLovesDanyCount++;	
				}
			}
		}
		
		if(jonLovesDanyCount > danyLovesJonCount)
			System.out.println("\nJon loves Daenerys more than Daenerys loves Jon.");
		
		else if(jonLovesDanyCount < danyLovesJonCount)
			System.out.println("\nDaenerys loves Jon more than Jon loves Daenerys.");
		
		else
			System.out.println("\nDaenerys and Jon love each other equally.");	
		
	}

}
