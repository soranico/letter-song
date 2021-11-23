package algorithm;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    private static class Node{
        private int k;
        private int v;
        private Node pre;
        private Node next;

        public Node(int k, int v) {
            this.k = k;
            this.v = v;
        }
    }
    private int size;
    private final int cap;
    private final Node head = new Node(0,0);
    private final Node tail = new Node(0,0);
    private final Map<Integer,Node> cache;


    public LRUCache(int capacity) {
        this.cache = new HashMap<>(capacity);
        this.cap = capacity;
        head.next = tail;
        tail.pre = head;
    }

    public int get(int key) {
        Node v = cache.get(key);
        if (v == null){
            return -1;
        }
        unlinkNode(v);
        moveTail(v);
        return v.v;
    }

    public void put(int key, int value) {
        Node node = cache.get(key);
        if (node == null){
            node = new Node(key,value);
            cache.put(key,node);
            moveTail(node);
            if (++size > cap){
                unlinkHeadNext();
                size--;
            }
        }else {
            node.v = value;
            unlinkNode(node);
            moveTail(node);
        }

    }

    private void unlinkHeadNext(){
        if (head.next!=null){
            cache.remove(head.next.k);
            unlinkNode(head.next);
        }
    }

    private void moveTail(Node node){
       Node pre = tail.pre;
       tail.pre = node;
       node.next = tail;
       pre.next = node;
       node.pre = pre;
    }

    private void unlinkNode(Node node){
        Node pre = node.pre,next = node.next;
        pre.next = next;
        next.pre = pre;
    }


    public static class Kano {
        @Test
        public void testLru() {
            LRUCache lruCache = new LRUCache(2);
            lruCache.get(2);
            lruCache.put(2, 1);
            lruCache.put(1, 1);
            lruCache.put(1, 2);
            lruCache.put(2, 3);
            lruCache.put(4, 1);
            int v = lruCache.get(1);
            v = lruCache.get(2);
//            lruCache.put(1, 5);
//            lruCache.put(1, 2);
//            lruCache.get(1);
//            lruCache.get(2);
        }
    }


}
