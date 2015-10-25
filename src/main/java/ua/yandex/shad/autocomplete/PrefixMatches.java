/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.yandex.shad.autocomplete;

import java.util.LinkedList;
import ua.yandex.shad.tries.RWayTrie;
import ua.yandex.shad.tries.Tuple;
import ua.yandex.shad.tries.Trie;

/**
 *
 * @author andrii
 */
public class PrefixMatches {

    private Trie trie;

    public PrefixMatches() {
        trie = new RWayTrie();
    }

    public int load(String... strings) {
        int counter = 0;
        for (String element : strings) {
            String[] arr = element.split(" ");
            for (String word : arr) {
                if (word.length() > 2) {
                    trie.add(new Tuple(word, 1));
                    counter++;
                }
            }
        }
        return counter;
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return wordsWithPrefix(pref, 3);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        LinkedList<String> values = (LinkedList) trie.wordsWithPrefix(pref);
        LinkedList<String> result = new LinkedList<String>();
        int counter = k - 1;
        String prev = values.peekFirst();
        for (String element : values) {
            if (prev.length() < element.length()) {
                counter--;
            }
            if (counter < 0) {
                return result;
            }
            
            result.add(element);
            prev = element;
        }
        return result;
    }

    public int size() {
        return trie.size();
    }
}
