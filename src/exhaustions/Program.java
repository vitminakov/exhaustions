package exhaustions;

import java.util.Random;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;
import java.time.LocalTime;
import java.io.File;
import java.io.FileWriter;

public class Program {    
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
        private final CalcFunction function;
        
        Operator(char _symbol, int _priority, CalcFunction _function) {
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
        
        public CalcFunction getFunction() {
            return function;
        }
        
        public Operator getNext() {
            if (this == HIGHEST) return LOWEST;
            return Operator.values()[ordinal() + 1];
        }
    }
    
   
    
    public interface CalcFunction {
        public int calc(int a, int b);
    }
    
        public static String readFromFile(File file) {
        try {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("\\Z");
            
            String string = scanner.next();
            
            scanner.close();
            return string;
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static Boolean saveToFile(File file, Enumeration<String> enumeration) {
        try {
            FileWriter writer = new FileWriter(file);
            
            while (enumeration.hasMoreElements()) {
                writer.write(enumeration.nextElement() + System.lineSeparator());
            }
            
            writer.close();
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
    
   
}