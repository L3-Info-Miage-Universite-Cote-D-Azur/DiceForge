package diceforge.moteur;

public class Log {
    public static boolean printLog = true;

    public static final void println(String string){
        if(printLog) {
            System.out.println(string);
        }
    }

    public static void setPrintLog(boolean printLog){
        Log.printLog = printLog;
    }
}
