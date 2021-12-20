package hw19;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
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

    // Get an average of all the data for counties in one particular state.
    public double getStateAverage(String state){
        // Number of counties
        long numOfCounties = data.stream()
                .filter(value -> value.get(3).equals(state))
                .count();
        // Total up all the county scores
        double totalOfScores = data.stream()
                .filter(value -> value.get(3).equals(state))
                .map((List<String> county) -> county.get(0)) // getting the score as a string
                .reduce(0.0, (result, score) -> {return result + Double.parseDouble(score);},
                        (runningTotal, subTotal) -> runningTotal + subTotal);
        return totalOfScores / (double) numOfCounties;
    }

    public static void main(String[] args){
        String testPath = "hw4_testdata/Model_12.4_20211110_results.csv";
        CovidDailyData testData = new CovidDailyData(testPath);

        String testPath2 = "hw4_testdata/Model_12.4_20210113_results.csv";
        CovidDailyData testDataOld = new CovidDailyData(testPath2);

        // Has Massachusetts improved as a state over the test period?
        System.out.println("MA Jan 13: "
                + testDataOld.getStateAverage("Massachusetts"));
        System.out.println("MA Nov 10: "
                + testData.getStateAverage("Massachusetts"));

        // Has Washington improved as a state over the test period?
        System.out.println("\nWA Jan 13: "
                + testDataOld.getStateAverage("Washington"));
        System.out.println("WA Nov 10: "
                + testData.getStateAverage("Washington"));

        // Has Texas improved as a state over the test period?
        System.out.println("\nTX Jan 13: "
                + testDataOld.getStateAverage("Texas"));
        System.out.println("TX Nov 10: "
                + testData.getStateAverage("Texas"));

    }

}
