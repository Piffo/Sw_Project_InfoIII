package com.bc.bookcrossing.src.ClientModels;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test della classe Book.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class BookTest {

    @Test
    public void testBookCreationByMsgOnlyTitle() {
        String msg = "TITLE:A;AUTHOR: ;YEAR: ;EDITION: ;TYPE: ;USER: ;ISBN: ;STATE: ;BCID: ;";
        Book b = new Book(msg);

        assertEquals(b.getTitle(),"A");
        assertNotEquals(b.getTitle(),"dddd");
    }

    @Test
    public void testBookCreationByNotCompleteMsg1() {
        String msg = "";
        // Missing ISBN field
        msg = "TITLE:A;AUTHOR:;YEAR:;EDITION:;TYPE:;USER:aa;STATE:;BCID:;";
        Book b = new Book(msg);

        assertEquals(b.getISBN(), null);
        assertEquals(b.getTitle(),"A");
        assertEquals(b.getUser(),"aa");
    }

    @Test
    public void testBookCreationByNotCompleteMsg2() {
        String msg = "";
        // Missing ISBN field
        msg = "TITLE:A;AUTHOR:;YEAR:;USER:bb;STATE:;BCID:;";
        Book b = new Book(msg);

        assertEquals(b.getISBN(), null);
        assertEquals(b.getEditionNumber(), null);
        assertEquals(b.getType(), null);
        assertEquals(b.getTitle(),"A");
        assertEquals(b.getUser(),"bb");
    }

    @Test
    public void testNullFields() {
        Book b = new Book();
        Integer edition = b.getEditionNumber();
        Integer year = b.getYearOfPubblication();

        assertEquals(edition,null);
        assertEquals(year,null);
    }

    @Test
    public void testSetISBN() {
        Book b = new Book();
        b.setISBN("A");

        assertEquals(b.getISBN(),"A");
        assertNotEquals(b.getISBN(),"dddd");
    }

    @Test
    public void testGetISBN() {
        Book b = new Book();
        b.setISBN("A");

        assertEquals(b.getISBN(),"A");
        assertNotEquals(b.getISBN(),"dddd");
    }

    @Test
    public void testSetBCID() {
        Book b = new Book();
        b.setBCID("A");

        assertEquals(b.getBCID(),"A");
        assertNotEquals(b.getBCID(),"dddd");
    }

    @Test
    public void testSetBCIDNotInitialized() {
        Book b = new Book();

        assertEquals(b.getBCID(),"");
        assertNotEquals(b.getBCID(),"dddd");
    }

    @Test
    public void testGetBCID() {
        Book b = new Book();
        b.setBCID("A");

        assertEquals(b.getBCID(),"A");
        assertNotEquals(b.getBCID(),"dddd");
    }

    @Test
    public void testSetAuthor() {
        Book b = new Book();
        b.setAuthor("A");

        assertEquals(b.getAuthor(),"A");
        assertNotEquals(b.getAuthor(),"dddd");
    }

    @Test
    public void testGetAuthor() {
        Book b = new Book();
        b.setAuthor("A");

        assertEquals(b.getAuthor(),"A");
        assertNotEquals(b.getAuthor(),"dddd");
    }

    @Test
    public void testSetTitle() {
        Book b = new Book();
        b.setTitle("A");

        assertEquals(b.getTitle(),"A");
        assertNotEquals(b.getTitle(),"dddd");
    }

    @Test
    public void testGetTitle() {
        Book b = new Book();
        b.setTitle("A");

        assertEquals(b.getTitle(),"A");
        assertNotEquals(b.getTitle(),"dddd");
    }

    @Test
    public void testSetYearOfPubblication() {
        Book b = new Book();
        b.setYearOfPubblication(1);

        assertEquals(b.getYearOfPubblication().intValue(), 1);
        assertNotEquals(b.getYearOfPubblication().intValue(),2);
    }

    @Test
    public void testGetYearOfPubblication() {
        Book b = new Book();
        b.setYearOfPubblication(1);

        assertEquals(b.getYearOfPubblication().intValue(),1);
        assertNotEquals(b.getYearOfPubblication().intValue(),2);
    }

    @Test
    public void testSetEditionNumber() {
        Book b = new Book();
        b.setEditionNumber(1);

        assertEquals(b.getEditionNumber().intValue(),1);
        assertNotEquals(b.getEditionNumber().intValue(),2);
    }

    @Test
    public void testGetEditionNumber() {
        Book b = new Book();
        b.setEditionNumber(1);

        assertEquals(b.getEditionNumber().intValue(),1);
        assertNotEquals(b.getEditionNumber().intValue(),2);
    }

    @Test
    public void testSetType() {
        Book b = new Book();
        b.setType("1");

        assertEquals(b.getType(),"1");
        assertNotEquals(b.getType(),"2");
    }

    @Test
    public void testGetType() {
        Book b = new Book();
        b.setType("1");

        assertEquals(b.getType(),"1");
        assertNotEquals(b.getType(),"2");
    }

    @Test
    public void testEncode() {
        Book b = new Book();
        b.setTitle("A");

        assertEquals(b.encode(),"TITLE:A;AUTHOR:null;YEAR:null;EDITION:null;TYPE:null;USER:null;ISBN:null;STATE:0;BCID:");
        assertNotEquals(b.encode(),"TITLE:A;AUTHOR:;YEAR:45;EDITION: ;TYPE:;USER:;ISBN:;STATE:;BCID:;");
    }
}