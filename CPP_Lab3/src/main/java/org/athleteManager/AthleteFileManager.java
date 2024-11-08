package org.athleteManager;
import java.io.*;
import java.util.*;

public class AthleteFileManager {

    // Метод для запису даних про спортсмена
    public void writeAthleteData(Athlete athlete) {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("athlete_data.txt", true))) {
            bos.write(athlete.getFirstName().getBytes());
            bos.write('\n');
            bos.write(athlete.getLastName().getBytes());
            bos.write('\n');
            bos.write(athlete.getSport().getBytes());
            bos.write('\n');
        } catch (IOException e) {
            System.err.println("Error writing athlete names: " + e.getMessage());
        }

        // Запис кількості медалей (небуферизований потік)
        try (FileOutputStream fos = new FileOutputStream("athlete_medals.txt", true)) {
            fos.write(athlete.getMedalsCount().toString().getBytes());
            fos.write('\n');
        } catch (IOException e) {
            System.err.println("Error writing athlete medals: " + e.getMessage());
        }
    }

//    // Метод для читання даних про спортсмена
//    public Athlete readAthleteData() {
//        String firstName = "";
//        String lastName = "";
//        String sport = "";
//        int medals = 0;
//
//
//        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream("athlete_data.txt"))) {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(bis));
//            firstName = reader.readLine();
//            lastName = reader.readLine();
//            sport = reader.readLine();
//        } catch (IOException e) {
//            System.err.println("Error reading athlete names: " + e.getMessage());
//        }
//
//        try (FileInputStream fis = new FileInputStream("athlete_medals.txt")) {
//            medals = fis.read();
//        } catch (IOException e) {
//            System.err.println("Error reading athlete medals: " + e.getMessage());
//        }
//
//        return new Athlete(firstName, lastName, sport, medals);
//    }

    public List<Athlete> readAthleteData() {
        List<Athlete> athletes = new ArrayList<>();

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream("athlete_data.txt"));
             BufferedReader reader = new BufferedReader(new InputStreamReader(bis))) {

            String firstName;
            String lastName;
            String sport;

            // Reading multiple athletes' data from the text file
            while (true) {
                firstName = readBufferedLine(bis);
                lastName = readBufferedLine(bis);
                sport = readBufferedLine(bis);

                if (firstName.isEmpty())
                    break;

                athletes.add(new Athlete(firstName, lastName, sport, 0)); // Temporary 0 for medals
            }
        } catch (IOException e) {
            System.err.println("Error reading athlete names: " + e.getMessage());
        }

        try (FileInputStream fis = new FileInputStream("athlete_medals.txt"))
        {
            for (Athlete athlete : athletes) {
                String medalsStr = readFileLine(fis);
                int medals = Integer.parseInt(medalsStr);
                athlete.setMedalsCount(medals);
            }
        } catch (IOException e) {
            System.err.println("Error reading athlete medals: " + e.getMessage());
        }

        return athletes;
    }

    private String readBufferedLine(BufferedInputStream bis) throws IOException {
        StringBuilder sb = new StringBuilder();
        int character;
        while ((character = bis.read()) != -1 && character != '\n') {
            sb.append((char) character);  // Build the string
        }
        return sb.toString();
    }

    private String readFileLine(FileInputStream fis) throws IOException {
        StringBuilder sb = new StringBuilder();
        int character;
        while ((character = fis.read()) != -1 && character != '\n') {
            sb.append((char) character);  // Build the string
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        AthleteFileManager manager = new AthleteFileManager();

        // Test writing
        Athlete athlete = new Athlete("John", "Doe", "Basketball", 3);
        manager.writeAthleteData(athlete);

        // Test reading
        List<Athlete> athletes = manager.readAthleteData();
        athletes.forEach(System.out::println);
//        System.out.println(readAthlete.toString());
    }
}
