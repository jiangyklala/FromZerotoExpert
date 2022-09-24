package com.jiang.fzte.util;

import java.util.HashMap;

public class Trie {

    private HashMap<Character, Trie> children;
    private boolean isEnd;

    // 每个Trie的数据内容为上一个Trie的children，自身是否为end是当前自己的 isEnd标志
    public Trie() {
        children = new HashMap<Character, Trie>();
        isEnd = false;
    }

    public void insert(String singleWord) {   // 先插入后匹配
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

    /**
     * 若 singleWord 存在 Trie 中, 则删除它
     * @param singleWord
     * @param root
     */
    public void delete(String singleWord, Trie root) {
        Trie node = check(singleWord, root);
        if (node == null) return;
        if (node.children.isEmpty() != true) {  // 这个分支下还有其它敏感词的尾节点
            node.isEnd = false;
        } else {  // 这个分支下面没有其它敏感词的尾节点, 但这个分支上面可能有
            Trie preWaitDel = root;   // 最后要开始往后删除字符的前一个节点
            char preWaitDelKey = singleWord.charAt(0);  // 最后要开始往后删除字符的值
            node = root;
            for (int i = 0; i < singleWord.length() - 1; ++i) {  // 最后一个字符上面if已经判断过了
                char c = singleWord.charAt(i);
                node = node.children.get(c);
                // 如果当前节点子节点不止一个, 即除了当前敏感词的下一个节点之外还有其它节点, 或者当且节点就是其它敏感词的末节点, 更新两个待删除所需值
                if (node.children.size() != 1 || node.isEnd == true) {
                    preWaitDel = node;
                    preWaitDelKey = singleWord.charAt(i + 1);
                }
            }
            preWaitDel.children.remove(preWaitDelKey);  // 最后删除
        }
    }

    /**
     *  检测整串昵称是否含有敏感词汇
     * @param target 目标字符串
     * @param root 敏感词汇Trie
     * @return 若找到则返回敏感词在Trie中的尾结点, 否则null
     */
    public Trie check(String target, Trie root) {
        for (int p1 = 0; p1 < target.length(); ++p1) {
            char ch = target.charAt(p1);
            Trie node = root;
            if (root.children.containsKey(ch)) {
                node = root.children.get(ch);
                if (node.isEnd) {
                    return node;
                } else {
                    for (int p2 = p1 + 1; p2 < target.length(); ++p2) {
                        char cch = target.charAt(p2);
                        if (node.children.containsKey(cch)) {
                            node = node.children.get(cch);
                            if (node.isEnd) {
                                return node;
                            }
                        } else {
                            break;   // 匹配中断后中返回p1继续往后匹配
                        }
                    }
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Trie root = new Trie();
        root.insert("abcde");
        root.insert("abcf");
        if (root.check("abcde", root) != null) {
            System.out.println("找到了");
        }
        root.delete("abcde", root);
        if (root.check("abcf", root) != null) {  // 在这里查看内存, 发现没有了"de"两个节点
            System.out.println("找到了");
        }
    }
}
