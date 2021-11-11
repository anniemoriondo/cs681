package hw4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class CovidDailyData {

    List<String> headers;
    List<List<String>> data;

    public CovidDailyData(String dataPath){
        // Get the file with data. Path relative to project root dir.
        Path path = Paths.get(dataPath);

        try(Stream<String> lines = Files.lines(path)){
            // Parse the csv data into a nested list
            List<List<String>> matrix = lines.map(line ->{
                    return Stream.of( line.split(","))
                            .map(value -> value.substring(1, value.length())
                                    // Get rid of unnecessary quotation marks
                                    .replace("\"", ""))
                            // Inner nested list
                            .collect(Collectors.toList()); })
                    // Outer nested list
                    .map(value -> {
                        // Add a single column that has both state and county.
                        value.add(value.get(3) + " " + value.get(4));
                        return value;
                    })
                    .collect(Collectors.toList());
            headers = matrix.get(0);
            data = matrix.subList(1, matrix.size());
        } catch (IOException ex){
            System.out.println(ex);
        }
    }

    // Getter method for data.
    public List<List<String>> getData(){ return data;}

    // Get a list of all the data for counties in one particular state.
    public List<List<String>> getStateData(String state){
        // Returns an empty stream if the search string is not a state.
        return data.stream().filter(value -> value.get(3).equals(state))
                .collect(Collectors.toList());
    }

    // Get a map of county names to scores for one particular state.
    public HashMap<String, Double> stateScoresByCounty(String state){
        List<List<String>> stateData = getStateData(state);
        HashMap<String, Double> scores = new HashMap<>();
        for (List<String> county : stateData){
            // Store the county name and its score
            String name = county.get(4);
            Double score = Double.parseDouble(county.get(0));
            scores.put(name, score);
        }
        return scores;
    }

    public Double averageScoreForState(String state){
        // Returns average score for all counties in state.
        HashMap<String, Double> stateScores = stateScoresByCounty(state);
        Double sum = 0.0;
        for (String key : stateScores.keySet()){ sum += stateScores.get(key);}
        return sum / stateScores.size();
    }

    public static void main(String[] args){
        String testPath = "hw4_testdata/Model_12.4_20211110_results.csv";
        CovidDailyData testData = new CovidDailyData(testPath);

        String testPath2 = "hw4_testdata/Model_12.4_20210113_results.csv";
        CovidDailyData testDataOld = new CovidDailyData(testPath2);

        // Has Massachusetts improved as a state over this calendar year?
        System.out.println("MA Jan 13: "
                + testDataOld.averageScoreForState("Massachusetts"));
        System.out.println("MA Nov 10: "
                + testData.averageScoreForState("Massachusetts"));

        // Has Washington improved as a state over this calendar year?
        System.out.println("\nWA Jan 13: "
                + testDataOld.averageScoreForState("Washington"));
        System.out.println("WA Nov 10: "
                + testData.averageScoreForState("Washington"));

        // Has Texas improved as a state over this calendar year?
        System.out.println("\nTX Jan 13: "
                + testDataOld.averageScoreForState("Texas"));
        System.out.println("TX Nov 10: "
                + testData.averageScoreForState("Texas"));

    }

}
