package uk.ac.man.cs.segreganalysis.models;


public class Flags {

    public enum Direction {
        NONE, GROWTH, DECAY;
    }
    public enum Function {
        NONE, LINEAR, CURVE,;
    }
    public enum Algorithm {
        DISSIMILARITY, AFFINITY, BOTH,;
    }

    public static Direction direction = Direction.NONE;
    public static Function function = Function.NONE;
    public static Algorithm algorithm = Algorithm.DISSIMILARITY;

    public Flags(Direction direction, Function function, Algorithm algorithm) {
        this.direction = direction;
        this.function = function;
        this.algorithm = algorithm;
    }

}
