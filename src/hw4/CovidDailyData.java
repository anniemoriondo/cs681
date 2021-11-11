package hw4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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

    public List<List<String>> getStateData(String state){
        // Get a list of all the data for counties in one particular state.
        return data.stream().filter(value -> value.get(3).equals(state))
                .collect(Collectors.toList());
    }

    public double stateAverage(String state){
        // Get all counties in this state
        List<List<String>> thisState = getStateData(state);
        // Parse the scores as doubles
        Double avgForCounties = thisState.stream()
                .map((List<String> values) -> Double.parseDouble(values.get(0)))
                .reduce();
        return 0;
    }

    // Getter method for data.
    public List<List<String>> getData(){ return data;}

    // Alphabetizes by state / county.
    public void alphabetizeByCounty(){
        data = data.stream().sorted(
                (List<String> county1, List<String> county2) ->
                    // "State + County" is item 21 in the dataset
                    county1.get(21).compareTo(county2.get(21))
                ).collect(Collectors.toList());
    }

    public boolean countiesMatch(CovidDailyData that){
        this.alphabetizeByCounty();
        that.alphabetizeByCounty();

        for(int i = 0; i < this.data.size(); i++){
            if (this.data.get(i).get(21) != that.getData().get(i).get(21)){
                System.out.println( "Mismatch in position " + i);
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args){
        String testPath = "hw4_testdata/Model_12.4_20211110_results.csv";
        CovidDailyData testData = new CovidDailyData(testPath);

        String testPath2 = "hw4_testdata/Model_12.4_20210113_results.csv";
        CovidDailyData testData2 = new CovidDailyData(testPath2);

        testData.alphabetizeByCounty();

        System.out.println(testData.stateAverage("Massachusetts"));
        String myNumber = "0.59329";
        Double myNumberAgain = Double.parseDouble(myNumber);
        System.out.println(myNumberAgain + 0.5);



    }

}
