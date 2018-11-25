package exhaustions;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;
import java.util.Scanner;

public class Utils {
    public static class ParseResult {
        public int Result;
        public boolean Succeeded;
    }
    
    public static ParseResult tryParseInteger(String str) {
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
    
    public static int[] parseStringOfNumbers(String str) {
        String current = "";
        ArrayList<Integer> list = new ArrayList();
        
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            
            if (c >= '0' && c <= '9' || c == '-') {
                current += c;
            }
            
            else if (c == ' ') {
                if (current.compareTo("") != 0) {
                    try {                    
                        list.add(Integer.parseInt(current));
                        current = "";
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
                list.add(Integer.parseInt(current));
                current = "";
            }
            catch (Exception ex) {
                return null;
            }
        }
        
        int count = list.size();
        int[] result = new int[count];
        for (int i = 0; i < count; i++)
            result[i] = list.get(i);
        
        return result;
    }
    
    public static int[] getRandomNumbers() {
        final int MIN = -100, MAX = 100, MINLEN = 2, MAXLEN = 8;
        
        Random rand = new Random(LocalTime.now().toNanoOfDay());
        
        int count = Math.abs(rand.nextInt()) % (MAXLEN - MINLEN) + MINLEN;
        int[] array = new int[count];
        
        for (int i = 0; i < count; i++) {
            array[i] = Math.abs(rand.nextInt()) % (MAX - MIN) + MIN;
        }
        
        return array;
    }
    
    public static int getRandomNumber() {
        final int MIN = -100, MAX = 100;
        Random rand = new Random(LocalTime.now().toNanoOfDay());
        
        return Math.abs(rand.nextInt()) % (MAX - MIN) + MIN;
    }
    
    public static String numbersArrayToString(int[] array) {
        String result = "";
        
        for (int i = 0; i < array.length; i++) {
            result += array[i] + " ";
        }
        
        return result;
    }
        
    public static String readFromFile(File file) {
        String string;
            
        try (Scanner scanner = new Scanner(file)) {
            scanner.useDelimiter("\\Z");
            string = scanner.next();
            
            return string;
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static boolean saveStringsToFile(File file, Enumeration<String> enumeration) {
        try (FileWriter writer = new FileWriter(file)) {
            while (enumeration.hasMoreElements()) {
                writer.write(enumeration.nextElement() + System.lineSeparator());
            }
            
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    public static boolean saveStringToFile(File file, String string) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(string);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
}