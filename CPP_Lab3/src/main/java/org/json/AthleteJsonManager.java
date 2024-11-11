package org.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.athleteManager.Athlete;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AthleteJsonManager {
    public static void writeAthleteData(Athlete athlete, String filename) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        List<Athlete> athletes = null;
        if(new File(filename).exists()){
            athletes = readAthleteData(filename);
        }

        if(athletes == null)
        {
            athletes = new ArrayList<>();
        }

        athletes.add(athlete);

        try {
            Writer writer = new FileWriter(filename);

            gson.toJson(athletes, writer);

            writer.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static ObservableList<Athlete> readAthleteData(String filename) {
        List<Athlete> athleteList = new ArrayList<>();

        Gson gson = new Gson();
        Type athleteListType = new TypeToken<List<Athlete>>() {}.getType();

        try {
            Reader reader = new FileReader(filename);

            athleteList = gson.fromJson(reader, athleteListType);

            reader.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        athleteList.forEach(System.out::println);

        return FXCollections.observableList(athleteList);
    }
}
