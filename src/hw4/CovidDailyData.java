package hw4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CovidDailyData {

    List<List<String>> data;

    public CovidDailyData(String dataPath){
        // Get the file with data
        Path path = Paths.get(dataPath);

        try(Stream<String> lines = Files.lines(path)){
            // Parse the csv data into a nested list
            List<List<String>> matrix = lines.map(line ->{
                    return Stream.of( line.split(","))
                            .map(value -> value.substring(1, value.length()-2))
                            // Inner nested list
                            .collect(Collectors.toList());
                    // Outer nested list
                    }).collect(Collectors.toList());
            data = matrix;
        } catch (IOException ex){
            System.out.println(ex);
        }
    }

    public List<List<String>> getData(){ return data;}

    public static void main(String[] args){
        String testPath = "hw4_testdata/Model_12.4_20211110_results.csv";
        CovidDailyData testData = new CovidDailyData(testPath);
        List<List<String> >dataList = testData.getData();
        for(List<String> item : dataList ){System.out.println(item.get(0));}

//        Path myPath = Paths.get("./hw2.xml");
//        try (Stream<String> myLines = Files.lines(myPath)){
//            System.out.println(myLines.collect(Collectors.toList()).get(0));
//        }
//        catch(IOException e){
//            System.out.println(e);
//        }


    }

}
