/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.yandex.shad.autocomplete;

import java.util.Iterator;
import java.util.NoSuchElementException;
import ua.yandex.shad.tries.RWayTrie;
import ua.yandex.shad.tries.Tuple;
import ua.yandex.shad.tries.Trie;

/**
 *
 * @author andrii
 */
public class PrefixMatches {

    public static final int PREFLIMIT = 3;
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

    public static class LimitedBfsIterable implements Iterable<String> {

        private final Iterable core;
        private final int lenLimit;
        private int lenCount;
        private String stackString;
        private int lastLen;

        public LimitedBfsIterable(Iterable core, int lenLimit) {
            this.core = core;
            this.lenLimit = lenLimit;
            this.lenCount = -1;
        }

        @Override
        public Iterator iterator() {
            return new LimitedBfsIterator();
        }

        public class LimitedBfsIterator implements Iterator<String> {

            @Override
            public boolean hasNext() {
                if (lenLimit <= lenCount) {
                    return false;
                }
                if (stackString == null) {
                    if (core.iterator().hasNext()) {
                        stackString = (String) core.iterator().next();
                        if (stackString.length() != lastLen) {
                            lenCount++;
                            lastLen = stackString.length();
                        }
                        return lenLimit > lenCount;
                    }
                    return false;
                }
                return true;
            }

            @Override
            public String next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                String tmp = stackString;
                stackString = null;
                return tmp;

            }
        }

    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return wordsWithPrefix(pref, PREFLIMIT);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        return new LimitedBfsIterable(trie.wordsWithPrefix(pref), k);
    }

    public int size() {
        return trie.size();
    }
}
