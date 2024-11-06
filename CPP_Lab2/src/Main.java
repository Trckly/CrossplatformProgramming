import java.util.HashMap;
import java.util.Map;


public class Main {


    public static void main(String[] args){
        Map<Integer, Task> tasks = new HashMap<>();
        tasks.put(1, new CurrencyRatePrediction());
        tasks.put(2, new NumberFinder());
    }
}