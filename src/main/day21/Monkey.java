package main.day21;

import java.util.Map;
import java.util.function.LongBinaryOperator;

//class is a bit more mutable than I like it, but works
public class Monkey {
    private final String name;
    private long v;
    private boolean unknown;
    private Monkey l;
    private Monkey r;

    private String opStr;
    private LongBinaryOperator op;

    private static final Map<String, LongBinaryOperator> OPS = Map.of(
            "+", Math::addExact,
            "-", Math::subtractExact,
            "*", Math::multiplyExact,
            "/", Math::floorDiv
    );
    private static final Map<String, LongBinaryOperator> RREV_OPS = Map.of(
            "-", Math::addExact,
            "+", Math::subtractExact,
            "/", Math::multiplyExact,
            "*", Math::floorDiv
    );

    private static final Map<String, LongBinaryOperator> LREV_OPS = Map.of(
            "-", (l, r) -> Math.subtractExact(r, l),
            "+", Math::subtractExact,
            "/", (l, r) -> Math.floorDiv(r, l),
            "*", Math::floorDiv
    );

    public Monkey(String name) {
        this.name = name;
    }

    public long v() {
        return v;
    }

    public String name() {
        return name;
    }

    public void setUnknown(boolean unknown) {
        this.unknown = unknown;
    }

    @Override
    public String toString() {
        return name + ": " +
                (isLeaf()
                        ? unknown ? "X" : "" + v
                        : l.name + " " + opStr + " " + r.name);
    }

    public Monkey setAsLeaf(long v) {
        this.v = v;
        this.unknown = false;
        this.l = null;
        this.r = null;
        this.opStr = null;
        this.op = null;
        return this;
    }

    public Monkey setAsOp(Monkey l, Monkey r, String op) {
        this.v = 0;
        this.unknown = true;
        this.l = l;
        this.r = r;
        this.opStr = op;
        this.op = OPS.get(op);
        return this;
    }

    private boolean isLeaf() {
        return l == null;
    }

    private boolean isKnownLeaf() {
        return isLeaf() && !unknown;
    }

    private boolean isUnknownLeaf() {
        return isLeaf() && unknown;
    }

    public Monkey resolve() {
        if (isLeaf()) {
            return this;
        }
        l = l.resolve();
        r = r.resolve();
        if (l.isKnownLeaf() && r.isKnownLeaf()) {
            return setAsLeaf(op.applyAsLong(l.v, r.v));
        }
        return this;
    }

    public Monkey reduce() {
        Monkey newN = new Monkey(name + "Rl");
        Monkey newB = new Monkey(name + "Rb");
        if (l.isKnownLeaf()) {
            if (r.isUnknownLeaf()) {
                return r.setAsLeaf(l.v);
            }
            if (r.l.isKnownLeaf()) {
                newN.setAsLeaf(LREV_OPS.get(r.opStr).applyAsLong(l.v, r.l.v));
                return newB.setAsOp(newN, r.r, "=");
            }
            if (r.r.isKnownLeaf()) {
                newN.setAsLeaf(RREV_OPS.get(r.opStr).applyAsLong(l.v, r.r.v));
                return newB.setAsOp(newN, r.l, "=");
            }
        }
        if (r.isKnownLeaf()) {
            if (l.isUnknownLeaf()) {
                return l.setAsLeaf(r.v);
            }
            if (l.l.isKnownLeaf()) {
                newN.setAsLeaf(LREV_OPS.get(l.opStr).applyAsLong(r.v, l.l.v));
                return newB.setAsOp(l.r, newN, "=");
            }
            if (l.r.isKnownLeaf()) {
                newN.setAsLeaf(RREV_OPS.get(l.opStr).applyAsLong(r.v, l.r.v));
                return newB.setAsOp(l.l, newN, "=");
            }
        }
        throw new RuntimeException("Should not be able to get here");
    }
}
