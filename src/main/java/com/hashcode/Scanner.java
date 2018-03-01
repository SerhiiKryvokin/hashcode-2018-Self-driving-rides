package com.hashcode;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Scanner {
    private BufferedReader br;
    private StringTokenizer st;

    public Scanner(File f) {
        try {
            br = new BufferedReader(new FileReader(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Scanner(InputStream f) {
        br = new BufferedReader(new InputStreamReader(f));
    }

    String next() {
        while (st == null || !st.hasMoreTokens()) {
            String s = null;
            try {
                s = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (s == null)
                return null;
            st = new StringTokenizer(s);
        }
        return st.nextToken();
    }

    private boolean isSpaceChar(int c) {
        return !(c >= 33 && c <= 126);
    }

    private int skip() {
        int b;
        while ((b = read()) != -1 && isSpaceChar(b)) ;
        return b;
    }

    private int read() {
        int res = -1;
        try {
            res = br.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    char[] nextCharArray(int size) {
        char[] buf = new char[size];
        int b = skip(), p = 0;
        while (p < size && !(isSpaceChar(b))) {
            buf[p++] = (char) b;
            b = read();
        }
        return size == p ? buf : Arrays.copyOf(buf, p);
    }

    char[][] nextCharMap(int n, int m) {
        char[][] map = new char[n][];
        for (int i = 0; i < n; i++) {
            map[i] = nextCharArray(m);
        }
        return map;
    }

    boolean hasMoreTokens() {
        while (st == null || !st.hasMoreTokens()) {
            String s = null;
            try {
                s = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (s == null)
                return false;
            st = new StringTokenizer(s);
        }
        return true;
    }

    int nextInt() {
        return Integer.parseInt(next());
    }

    long nextLong() {
        return Long.parseLong(next());
    }

    double nextDouble() {
        return Double.parseDouble(next());
    }
}