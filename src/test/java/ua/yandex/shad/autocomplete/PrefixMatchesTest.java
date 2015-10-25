/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.yandex.shad.autocomplete;

import java.util.Arrays;
import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vasyl
 */
public class PrefixMatchesTest {
    
    public PrefixMatchesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of load method, of class PrefixMatches.
     */
    @Test
    public void testLoad() {
        System.out.println("load");
        PrefixMatches instance = new PrefixMatches();
        int expResult = 6;
        int result = instance.load("hello world in the", "ololo try a i lock");
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }

    /**
     * Test of contains method, of class PrefixMatches.
     */
    @Test
    public void testContains_expectTrue() {
        System.out.println("contains");
        String word = "abc";
        PrefixMatches instance = new PrefixMatches();
        instance.load("hello world abc cdz");
        boolean expResult = true;
        boolean result = instance.contains(word);
        assertEquals(expResult, result);
    }
    @Test
     public void testContains_expectFalse() {
        System.out.println("contains");
        String word = "abc";
        PrefixMatches instance = new PrefixMatches();
        instance.load("hello world abcd cdz");
        boolean expResult = false;
        boolean result = instance.contains(word);
        assertEquals(expResult, result);
    }

    /**
     * Test of delete method, of class PrefixMatches.
     */
    @Test
    public void testDelete_elementNotExist() {
        System.out.println("delete");
        String word = "world";
        PrefixMatches instance = new PrefixMatches();
        instance.load("hello abc cde");
        boolean expResult = false;
        boolean result = instance.delete(word);
        assertEquals(expResult, result);
    }

     @Test
    public void testDelete_manyElements() {
        System.out.println("delete");
        String word = "world";
        PrefixMatches instance = new PrefixMatches();
        
        instance.load("hello world abc cde");
        boolean expResult = true;
        boolean result = instance.delete(word);
        assertEquals(expResult, result);
        expResult = false;
        result = instance.contains(word);
        assertEquals(expResult, result);
    }
    /**
     * Test of wordsWithPrefix method, of class PrefixMatches.
     */
    @Test
    public void testWordsWithPrefix_String_manyElements() {
        System.out.println("wordsWithPrefix");
        String pref = "abc";
        PrefixMatches instance = new PrefixMatches();
        instance.load("hello world abc cde", "abcset", "abchel", "abctrie", 
                "abcd", "abcde","abcdefgh","abce");
        Iterable<String> expResult = new LinkedList<String>(Arrays.asList("abc",
                "abcd", "abce","abcde"));
        Iterable<String> result = instance.wordsWithPrefix(pref);
        LinkedList<String> listExp = (LinkedList) expResult;
        LinkedList<String> listRes = (LinkedList) result;
        assertListsEquals(listExp, listRes);
        // TODO review the generated test code and remove the default call to fail.
      //  fail("The test case is a prototype.");
    }

    @Test
    public void testWordsWithPrefix_String_int_manyElements() {
        System.out.println("wordsWithPrefix");
        String pref = "abc";
        PrefixMatches instance = new PrefixMatches();
        instance.load("hello world abc cde", "abcset", "abchel", "abctrie", 
                "abcd", "abcde","abcdefgh","abce");
        Iterable<String> expResult = new LinkedList<String>(Arrays.asList("abc",
                "abcd", "abce","abcde", "abcset", "abchel"));
        Iterable<String> result = instance.wordsWithPrefix(pref,4);
        LinkedList<String> listExp = (LinkedList) expResult;
        LinkedList<String> listRes = (LinkedList) result;
        assertListsEquals(listExp, listRes);
        // TODO review the generated test code and remove the default call to fail.
      //  fail("The test case is a prototype.");
    }
    /**
     * Test of wordsWithPrefix method, of class PrefixMatches.
     */ /*
    @Test
    public void testWordsWithPrefix_String_int() {
        System.out.println("wordsWithPrefix");
        String pref = "";
        int k = 0;
        PrefixMatches instance = new PrefixMatches();
        Iterable<String> expResult = null;
        Iterable<String> result = instance.wordsWithPrefix(pref, k);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of size method, of class PrefixMatches.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        PrefixMatches instance = new PrefixMatches();
        instance.load("hello world ab abc", "sunny", "vasa uiy op");
        int expResult = 6;
        int result = instance.size();
        assertEquals(expResult, result);

    }
    
     public static void assertListsEquals(LinkedList<String> listExp,
            LinkedList<String> listRes) throws AssertionError {
         for(String element : listRes) {
             System.out.println(element);
         }
        if (listExp.size() != listRes.size()) {
            throw new AssertionError("Lists have different length : expected "
                    + Integer.toString(listExp.size()) + " and result was "
                    + Integer.toString(listRes.size()));
        }
        for (String element : listExp) {
            if (!(listRes.contains(element))) {
                throw new AssertionError("Result list does not contain " + 
                        element);
            }
        }
        
    }
    
    
}
