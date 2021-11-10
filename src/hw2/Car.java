package hw2;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// The Car class - exactly as present in CS 680 HW 14.
public class Car {
    private String make, model;
    private int mileage, year, price;

    // Domination count, for Pareto comparisons.
    private int dominationCount = 0;

    public Car(String make, String model, int mileage, int year, int price){
        this.make = make;
        this.model = model;
        this.mileage = mileage;
        this.year = year;
        this.price = price;
    }

    // Getter methods.
    public String getMake(){
        return this.make;
    }

    public String getModel(){
        return this.model;
    }

    public String getMakeAndModel() { return this.make + " " + this.model;}

    public int getMileage(){
        return this.mileage;
    }

    public int getYear(){
        return this.year;
    }

    public int getPrice() {
        return this.price;
    }

    public int getDominationCount(){ return this.dominationCount;}

    public void setDominationCount(int newCount){
        this.dominationCount = newCount;
    }

    // Does this car dominate another car?
    public boolean dominates(Car otherCar){
        // Does this car tie the other car on all criteria?
        boolean priceTies = this.price <= otherCar.getPrice();
        boolean yearTies = this.year >= otherCar.getYear();
        boolean mileageTies = this.mileage <= otherCar.getMileage();
        // Is at least one criterion strictly better?
        boolean oneBetter = this.price < otherCar.getPrice() ||
                this.year > otherCar.getYear() ||
                this.mileage < otherCar.getMileage();

        return priceTies && yearTies && mileageTies && oneBetter;
    }

    public static void computeDominationCounts(LinkedList<Car> cars){
        for (Car thisCar : cars){
            thisCar.setDominationCount(0);
        }
        for (Car thisCar : cars){
            for (Car thatCar : cars){
                if (thisCar.dominates(thatCar)){
                    // Increase the domination count of the car that gets dominated
                    thatCar.setDominationCount(thatCar.getDominationCount() + 1);
                }
            }
        }
    }


    public static void main(String[] args){

        // Sample cars to sort.
        LinkedList<Car> cars = new LinkedList<>();
        cars.add( new Car("Ford", "Fusion", 0, 2021, 40000));
        cars.add( new Car("Subaru", "Forester", 90000, 2017, 30000));
        cars.add( new Car("Toyota", "Corolla", 20000, 2020, 45000));
        cars.add( new Car("Ford", "Explorer", 10000, 2019, 50000));
        cars.add( new Car("Chevrolet", "Camaro", 80000, 2010, 75000));
        // TODO finish this assignment

        // Sorting the cars using Stream API and lambda expressions.

        // Sort policy 1: Sort the cars from least to most mileage
        List<Car> ascendingMileage = cars.stream()
                .sorted((Car car1, Car car2) ->
                        car1.getMileage() - car2.getMileage() )
                .collect(Collectors.toList());

        // Verify sort by ascending mileage
        System.out.println("Ascending mileage:");
        for (Car thisCar : ascendingMileage) {
            System.out.println(thisCar.getMakeAndModel() + " - "
                    + thisCar.getMileage() + " miles");
        }

        // Sort policy 2: Sort the cars from newest to oldest
        List<Car> descendingYear = cars.stream()
                .sorted((Car car1, Car car2) -> car2.getYear() - car1.getYear())
                .collect(Collectors.toList());

        // Verify sort by descending year (i.e. newest to oldest)
        System.out.println("\nNewest to oldest:");
        for (Car thisCar : descendingYear) {
            System.out.println(thisCar.getYear() + " " +
                    thisCar.getMakeAndModel());
        }

        // Sort policy 3: Sort the cars from least to most expensive
        List<Car> ascendingPrice = cars.stream()
                .sorted((Car car1, Car car2)
                        -> car1.getPrice() - car2.getPrice())
                .collect(Collectors.toList());

        // Verify sort by ascending price
        System.out.println("\nLeast to most expensive:");
        for (Car thisCar : ascendingPrice) {
            System.out.println(thisCar.getMakeAndModel() + " - $"
                + thisCar.getPrice());
        }

        // Sorting policy 4: Pareto sort (ascending domination count)
        computeDominationCounts(cars);
        List<Car> paretoSorted = cars.stream()
                .sorted((Car car1, Car car2) ->
                        car1.getDominationCount() - car2.getDominationCount()
                ).collect(Collectors.toList());

        // Verify sort by ascending domination count
        System.out.println("\nPareto sort (# of cars that dominate):");
        for(Car thisCar : paretoSorted){
            System.out.println(thisCar.getMakeAndModel() + " ("
                + thisCar.getDominationCount() + ")");
        }

    }
}