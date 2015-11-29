package ua.yandex.shad.tries;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RWayTrie implements Trie {

    public static class DynamicArray<Typename> {

        private Typename[] values;
        private int length;

        public DynamicArray(int length) {
            values = (Typename[]) new Object[length];
        }

        public DynamicArray() {
            this(0);
        }

        public void add(Typename... elements) {
            if (values.length == 0) {
                values = (Typename[]) new Object[elements.length];
                this.length = elements.length;
                System.arraycopy(elements, 0, values, 0, elements.length);
            } else {
                int finalLength = values.length;
                while (elements.length + this.length > finalLength) {
                    finalLength *= 2;
                }
                Typename[] newArr = (Typename[]) new Object[finalLength];

                System.arraycopy(this.values, 0, newArr, 0, this.length);
                System.arraycopy(elements, 0, newArr, this.length,
                        elements.length);
                values = newArr;
                this.length += elements.length;
            }
        }

        public Typename at(int index) {
            return values[index];
        }
    }

    public static final int ALPHABET_SIZE = 26;
    private Node root = new Node(' ', 0, null);

    private static class Node {

        private char key;
        private int weight;
        private Node[] next;
        private Node parent;

        public Node(char inKey, int inWeight, Node parent) {
            key = inKey;
            weight = inWeight;
            next = new Node[ALPHABET_SIZE];
            this.parent = parent;
        }

        public void setWeight(int inWeight) {
            weight = inWeight;
        }
    }

    @Override
    public void add(Tuple tuple) {
        add(root, tuple, 0);
    }

    private void add(Node node, Tuple tuple, int position) {
        String term = tuple.getTerm();
        int weight = tuple.getWeight();
        int wordLength = term.length();
        if (position == wordLength) {
            node.setWeight(weight);
            return;
        }
        int insertPos = (int) term.charAt(position) - 'a';
        if (node.next[insertPos] == null) {
            node.next[insertPos] = new Node(term.charAt(position), 0, node);
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

    private static class BfsStringIterable implements Iterable<String> {

        private DynamicArray<Node> layout;
        private int pointer;

        public BfsStringIterable(Node prefix) {
            this.layout = new DynamicArray<>(0);
            this.layout.add(prefix);
            pointer = 0;

        }

        @Override
        public Iterator iterator() {
            return new BfsStringIterator();
        }

        public class BfsStringIterator implements Iterator<String> {

            @Override
            public boolean hasNext() {
                return layout.length != 0;
            }

            @Override
            public String next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                boolean found = false;
                Node resultNode = null;
                while (!found) {
                    resultNode = layout.at(pointer++);
                    if (pointer == layout.length) {
                        DynamicArray<Node> newLayout = new DynamicArray();
                        for (int i = 0; i < layout.length; i++) {
                            for (int j = 0; j < ALPHABET_SIZE; j++) {
                                Node child = layout.at(i).next[j];
                                if (child != null) {
                                    newLayout.add(child);
                                }
                            }
                        }
                        layout = newLayout;
                        pointer = 0;
                    }
                    if (resultNode.weight != 0) {
                        found = true;
                    }

                }
                String resultString = "";
                resultString += resultNode.key;
                while (resultNode.parent != null) {
                    resultNode = resultNode.parent;
                    resultString += resultNode.key;
                }
                return new StringBuffer(resultString).reverse().
                        toString().trim();
            }
        }

    }

    private static class EmptyStringIterable implements Iterable<String> {

        @Override
        public Iterator<String> iterator() {
            return new EmptyStringIterator();
        }

        private static class EmptyStringIterator implements Iterator<String> {

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public String next() {
                throw new NoSuchElementException();
            }

        }

    }

    private Iterable<String> getWords(Node node) {
        return new BfsStringIterable(node);
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Node current = root;
        for (int i = 0; i < s.length(); i++) {
            current = current.next[s.charAt(i) - 'a'];
            if (current == null) {
                return new EmptyStringIterable();
            }
        }
        return getWords(current);
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
