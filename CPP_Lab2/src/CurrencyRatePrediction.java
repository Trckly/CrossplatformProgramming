import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import org.json.JSONObject;

import static java.lang.Thread.sleep;

public class CurrencyRatePrediction implements Task {

    // API key for the exchange rate provider
    private static final String API_URL = "http://api.currencylayer.com/historical";
    private static final String API_KEY = "bcf1434f6aeeba07d69db577e75437b6";
    private static final int[] API_OLDEST_DATE_ENTRY = {2019, 1, 1};
    private static final int[] API_LATEST_DATE_ENTRY = {2024, 1, 1};

    private static final int interval = 1;

    enum DateStructure{
        year,
        month,
        day
    }

    @SuppressWarnings("CallToPrintStackTrace")
    @Override
    public void executeTask() {
        String[] currencyPairs = {"UAH/USD"};

        for (String pair : currencyPairs) {
            try {
                List<Double> historicalRates = getSpecificDateRate(pair);
                analyzeTrends(historicalRates);
                predictFutureRates(historicalRates);
            } catch (IOException | InterruptedException e) {
                System.err.println("Error fetching data for " + pair);
                e.printStackTrace();
            }
        }
    }

    private static List<Double> getSpecificDateRate(String currencyPair) throws IOException, InterruptedException {
        String[] currencies = currencyPair.split("/");
        String targetCurrency = currencies[0];
        String baseCurrency = currencies[1];

        List<Double> rates = new ArrayList<>();

        int ageGap = API_LATEST_DATE_ENTRY[DateStructure.year.ordinal()] -
                API_OLDEST_DATE_ENTRY[DateStructure.year.ordinal()];

        int[] shiftingDate = API_OLDEST_DATE_ENTRY;
        for (int i = 0; i < ageGap; ++i) {
            String url = API_URL + "?access_key=" + API_KEY + "&source=" + baseCurrency + "&date=" +
                    shiftingDate[DateStructure.year.ordinal()] + "-" +
                    String.format("%02d", shiftingDate[DateStructure.month.ordinal()]) + "-" +
                    String.format("%02d", shiftingDate[DateStructure.day.ordinal()]);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonResponse = new JSONObject(response.body());

            Double rate = jsonResponse.getJSONObject("quotes").getDouble(baseCurrency+targetCurrency);
            rates.add(rate);

            shiftingDate[DateStructure.year.ordinal()]++;
            sleep(5000, 0);
        }

        return rates;
    }

    private static void analyzeTrends(List<Double> rates) {
        if (rates == null || rates.isEmpty()) {
            System.out.println("No data available for analysis.");
            return;
        }

        double sum = 0;
        for (double rate : rates) {
            sum += rate;
        }
        double average = sum / rates.size();
        System.out.println("Average Rate: " + average);

        // Simple moving average (SMA)
        int period = 1;

        List<Double> sma = calculateSMA(rates, period);
        System.out.println("Simple Moving Average: " + sma);
    }

    private static List<Double> calculateSMA(List<Double> rates, int period) {
        List<Double> sma = new ArrayList<>();
        for (int i = 0; i <= rates.size() - period; i++) {
            double sum = 0;
            for (int j = i; j < i + period; j++) {
                sum += rates.get(j);
            }
            sma.add(sum / period);
        }
        return sma;
    }

    // Predicts using linear regression
    private static void predictFutureRates(List<Double> rates) {
        if (rates == null || rates.size() < 2) {
            System.out.println("Not enough data to perform linear regression.");
            return;
        }

        int n = rates.size();
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;

        for (int i = 0; i < n; i++) {
            sumX += i;
            sumY += rates.get(i);
            sumXY += i * rates.get(i);
            sumX2 += i * i;
        }

        double denominator = (n * sumX2 - sumX * sumX);
        if (denominator == 0) {
            System.out.println("Unable to perform linear regression: division by zero.");
            return;
        }

        double m = (n * sumXY - sumX * sumY) / denominator;
        double b = (sumY - m * sumX) / n;

        double nextRate = m * n + b;
        System.out.println("Predicted next rate: " + nextRate);
    }
}
