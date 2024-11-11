package org.javaNative;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.athleteManager.Athlete;

import java.io.*;

public class AthleteNativeManager {
    // Custom ObjectOutputStream to avoid writing the header when appending
    static class AppendableObjectOutputStream extends ObjectOutputStream {
        public AppendableObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            // Skip writing a new header
            reset();
        }
    }

    public static void writeAthleteData(Athlete athlete, String filename) {
        boolean append = new File(filename).exists();

        try (OutputStream os = new FileOutputStream(filename, true);
             ObjectOutputStream oos = append ? new AppendableObjectOutputStream(os) : new ObjectOutputStream(os))
        {
            oos.writeObject(athlete);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static ObservableList<Athlete> readAthleteData(String filename) {
        ObservableList<Athlete> athletes = FXCollections.observableArrayList();

        try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {

            while (true) {
                Athlete athlete;
                try {
                    athlete = (Athlete) ois.readObject();
                } catch (EOFException e) {
                    break;
                }
                athletes.add(athlete);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return athletes;
    }
}
