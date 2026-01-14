package org.example.parkinglot.common;
import java.util.*;
import java.util.Comparator;

public class CarDto implements Comparable<CarDto>{
    Long id;
    String licensePlate;
    String parkingSpot;
    String ownerName;


    public CarDto(Long id, String licensePlate, String parkingSpot, String ownerName) {

        this.id = id;
        this.licensePlate = licensePlate;
        this.parkingSpot = parkingSpot;
        this.ownerName = ownerName;

    }
    public Long getId() {
        return id;
    }
    public String getLicensePlate() {
        return licensePlate;
    }
    public String getParkingSpot() {
        return parkingSpot;

    }
    public String getOwnerName() {
        return ownerName;

    }

    public int compareTo(CarDto o) {

        return 0;
    }

    public static Comparator<CarDto> carDtoComparator = new Comparator<CarDto>() {

        public int compare(CarDto car1,CarDto car2) {
            String car1ownerName = car1.getOwnerName();
            String car2ownerName = car2.getOwnerName();
            return car1ownerName.compareTo(car2ownerName);
            //descending order
            //return fruitName2.compareTo(fruitName1);
        }

    };
}
