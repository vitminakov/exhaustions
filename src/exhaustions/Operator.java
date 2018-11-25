package exhaustions;

public enum Operator {
    Add('+', 0, (a, b) -> Math.addExact(a, b)),
    Subtract('-', 0, (a, b) -> Math.subtractExact(a, b)),
    Multiply('*', 1, (a, b) -> Math.multiplyExact(a, b));
        
    public static final Operator LOWEST = Add;
    public static final Operator HIGHEST = Multiply;
        
    public static final int LOWEST_PRIORITY = 0;
    public static final int HIGHEST_PRIORITY = 1;
    
    private final char symbol;
    private final int priority;
    private final Delegates.CalcFunction function;
        
    Operator(char _symbol, int _priority, Delegates.CalcFunction _function) {
        symbol = _symbol;
        priority = _priority;
        function = _function;
    }
        
    public char getSymbol() {
        return symbol;
    }
        
    public int getPriority() {
        return priority;
    }
        
    public Delegates.CalcFunction getFunction() {
        return function;
    }
        
    public Operator getNext() {
        if (this == HIGHEST) return LOWEST;
        return Operator.values()[ordinal() + 1];
    }
}