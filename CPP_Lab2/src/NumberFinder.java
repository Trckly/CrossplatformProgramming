import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberFinder implements Task {

    @Override
    public void executeTask() {
        String filePath = "text.txt";
        String text;

        try {
            text = readFromFile(filePath);
        } catch (FileNotFoundException e) {
            System.err.println("Error: The file " + filePath + " was not found.");
            e.printStackTrace();
            return;
        }

        // Regular expression for finding valid numbers only
        String numberRegex = "(?<![\\d.])(-?\\d+(\\.\\d+)?([eE]-?\\d+)?)(?![\\d.])";

        // Create pattern and matcher
        Pattern pattern = Pattern.compile(numberRegex);
        Matcher matcher = pattern.matcher(text);

        OutFoundNumbers(matcher);
    }

    private static String readFromFile(String filePath) throws FileNotFoundException {
        StringBuilder text = new StringBuilder();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                text.append(scanner.nextLine()).append("\n");
            }
        }

        return text.toString();
    }

    private static void OutFoundNumbers(Matcher matcher) {
        System.out.println("Found numbers:");

        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
