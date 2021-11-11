package hw4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.nio.file.Path;
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
                            .collect(Collectors.toList());
                    // Outer nested list
                    }).collect(Collectors.toList());
            headers = matrix.get(0);
            data = matrix.subList(1, matrix.size());
        } catch (IOException ex){
            System.out.println(ex);
        }
    }

    // Getter method for data.
    public List<List<String>> getData(){ return data;}

    // Alphabetizes by county. Can be used to compare across files,
    // as the same list of counties is maintained throughout study.
    public void alphabetizeByCounty(){
        data = data.stream().sorted(
                (List<String> county1, List<String> county2) ->
                    // County is item 3 in the dataset
                    county1.get(3).compareTo(county2.get(3))
                ).collect(Collectors.toList());
    }



    public static void main(String[] args){
        String testPath = "hw4_testdata/Model_12.4_20211110_results.csv";
        CovidDailyData testData = new CovidDailyData(testPath);

        for (List<String> item: testData.getData()){
            System.out.println(item.get(1) + " " + item.get(2) +
                    item.get(3) + item.get(4));
        }

        testData.alphabetizeByCounty();



    }

}
