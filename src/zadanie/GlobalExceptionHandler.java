package zadanie;

public class GlobalExceptionHandler {

    public static void main(String[] args) {


    }

    public int performArithmeticOperation(int num1, int num2) {
        return num1/num2;
    }
}

class Handler implements Thread.UncaughtExceptionHandler {



    public void uncaughtException(Thread t, Throwable e) {
    }
}