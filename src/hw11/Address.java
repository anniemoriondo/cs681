package hw11;

public final class Address {
    private final String street, city, state;
    private final int zipcode;

    public Address(String street, String city, String state, int zipcode){
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

    // Getter methods
    public String getStreet(){ return street; }
    public String getCity(){ return city; }
    public String getState(){ return state; }
    public int getZipcode() { return zipcode; }

    // The whole address as a string.
    public String toString(){
        return street + ", " + city + " " + state + " " + zipcode;
    }

    // Compares two Addresses
    public boolean equals(Address otherAddress){
        return this.toString().equals(otherAddress.toString());
    }

    // Not a setter!
    public Address change(String street, String city, String state,
                          int zipcode){
        return new Address(street, city, state, zipcode);
    }

    public static void main(String[] args){
        Address whiteHouse = new Address("1600 Pennsylvania Avenue",
                "Washington", "DC", 20500);
        System.out.println(whiteHouse);
    }
}
