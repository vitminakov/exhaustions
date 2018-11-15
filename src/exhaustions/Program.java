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
    
    public interface LogAction {
        public void log(String str);
    }
    
    public interface CalcFunction {
        public int calc(int a, int b);
    }
    
    public static class ParseResult {
        public int Result;
        public Boolean Succeeded;
    }
    
    public static ParseResult TryParseInteger(String str) {
        ParseResult result = new ParseResult();
        
        try {
            result.Result = Integer.parseInt(str);
            result.Succeeded = true;
        }
        catch (Exception ex) {
            result.Succeeded = false;
        }
        
        return result;
    }
    
    public static int[] parseStringOfNumbers(String str, int count) {
        String current = "";
        int added = 0;
        int[] array = new int[count];
        
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            
            if (c >= '0' && c <= '9' || c == '-') {
                current += c;
            }
            
            else if (c == ' ') {
                if (current.compareTo("") != 0) {
                    try {
                        if (added >= count) return null;
                    
                        array[added] = Integer.parseInt(current);
                    
                        current = "";
                        added++;
                    }
                    catch (Exception ex) {
                        return null;
                    }
                }
            }
            
            else return null;
        }
        
        if (current.compareTo("") != 0) {
            try {
                if (added >= count) return null;
                    
                array[added] = Integer.parseInt(current);
                    
                current = "";
                added++;
            }
            catch (Exception ex) {
                return null;
            }
        }
        
        if (added != count) return null;
        return array;
    }
    
    public static int[] getRandomNumbers(int count) {
        final int MIN = -100, MAX = 100;
        
        int[] array = new int[count];
        Random rand = new Random(LocalTime.now().toNanoOfDay());
        
        for (int i = 0; i < count; i++) {
            array[i] = Math.abs(rand.nextInt()) % (MAX - MIN) + MIN;
        }
        
        return array;
    }
    
       public static Operator[] makeFirstCombination(int count) {
        Operator[] result = new Operator[count];
        
        for (int i = 0; i < count; i++)
            result[i] = Operator.LOWEST;
        
        return result;
    }
    
    public static Boolean goNextCombination(Operator[] opers) {
        for (int i = opers.length - 1; i >= 0; i--) {
            opers[i] = opers[i].getNext();
            
            if (opers[i] != Operator.LOWEST) return true;
        }
        return false;
    }
    
    public static int calcCombination(int[] _numbers, Operator[] _opers) {        
        ArrayList<Integer> numbers = new ArrayList<>();
        for (Integer n : _numbers) numbers.add(n);
        
        ArrayList<Operator> opers = new ArrayList();
        for (Operator op : _opers) opers.add(op);
        
        for (int prior = Operator.HIGHEST_PRIORITY;
                prior >= Operator.LOWEST_PRIORITY; prior--)
        {
            for (int i = 0; i < opers.size(); ) {
                Operator oper = opers.get(i);
                
                if (oper.getPriority() == prior) {
                    int left = numbers.get(i);
                    int right = numbers.get(i + 1);
                    
                    int result = oper.getFunction().calc(left, right);
                    
                    numbers.set(i, result);
                    numbers.remove(i + 1);
                    
                    opers.remove(i);
                }
                else i++;
            }
        }
        
        return numbers.get(0);
    }
    
    public static String numbersArrayToString(int[] array) {
        String result = "";
        
        for (int i = 0; i < array.length; i++) {
            result += array[i] + " ";
        }
        
        return result;
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