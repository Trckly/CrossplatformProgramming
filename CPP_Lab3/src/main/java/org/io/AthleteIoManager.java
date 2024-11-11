package org.io;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.athleteManager.Address;
import org.athleteManager.Athlete;

import java.io.*;

public class AthleteIoManager
{
    public static void writeAthleteData(Athlete athlete, String filename) {

        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filename, true))) {
            bos.write(athlete.getFirstName().getBytes());
            bos.write('\n');
            bos.write(athlete.getLastName().getBytes());
            bos.write('\n');
            bos.write(athlete.getSport().getBytes());
            bos.write('\n');
            bos.write(athlete.getAge().toString().getBytes());
            bos.write('\n');
            bos.write(athlete.getAddress().getStreet().getBytes());
            bos.write('\n');
            bos.write(athlete.getAddress().getNumber().toString().getBytes());
            bos.write('\n');
        } catch (IOException e) {
            System.err.println("Error writing athlete names: " + e.getMessage());
        }

        try (FileOutputStream fos = new FileOutputStream("athlete_io_medals.txt", true)) {
            fos.write(athlete.getMedalsCount().toString().getBytes());
            fos.write('\n');
        } catch (IOException e) {
            System.err.println("Error writing athlete medals: " + e.getMessage());
        }
    }

    public static ObservableList<Athlete> readAthleteData(String filename) {
        ObservableList<Athlete> athletes = FXCollections.observableArrayList();

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filename)))
        {
            String firstName;
            String lastName;
            String sport;
            String age;
            String addressStreet;
            String addressNumber;

            while (true) {
                firstName = readBufferedLine(bis);
                lastName = readBufferedLine(bis);
                sport = readBufferedLine(bis);
                age = readBufferedLine(bis);
                addressStreet = readBufferedLine(bis);
                addressNumber = readBufferedLine(bis);

                if (firstName.isEmpty())
                    break;

                Address address = new Address(addressStreet, Integer.parseInt(addressNumber));
                athletes.add(new Athlete(firstName, lastName, sport, 0, Integer.parseInt(age), address));
            }
        } catch (IOException e) {
            System.err.println("Error reading athlete names: " + e.getMessage());
        }

        try (FileInputStream fis = new FileInputStream("athlete_io_medals.txt"))
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

    private static String readBufferedLine(BufferedInputStream bis) throws IOException {
        StringBuilder sb = new StringBuilder();
        int character;
        while ((character = bis.read()) != -1 && character != '\n') {
            sb.append((char) character);
        }
        return sb.toString();
    }

    private static String readFileLine(FileInputStream fis) throws IOException {
        StringBuilder sb = new StringBuilder();
        int character;
        while ((character = fis.read()) != -1 && character != '\n') {
            sb.append((char) character);
        }
        return sb.toString();
    }
}
