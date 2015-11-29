/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.yandex.shad.autocomplete;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vasyl
 */
public class PrefixMatchesTest {

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

    @Test
    public void testDelete_manyElementsNotExist() {
        System.out.println("delete");
        String word = "world";
        PrefixMatches instance = new PrefixMatches();

        instance.load("hello worsdld abc cde");
        boolean expResult = false;
        boolean result = instance.delete(word);
        assertEquals(expResult, result);
    }

    @Test
    public void testDelete_manyElementsTestNotDeleteDeep() {
        System.out.println("delete");
        String word = "world";
        PrefixMatches instance = new PrefixMatches();

        instance.load("hello world abc cde worldwerer wor");
        boolean expResult = true;
        boolean result = instance.delete(word);
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
                "abcd", "abcde", "abcdefgh", "abce");
        Iterable<String> expResult = new LinkedList<String>(Arrays.asList("abc",
                "abcd", "abce", "abcde"));
        Iterable<String> result = instance.wordsWithPrefix(pref);
        LinkedList<String> listExp = (LinkedList) expResult;
        LinkedList<String> listRes = new LinkedList<>();
        while (result.iterator().hasNext()) {
            listRes.add(result.iterator().next());
        }
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
                "abcd", "abcde", "abcdefgh", "abce");
        Iterable<String> expResult = new LinkedList<String>(Arrays.asList("abc",
                "abcd", "abce", "abcde", "abcset", "abchel"));
        Iterable<String> result = instance.wordsWithPrefix(pref, 4);
        LinkedList<String> listExp = (LinkedList) expResult;
        LinkedList<String> listRes = new LinkedList<>();
        while (result.iterator().hasNext()) {
            listRes.add(result.iterator().next());
        }
        boolean a = result.iterator().hasNext();
        if(a) {
            fail("HasNext Error : expected false");
        }
        assertListsEquals(listExp, listRes);
    }

    @Test
    public void testWordsWithPrefix_String_int_bigCounter() {
        System.out.println("wordsWithPrefix");
        String pref = "abc";
        PrefixMatches instance = new PrefixMatches();
        instance.load("hello world abc cde", "abcset");
        Iterable<String> expResult = new LinkedList<String>(Arrays.asList("abc",
                "abcset"));
        Iterable<String> result = instance.wordsWithPrefix(pref, 15);
        LinkedList<String> listExp = (LinkedList) expResult;
        LinkedList<String> listRes = new LinkedList<>();
        while (result.iterator().hasNext()) {
            listRes.add(result.iterator().next());
        }
        assertListsEquals(listExp, listRes);
        // TODO review the generated test code and remove the default call to fail.
        //  fail("The test case is a prototype.");
    }

    
        @Test(expected = NoSuchElementException.class)
        public void testWordsWithPrefix_String_int_iterableTest() {
        System.out.println("wordsWithPrefix");
        String pref = "abc";
        PrefixMatches instance = new PrefixMatches();
        instance.load("hello world abc cde", "abcset");
        Iterable<String> expResult = new LinkedList<String>(Arrays.asList("abc",
                "abcset"));
        Iterable<String> result = instance.wordsWithPrefix(pref, 15);
        LinkedList<String> listExp = (LinkedList) expResult;
        LinkedList<String> listRes = new LinkedList<>();
        result.iterator().hasNext();
        result.iterator().hasNext();
        while (result.iterator().hasNext()) {
            listRes.add(result.iterator().next());
        }
        result.iterator().hasNext();
        boolean a = result.iterator().hasNext();
        if(a) {
            throw new AssertionError("Iterable hasNext fail : expected false");
        }
        result.iterator().next();
    }
        
         @Test(expected = NoSuchElementException.class)
        public void testWordsWithPrefix_String_int_iterableTestShortPrefixes() {
        System.out.println("wordsWithPrefix");
        String pref = "abc";
        PrefixMatches instance = new PrefixMatches();
        instance.load("hello", "world" ,"abc", "cde", "absd", "sfgsrgr",
                "sdgf", "f", "fdfd", "abcset", "abababa");
        Iterable<String> expResult = new LinkedList<String>(Arrays.asList("abc",
                "abcset"));
        Iterable<String> result = instance.wordsWithPrefix(pref, 3);
        LinkedList<String> listExp = (LinkedList) expResult;
        LinkedList<String> listRes = new LinkedList<>();
        result.iterator().hasNext();
        result.iterator().hasNext();
        while (result.iterator().hasNext()) {
            listRes.add(result.iterator().next());
        }
        result.iterator().hasNext();
        result.iterator().hasNext();
        result.iterator().hasNext();
        boolean a = result.iterator().hasNext();
        if(a) {
            throw new AssertionError("Iterable hasNext fail : expected false");
        }
        result.iterator().next();
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
        for (String element : listRes) {
            System.out.println(element);
        }
        if (listExp.size() != listRes.size()) {
            throw new AssertionError("Lists have different length : expected "
                    + Integer.toString(listExp.size()) + " and result was "
                    + Integer.toString(listRes.size()));
        }
        for (String element : listExp) {
            if (!(listRes.contains(element))) {
                throw new AssertionError("Result list does not contain "
                        + element);
            }
        }

    }

}
