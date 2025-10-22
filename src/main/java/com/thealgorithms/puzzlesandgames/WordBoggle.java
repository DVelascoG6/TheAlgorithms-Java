package com.thealgorithms.puzzlesandgames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * Las variables del código han sido traducidas por DiegoEspMig al Castellano
 * @author DiegoEspMig
 */

public final class WordBoggle {

    private WordBoggle() {
    }
    /**
     * O(nm * 8^s + ws) time where n = width of boggle board, m = height of
     * boggle board, s = length of longest word in string array, w = length of
     * string array, 8 is due to 8 explorable neighbours O(nm + ws) space.
     */
    public static List<String> boggleBoard(char[][] tabla, String[] frase) {
        Trie trie = new Trie();
        for (String palabra : frase) {
            trie.add(palabra);
        }
        Set<String> fraseFinal = new HashSet<>();
        boolean[][] visitado = new boolean[tabla.length][tabla.length];
        for (int i = 0; i < tabla.length; i++) {
            for (int j = 0; j < tabla[i].length; j++) {
                explore(i, j, tabla, trie.root, visitado, fraseFinal);
            }
        }
        return new ArrayList<>(fraseFinal);
    }

    public static void explore(int i, int j, char[][] tabla, TrieNode trieNode, boolean[][] visitado, Set<String> fraseFinal) {
        if (visitado[i][j]) {
            return;
        }

        char letra = tabla[i][j];
        if (!trieNode.ninos.containsKey(letra)) {
            return;
        }
        visitado[i][j] = true;
        trieNode = trieNode.ninos.get(letra);
        if (trieNode.ninos.containsKey('*')) {
            fraseFinal.add(trieNode.palabra);
        }

        List<Integer[]> vecindario = getNeighbors(i, j, tabla);
        for (Integer[] vecino : vecindario) {
            explore(vecino[0], vecino[1], tabla, trieNode, visitado, fraseFinal);
        }

        visitado[i][j] = false;
    }

    public static List<Integer[]> getNeighbors(int i, int j, char[][] tabla) {
        List<Integer[]> vecindario = new ArrayList<>();
        if (i > 0 && j > 0) {
            vecindario.add(new Integer[] {i - 1, j - 1});
        }

        if (i > 0 && j < tabla[0].length - 1) {
            vecindario.add(new Integer[] {i - 1, j + 1});
        }

        if (i < tabla.length - 1 && j < tabla[0].length - 1) {
            vecindario.add(new Integer[] {i + 1, j + 1});
        }

        if (i < tabla.length - 1 && j > 0) {
            vecindario.add(new Integer[] {i + 1, j - 1});
        }

        if (i > 0) {
            vecindario.add(new Integer[] {i - 1, j});
        }

        if (i < tabla.length - 1) {
            vecindario.add(new Integer[] {i + 1, j});
        }

        if (j > 0) {
            vecindario.add(new Integer[] {i, j - 1});
        }

        if (j < tabla[0].length - 1) {
            vecindario.add(new Integer[] {i, j + 1});
        }

        return vecindario;
    }
}

// Trie used to optimize string search
class TrieNode {

    Map<Character, TrieNode> ninos = new HashMap<>();
    String palabra = "";
}

class Trie {

    TrieNode root;
    char endSymbol;

    Trie() {
        this.root = new TrieNode();
        this.endSymbol = '*';
    }

    public void add(String str) {
        TrieNode node = this.root;
        for (int i = 0; i < str.length(); i++) {
            char letra = str.charAt(i);
            if (!node.ninos.containsKey(letra)) {
                TrieNode newNode = new TrieNode();
                node.ninos.put(letra, newNode);
            }
            node = node.ninos.get(letra);
        }
        node.ninos.put(this.endSymbol, null);
        node.palabra = str;
    }
}
