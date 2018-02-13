package uk.ac.man.cs.segreganalysis.models;


public class Flags {

    public enum Direction {
        NONE, GROWTH, DECAY;
    }
    public enum Function {
        NONE, LINEAR, CURVE,;
    }

    public static Direction direction = Direction.NONE;
    public static Function function = Function.NONE;

    public Flags(Direction direction, Function function) {
        this.direction = direction;
        this.function = function;
    }

}
