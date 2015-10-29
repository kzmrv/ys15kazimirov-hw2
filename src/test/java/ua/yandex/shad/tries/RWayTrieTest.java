/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.yandex.shad.tries;

import com.sun.java.accessibility.util.EventID;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Iterator;
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
public class RWayTrieTest {

    /**
     * Test of contains method, of class RWayTrie.
     */
    @Test
    public void testContains_OneElement() {
        System.out.println("contains");
        String word = "abc";
        int weight = 10;
        RWayTrie instance = new RWayTrie();
        instance.add(new Tuple(word, weight));
        boolean expResult = true;
        boolean result = instance.contains(word);
        assertEquals(expResult, result);
    }

    @Test
    public void testContains_ExpectFalse() {
        System.out.println("contains");
        String word = "abc";
        int weight = 10;
        RWayTrie instance = new RWayTrie();
        instance.add(new Tuple("cde", weight));
        instance.add(new Tuple("cba", weight));
        instance.add(new Tuple("abcd", weight));
        boolean expResult = false;
        boolean result = instance.contains(word);
        assertEquals(expResult, result);
    }

    @Test
    public void testContains_ExpectTrue() {
        System.out.println("contains");
        String word = "abc";
        int weight = 10;
        RWayTrie instance = new RWayTrie();
        instance.add(new Tuple("cde", weight));
        instance.add(new Tuple("cba", weight));
        instance.add(new Tuple("abcd", weight));
        instance.add(new Tuple("abc", weight));
        boolean expResult = true;
        boolean result = instance.contains(word);
        assertEquals(expResult, result);
    }

    @Test
    public void testDelete_deleteElement() {
        System.out.println("delete");
        String word = "abc";
        int weight = 10;
        RWayTrie instance = new RWayTrie();
        instance.add(new Tuple("cde", weight));
        instance.add(new Tuple("cba", weight));
        instance.add(new Tuple("abcd", weight));
        instance.add(new Tuple("abc", weight));
        boolean expResult = true;
        boolean result = instance.delete(word);
        assertEquals(expResult, result);
    }

    @Test
    public void testDelete_deleteNotExistingElement() {
        System.out.println("delete");
        String word = "abcde";
        int weight = 10;
        RWayTrie instance = new RWayTrie();
        instance.add(new Tuple("cde", weight));
        instance.add(new Tuple("cba", weight));
        instance.add(new Tuple("abcd", weight));
        instance.add(new Tuple("abcdez", weight));
        instance.add(new Tuple("abcdeshs", weight));
        instance.add(new Tuple("abc", weight));
        boolean expResult = false;
        boolean result = instance.delete(word);
        assertEquals(expResult, result);
    }

    /**
     * Test of words method, of class RWayTrie.
     */
    @Test
    public void testWords() {
        System.out.println("words");
        RWayTrie instance = new RWayTrie();
        instance.add(new Tuple("vasa", 1));
        instance.add(new Tuple("sadqwe", 1));
        instance.add(new Tuple("etgsad", 1));
        instance.add(new Tuple("sad", 1));
        instance.add(new Tuple("sadabc", 1));
        Iterable<String> expResult = new LinkedList<String>
        (Arrays.asList("etgsad", "sad",
                "sadabc", "sadqwe", "vasa"));
        Iterable<String> result = instance.words();
        LinkedList<String> listExp = (LinkedList) expResult;
        LinkedList<String> listRes = (LinkedList) result;
        assertListsEquals(listExp, listRes);
    }

    /**
     * Test of wordsWithPrefix method, of class RWayTrie.
     */
    
    public static void assertListsEquals(LinkedList<String> listExp,
            LinkedList<String> listRes) throws AssertionError {
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
    
    @Test
    public void testWordsWithPrefix_manyElements() {
        System.out.println("wordsWithPrefix");
        String s = "sad";
        RWayTrie instance = new RWayTrie();
        instance.add(new Tuple("sadqwe", 1));
        instance.add(new Tuple("etgsad", 1));
        instance.add(new Tuple("sad", 1));
        instance.add(new Tuple("sadabc", 1));
        Iterable<String> expResult = new LinkedList<String>(Arrays.asList("sad",
                "sadabc", "sadqwe"));
        Iterable<String> result = instance.wordsWithPrefix(s);
        LinkedList<String> listExp = (LinkedList) expResult;
        LinkedList<String> listRes = (LinkedList) result;
        assertListsEquals(listExp, listRes);
        
    }
    
    
     @Test
    public void testWordsWithPrefix_expectedEmpty() {
        System.out.println("wordsWithPrefix");
        String s = "opuf";
        RWayTrie instance = new RWayTrie();
        instance.add(new Tuple("sadqwe", 1));
        instance.add(new Tuple("etgsad", 1));
        instance.add(new Tuple("sad", 1));
        instance.add(new Tuple("sadabc", 1));
        Iterable<String> expResult = new LinkedList<String>();
        Iterable<String> result = instance.wordsWithPrefix(s);
        LinkedList<String> listExp = (LinkedList) expResult;
        LinkedList<String> listRes = (LinkedList) result;
        assertListsEquals(listExp, listRes);
        
    }

    

    @Test
    public void testSize_zeroSize() {
        System.out.println("size");
        RWayTrie instance = new RWayTrie();
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
    }

    @Test
    public void testSize_oneElement() {
        System.out.println("size");
        RWayTrie instance = new RWayTrie();
        instance.add(new Tuple("zax", 1));
        int expResult = 1;
        int result = instance.size();
        assertEquals(expResult, result);
    }

    @Test
    public void testSize_manyElements() {
        System.out.println("size");
        RWayTrie instance = new RWayTrie();
        instance.add(new Tuple("zax", 1));
        instance.add(new Tuple("zaxettr", 1));
        instance.add(new Tuple("zaxiot", 1));
        instance.add(new Tuple("abc", 1));
        instance.add(new Tuple("abcdefg", 1));
        instance.add(new Tuple("oiy", 1));
        int expResult = 6;
        int result = instance.size();
        assertEquals(expResult, result);
    }

}
