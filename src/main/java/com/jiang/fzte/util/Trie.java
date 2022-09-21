package com.jiang.fzte.util;

import java.util.HashMap;

public class Trie {

    private HashMap<Character, Trie> children;
    private boolean isEnd;

    public Trie() {
        children = new HashMap<Character, Trie>();
        isEnd = false;
    }

    public void insert(String singleWord) {
        Trie node = this;
        for (int i = 0; i < singleWord.length(); ++i) {
            char ch = singleWord.charAt(i);
            if (!node.children.containsKey(ch)) {
                node.children.put(ch, new Trie());
            }
            node = node.children.get(ch);
            if (i == singleWord.length() - 1) {
                node.isEnd = true;
            }
        }

    }
//
//    public Trie search(String target) {
//        Trie node = this;
//        for (int i = 0; i < target.length(); ++i) {
//            char ch = target.charAt(i);
//            if (!node.children.containsKey(ch)) {
//                return null;
//            }
//            node = node.children.get(ch);
//        }
//        return node;
//    }

    /**
     *  检测整串昵称是否含有敏感词汇
     * @param target 目标字符串
     * @param root 敏感词汇Trie
     * @return true - 是; false - 否
     */
    public boolean check(String target, Trie root) {
        for (int p1 = 0; p1 < target.length(); ++p1) {
            char ch = target.charAt(p1);
            Trie node = root;
            if (root.children.containsKey(ch)) {
                node = root.children.get(ch);
                if (node.isEnd) {
                    return true;
                } else {
                    for (int p2 = p1 + 1; p2 < target.length(); ++p2) {
                        char cch = target.charAt(p2);
                        if (node.children.containsKey(cch)) {
                            node = node.children.get(cch);
                            if (node.isEnd) {
                                return true;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        return false;
    }
}
