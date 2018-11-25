package exhaustions;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Program {
    public static ArrayList<Operator[]> doExhaustions(int[] numbers, int amount,
            Delegates.LogAction logger, Delegates.SaveDialogCaller sdCaller) {
        ArrayList<Operator[]> succeded = new ArrayList();
        Operator[] opers = makeFirstCombination(numbers.length - 1);
        
        do {
            try {
                int S = calcCombination(numbers, opers);
                logCombination(numbers, opers, S, logger);
            
                if (S == amount) {
                    succeded.add(Arrays.copyOf(opers, opers.length));
                }
            }
            catch (Exception ex) {
                logger.log("Не удалось рассчитать: " +
                    сombinationToString(numbers, opers));
            }
        } while (goNextCombination(opers));
        
        int count = succeded.size();
        if (count > 0) {
            logger.log("Найдено комбинаций: " + count + ".");
            saveSequencesToFile(numbers, succeded, amount, logger, sdCaller);
        }
        else logger.log("Комбинации не обнаружены.");
        
        return succeded;
    }
    
    public static Operator[] makeFirstCombination(int count) {
        Operator[] result = new Operator[count];
        
        for (int i = 0; i < count; i++)
            result[i] = Operator.LOWEST;
        
        return result;
    }
    
    public static boolean goNextCombination(Operator[] opers) {
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
        opers.addAll(Arrays.asList(_opers));
        
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
    
    public static String сombinationToString(int[] numbers, Operator[] opers) {
        String message = "";
        int num;
        
        message += numbers[0] + " " + opers[0].getSymbol() + " ";
        
        for (int i = 1; i < opers.length; i++) {
            num = numbers[i];
            if (num >= 0) message += num + " " + opers[i].getSymbol() + " ";
            else message += "(" + num + ") " + opers[i].getSymbol() + " ";
        }
        
        num = numbers[numbers.length - 1];
        if (num >= 0) message += num;
        else message += "(" + num + ")";
        
        return message;
    }
    
    public static void logCombination(int[] numbers, Operator[] opers, int S, Delegates.LogAction logger) {
        String message = "";
        
        message += сombinationToString(numbers, opers);
        message += " = " + S;
        
        logger.log(message);
    }
    
    public static void saveSequencesToFile(int[] numbers, ArrayList<Operator[]> sequences, int amount,
            Delegates.LogAction logger, Delegates.SaveDialogCaller sdCaller) {
        String text = "";
        File file = sdCaller.call();
        
        if (file != null) {
            for (Operator[] opers : sequences) {
                text += сombinationToString(numbers, opers) + " = " + amount + System.lineSeparator();
            }
            
            if (Utils.saveStringToFile(file, text))
                logger.log("Комбинации сохранены в файл.");
            else logger.log("Не удалось сохранить комбинации в файл.");
        }
        else logger.log("Комбинации не были сохранены в файл.");
    }
}