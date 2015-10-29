package ua.yandex.shad.tries;

import java.util.LinkedList;

public class RWayTrie implements Trie {

    public static final int ALPHABET_SIZE = 26;
    private Node root = new Node();

    private static class Node {

        public Node() {
            next = new Node[ALPHABET_SIZE];
            weight = 0;
        }

        public Node(char inKey, int inWeight) {
            key = inKey;
            weight = inWeight;
            next = new Node[ALPHABET_SIZE];
        }

        public void setWeight(int inWeight) {
            weight = inWeight;
        }
        
        private char key;
        private int weight;
        private Node[] next;
    }

    @Override
    public void add(Tuple tuple) {
        add(root, tuple, 0);
    }

    public void add(Node node, Tuple tuple, int position) {
        String term = tuple.getTerm();
        int weight = tuple.getWeight();
        int wordLength = term.length();
        if (position == wordLength) {
            node.setWeight(weight);
            return;
        }
        int insertPos = (int) term.charAt(position) - 'a';
        if (node.next[insertPos] == null) {
            node.next[insertPos] = new Node(term.charAt(position), 0);
        }
        add(node.next[(int) term.charAt(position) - 'a'],
                tuple, position + 1);
    }

    private Node iterateToLast(Node node, String word) {
        Node current = node;
        for (int i = 0; i < word.length(); i++) {
            if (current.next[word.charAt(i) - 'a'] == null) {
                return null;
            }
            current = current.next[word.charAt(i) - 'a'];
        }
        return current;
    }

    @Override
    public boolean contains(String word) {
        Node current = iterateToLast(root, word);
        if (current == null) {
            return false;
        }
        return current.weight != 0;
    }

    private Node delete(Node node, String word, int position) {
        if (position == word.length()) {
            node.weight = 0;
        } else {
            int c = word.charAt(position) - 'a';
            node.next[c] = delete(node.next[c], word, position + 1);
        }
        if (node.weight != 0) {
            return node;
        }
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (node.next[i] != null) {
                return node;
            }
        }
        return null;

    }

    @Override
    public boolean delete(String word) {
        if (!contains(word)) {
            return false;
        }
        root = delete(root, word, 0);
        return true;
    }

    @Override
    public Iterable<String> words() {
        return getWords(root);
    }

    private Iterable<String> getWords(Node node) {
        LinkedList<String> result = new LinkedList<String>();
        LinkedList<Node> activeNodes = new LinkedList<Node>();
        LinkedList<String> activeStrings = new LinkedList<String>();
        activeNodes.add(node);
        activeStrings.add("");
        while (!activeNodes.isEmpty()) {
            Node currNode = activeNodes.pollFirst();
            String currString = activeStrings.pollFirst();
            if (currNode.weight != 0) {
                result.add(currString);
            }
            for (int i = 0; i < ALPHABET_SIZE; i++) {
                Node next = currNode.next[i];
                if (next != null) {
                    activeNodes.add(next);
                    activeStrings.add(currString + next.key);
                }
            }
        }
        return result;
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        LinkedList<String> prevRes = new LinkedList<String>();
        Node current = root;
        for (int i = 0; i < s.length(); i++) {
            current = current.next[s.charAt(i) - 'a'];
            if (current == null) {
                return prevRes;
            }
        }
        prevRes = (LinkedList) getWords(current);
        LinkedList<String> result = new LinkedList<String>();
        for (String element : prevRes) {
            result.add(s + element);
        }

        return result;
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        }
        int count = 0;
        if (node.weight != 0) {
            count++;
        }
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            count += size(node.next[i]);
        }
        return count;
    }

    @Override
    public int size() {
        return size(root);
    }

}
