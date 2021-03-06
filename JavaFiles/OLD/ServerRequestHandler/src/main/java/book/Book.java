package book;

import java.security.SecureRandom;
import java.util.Random;

import dataManager.BookData;
import dataManager.BookQuery;

/**
 * 
 * @author Michele
 * 
 * String format received from client:
 * - TITLE:.....;
 * - AUTHOR:.....;
 * - YEAR:.....;
 * - EDITION:.....;
 * - TYPE:.....;
 *
 */

public class Book {

    private String title;
	private String author;
    private int yearOfPubblication;
    private int editionNumber;
    private BookType type;
    private String BCID;
    private String ISBN;
    private String proprietario;
    
    public String getProprietario() {
		return proprietario;
	}


	public void setProprietario(String proprietario) {
		this.proprietario = proprietario;
	}


	public Book(String msg) {	
    	this();
    	String lines[] = msg.split(";");
        this.title = getTitleFromString(lines[0]);
        this.author = getAuthorFromString(lines[1]);
        this.yearOfPubblication = getYearOfPubblicationFromString(lines[2]);
        this.editionNumber = getEditionNumberFromString(lines[3]);
        this.type = getBookTypeFromString(lines[4]);
    }
    
    
    public Book(String title, String author, int yearOfPubblication, int editionNumber, BookType type) {
        this();
    	this.title = title;
        this.author = author;
        this.yearOfPubblication = yearOfPubblication;
        this.editionNumber = editionNumber;
        this.type = type;
    }
    
    public Book() {
    	do {
    		BCID = generateBCID();
    	}while(!BookData.getInstance().isBCIDavailable(BCID));
    }
    
    
    public boolean insert() {
    	return BookData.getInstance().insertBook(this);
    }
    
    
    public String getAuthor() {

        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {

        return title;
    }

    public String getBCID() {
		return BCID;
	}


	public void setBCID(String bCID) {
		BCID = bCID;
	}


	public String getISBN() {
		return ISBN;
	}


	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}


	public void setTitle(String title) {
        this.title = title;
    }
    
    public int getYearOfPubblication() {
		return yearOfPubblication;
	}

	public void setYearOfPubblication(int yearOfPubblication) {
		this.yearOfPubblication = yearOfPubblication;
	}

	public int getEditionNumber() {
		return editionNumber;
	}

	public void setEditionNumber(int editionNumber) {
		this.editionNumber = editionNumber;
	}

	public BookType getType() {
		return type;
	}

	public void setType(BookType type) {
		this.type = type;
	}
	
	private String getTitleFromString(String msg) {
		String words[] = msg.split(":");
		return words[1];
	}
	
	private String getAuthorFromString(String msg) {
		String words[] = msg.split(":");
		return words[1];
	}
	
	private Integer getYearOfPubblicationFromString(String msg) {
		String words[] = msg.split(":");
		if (words[1].equalsIgnoreCase("null")) {
			return null;
		} else {
			return Integer.parseInt(words[1]);
		}
	}
	
	private Integer getEditionNumberFromString(String msg) {
		String words[] = msg.split(":");
		if (words[1].equalsIgnoreCase("null")) {
			return null;
		} else {
			return Integer.parseInt(words[1]);
		}
	}
	
	private BookType getBookTypeFromString(String msg) {
		String words[] = msg.split(":");
		return BookType.valueOf(words[1]);
	}
	
	
	
	
	public String generateBCID() {
		
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		String generatedString = buffer.toString();
		return generatedString;
	}
	
	
}
