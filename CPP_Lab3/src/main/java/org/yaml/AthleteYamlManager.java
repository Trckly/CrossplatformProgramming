package org.yaml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.athleteManager.Address;
import org.athleteManager.Athlete;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.util.*;

public class AthleteYamlManager {
//    public static class CustomRepresenter extends Representer {
//        public CustomRepresenter(DumperOptions dumperOptions) {
//            super(dumperOptions);
//        }
//
//        @Override
//        public org.yaml.snakeyaml.nodes.Node represent(Object data) {
//            if (data instanceof Athlete athlete) {
//                Map<String, Object> values = new LinkedHashMap<>();
//                values.put("firstName", athlete.getFirstName());
//                values.put("lastName", athlete.getLastName());
//
//                // Only include 'sport' if age <= 30
//                if (athlete.getAge() <= 30) {
//                    values.put("sport", athlete.getSport());
//                }
//
//                // Pass the FlowStyle (e.g., BLOCK) to representMapping
//                return representMapping(new Tag("Athlete"), values, DumperOptions.FlowStyle.AUTO);
//            }
//            return super.represent(data);
//        }
//    }

    public static void writeAthleteData(Athlete athlete, String filename) {
        ObservableList<Athlete> athletes;

        if(new File(filename).exists())
            athletes = readAthleteData(filename);
        else
            athletes = FXCollections.observableArrayList();

        athletes.add(athlete);

        DumperOptions options = new DumperOptions();
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Representer representer = new Representer(options);
        representer.addClassTag(Athlete.class, Tag.MAP);

        athletes.forEach(a -> {
            try {
                if (a.getAge() > 30)
                    a.setSport(null);
            } catch (NullPointerException e) {
                System.out.println("Age is null: " + e.getMessage());
            }
        });

        Yaml yaml = new Yaml(representer, options);
        try (Writer writer = new FileWriter(filename)) {
            yaml.dump(athletes, writer);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static ObservableList<Athlete> readAthleteData(String filename) {
        ArrayList<Athlete> athletes = new ArrayList<>();

        Constructor constructor = new Constructor(List.class, new LoaderOptions());
        Yaml yaml = new Yaml(constructor);

        List<Map<String, Object>> athletesMapList;
        try (Reader reader = new FileReader(filename)) {
            // Load the list of maps from the YAML file
            athletesMapList = yaml.load(reader);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return FXCollections.observableArrayList();
        } catch (Exception e) {
            System.err.println("Deserialization error: " + e.getMessage());
            return FXCollections.observableArrayList();
        }

        for (Map<String, Object> athleteMap : athletesMapList) {
            String firstName = (String) athleteMap.get("firstName");
            String lastName = (String) athleteMap.get("lastName");
            String sport = (String) athleteMap.get("sport");

            // Safely cast 'age' to Integer
            Integer age = athleteMap.get("age") instanceof Integer ? (Integer) athleteMap.get("age") : Integer.valueOf((String) athleteMap.get("age"));

            // Deserialize 'address' map
            Map<String, Object> addressMap = (Map<String, Object>) athleteMap.get("address");
            Address address = new Address((String) addressMap.get("street"), (Integer) addressMap.get("number"));

            // Create an Athlete object with the data from the map
            Athlete athlete = new Athlete(firstName, lastName, sport, null, age, address);
            athletes.add(athlete);
        }


        // Return the ObservableList of Athlete objects
        return FXCollections.observableArrayList(athletes);
    }

//    private Map<String, Object> AthleteIntoMap(Athlete athlete) {
//
//    }

//    public static void main(String[] args) {
//        // Create a list of example Athlete objects
//        List<Athlete> athletes = new ArrayList<>();
//        athletes.add(new Athlete("John", "Doe", "Swimming", 5, 25, new Address()));
//        athletes.add(new Athlete("Jane", "Smith", "Running", 8, 45, new Address()));
//        athletes.add(new Athlete("Michael", "Johnson", "Cycling", 3, 78, new Address()));
//
//        // File path for YAML file
//        String filePath = "athletes.yaml";
//
//        // Serialize the list to YAML
//        athletes.forEach(athlete -> writeAthleteData(athlete, filePath));
////        writeAthleteData(athletes, filePath);
//        System.out.println("Athletes serialized to " + filePath);
//
//        // Deserialize the list from YAML
//        List<Athlete> deserializedAthletes = readAthleteData(filePath);
//        System.out.println("Deserialized Athletes:");
//        for (Athlete athlete : deserializedAthletes) {
//            System.out.println(athlete);
//        }
//    }
}
