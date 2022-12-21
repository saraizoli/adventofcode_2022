package main.day20;

public class Node {
    private final int v;
    private Node next;
    private Node prev;

    private static int cnt;
    private static int mult;

    public Node(int v) {
        this.v = v;
        ++cnt;
    }

    public int v() {
        return v;
    }

    public Node next() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
        next.prev = this;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
        prev.next = this;
    }

    public Node next(int offset) {
        int mod = cnt - 1;
        offset = (offset % mod + mod) % mod; //don't wanna deal with neg numbers
        offset = (offset * mult) % mod;
        return rawNext(offset);
    }

    public Node rawNext(int offset) {
        Node curr = this;
        for (int i = 0; i < offset; i++) { //go to new place
            curr = curr.next;
        }
        return curr;
    }

    void move() {
        if (v == 0) {
            return;
        }
        Node prevN = prev;
        Node nextN = next;
        //unlink current place
        prevN.setNext(nextN);
        //move to new place
        nextN = nextN.next(v);
        prevN = nextN.prev;
        //relink
        setPrev(prevN);
        setNext(nextN);
    }

    @Override
    public String toString() {
        return "" + v;
    }

    static void reset(int newMult) {
        cnt = 0;
        mult = newMult;
    }
}
