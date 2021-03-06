package test;

import static org.junit.Assert.*;

import org.junit.Test;

import book.Book;
import dataManager.BookData;

public class BookDataTest {

	@Test
	public void insertBookTest() {
		String bookEncode = "TITLE:" + "CasoDiTest" + ";" +
				"AUTHOR:" + "JUNIT" + ";" +
				"YEAR:" + 2019 + ";" +
				"EDITION:" + 1 + ";" +
				"TYPE:" + "Test" + ";" +
				"USER:" + "A" + ";" +
				"ISBN:" + "" + ";" +
				"STATE:" + false + ";" +
				"BCID:" + "";

		Book b = new Book(bookEncode);
		assertTrue(BookData.getInstance().insertBook(b));
		System.out.println(b.getBCID());
		b.setActualOwnerUsername("XA");
		assertFalse(BookData.getInstance().insertBook(b));	
	}
	
	@Test
	public void searchByTitleTest() {
		String title = "CasoDiTest";
		assertTrue(!Book.searchBookByTitle(title).isEmpty());
		assertTrue(Book.searchBookByTitle(title).size() > 0);
	}
	
	@Test
	public void searchByAuthorTest() {
		String author = "JUNIT";
		assertTrue(!Book.searchBookByAuthor(author).isEmpty());
		assertTrue(Book.searchBookByAuthor(author).size() > 0);
	}
	
	@Test
	public void searchBookTest() {
		String title = "CasoDiTest";
		String author = "JUNIT";
		assertTrue(!Book.searchBook(title, author).isEmpty());
		assertTrue(Book.searchBook(title, author).size() > 0);
	}

}
