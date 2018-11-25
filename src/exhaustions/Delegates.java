package exhaustions;

import java.io.File;

public class Delegates {
    public static interface LogAction {
        public void log(String str);
    }
    
    public static interface CalcFunction {
        public int calc(int a, int b);
    }
    
    public static interface SaveDialogCaller {
        public File call();
    }
}