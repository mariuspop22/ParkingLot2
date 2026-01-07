package org.example.parkinglot.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "car_photo")
public class CarPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String fileType;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] fileContent;

    @OneToOne(optional = false)
    @JoinColumn(name = "car_id", nullable = false, unique = true)
    private Car car;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
