package org.example.ejb;

import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.parkinglot.common.CarDto;
import org.example.parkinglot.common.CarPhotoDto;
import org.example.parkinglot.entities.Car;
import org.example.parkinglot.entities.CarPhoto;
import org.example.parkinglot.entities.UserGroup;
import org.example.parkinglot.entities.Users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CarsBean {
    private static final Logger LOG = Logger.getLogger(CarsBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public List<CarDto> findAllCars() {
        LOG.info("findAllCars");
        try {
            TypedQuery<Car> typedQuery = entityManager.createQuery(
                    "SELECT c FROM Car c", Car.class);

            List<Car> cars = typedQuery.getResultList();

            return copyCarsToDto(cars);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    public List<CarDto> copyCarsToDto(List<Car> cars) {
        List<CarDto> carDtos = new ArrayList<>();

        for (Car car : cars) {
            Long id = car.getId();
            String licensePlate = car.getLicensePlate();
            String parkingSpot = car.getParkingSpot();
            String ownerName = car.getOwner().getUsername();

            CarDto dto = new CarDto(id, licensePlate, parkingSpot, ownerName);
            carDtos.add(dto);
        }

        return carDtos;
    }
    public void createCar(String licensePlate, String parkingSpot, Long userId) {
        LOG.info("CreateCar");
        Car car = new Car();
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);
        Users user=entityManager.find(Users.class, userId);
        user.getCars().add(car);
        car.setOwner(user);
        entityManager.persist(car);

    }
    public CarDto findById(Long carId) {
        LOG.info("findById: " + carId);

        try {
            Car car = entityManager.find(Car.class, carId);
            if (car == null) {
                return null; // or throw exception if you prefer
            }

            Long id = car.getId();
            String licensePlate = car.getLicensePlate();
            String parkingSpot = car.getParkingSpot();
            String ownerName = car.getOwner().getUsername();

            return new CarDto(id, licensePlate, parkingSpot, ownerName);

        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }
    public void updateCar(Long carId, String licensePlate, String parkingSpot, Long userId) {
        LOG.info("updateCar");
        Car car = entityManager.find(Car.class, carId);
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);

        Users oldUser = car.getOwner();
        oldUser.getCars().remove(car);
        Users user = entityManager.find(Users.class, userId);
        user.getCars().add(car);
        car.setOwner(user);
    }
    public void deleteCarsByIds(Collection<Long> carIds) {
        LOG.info("deleteCarsByIds");
        for(Long carId : carIds) {
            Car car = entityManager.find(Car.class, carId);
            entityManager.remove(car);
        }
    }
    public void addPhotoToCar(Long carId, String filename, String fileType, byte[] fileContent) {
        LOG.info("addPhotoToCar");
        CarPhoto photo = new CarPhoto();
        photo.setFilename(filename);
        photo.setFileType(fileType);
        photo.setFileContent(fileContent);
        Car car = entityManager.find(Car.class, carId);
        if (car.getPhoto() != null) {
            entityManager.remove(car.getPhoto());
        }
        car.setPhoto(photo);
        photo.setCar(car);
        entityManager.persist(photo);
    }

    public CarPhotoDto findPhotoByCarId(Integer carId) {
        List<CarPhoto> photos = entityManager
                .createQuery("SELECT p FROM CarPhoto p where p.car.id = :id", CarPhoto.class)
                .setParameter("id", carId)
                .getResultList();
        if (photos.isEmpty()) {
            return null;
        }
        CarPhoto photo = photos.get(0); // the first element
        return new CarPhotoDto(photo.getId(), photo.getFilename(), photo.getFileType(),
                photo.getFileContent());
    }
    public int get_number_of_free_parkingspots(int number_of_parkingspots) {
        List<CarDto> curent_cars=findAllCars();
        return number_of_parkingspots-curent_cars.size();
    }

    public ArrayList<CarDto> sorteaza_masinile_dupa_owner(ArrayList<CarDto> cars) {
       //Am comentat pentru ca aveam eroare nu stiu de ce
       // Arrays.sort(cars,CarDto.carDtoComparator);
        return cars;


    }
    public List<CarDto> gaseste_masina_dupa_nr_inmatriculare(String nr_inmatriculare){
        try {
            TypedQuery<Car> typedQuery = entityManager.createQuery(
                    "SELECT c FROM Car c WHERE c.licensePlate=:license_plate", Car.class)
                    .setParameter("license_plate",nr_inmatriculare);

            List<Car> cars = typedQuery.getResultList();

            return copyCarsToDto(cars);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }


}
