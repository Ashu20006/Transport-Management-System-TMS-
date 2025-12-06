package com.ashutosh.tms.entity;

import com.ashutosh.tms.entity.enums.LoadStatus;
import com.ashutosh.tms.entity.enums.WeightUnit;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "loads")
public class Load {

    @Id
    @GeneratedValue
    @Column(name = "load_id")
    private UUID loadId;

    private String shipperId;

    private String loadingCity;
    private String unloadingCity;

    private Instant loadingDate;

    private String productType;

    private double weight;

    @Enumerated(EnumType.STRING)
    private WeightUnit weightUnit;

    private String truckType;

    private int noOfTrucks;

    @Enumerated(EnumType.STRING)
    private LoadStatus status;

    private Instant datePosted;

    @Version
    private Long version; // for optimistic locking

    // ---------------- GETTERS & SETTERS ----------------

    public UUID getLoadId() { return loadId; }
    public void setLoadId(UUID loadId) { this.loadId = loadId; }

    public String getShipperId() { return shipperId; }
    public void setShipperId(String shipperId) { this.shipperId = shipperId; }

    public String getLoadingCity() { return loadingCity; }
    public void setLoadingCity(String loadingCity) { this.loadingCity = loadingCity; }

    public String getUnloadingCity() { return unloadingCity; }
    public void setUnloadingCity(String unloadingCity) { this.unloadingCity = unloadingCity; }

    public Instant getLoadingDate() { return loadingDate; }
    public void setLoadingDate(Instant loadingDate) { this.loadingDate = loadingDate; }

    public String getProductType() { return productType; }
    public void setProductType(String productType) { this.productType = productType; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public WeightUnit getWeightUnit() { return weightUnit; }
    public void setWeightUnit(WeightUnit weightUnit) { this.weightUnit = weightUnit; }

    public String getTruckType() { return truckType; }
    public void setTruckType(String truckType) { this.truckType = truckType; }

    public int getNoOfTrucks() { return noOfTrucks; }
    public void setNoOfTrucks(int noOfTrucks) { this.noOfTrucks = noOfTrucks; }

    public LoadStatus getStatus() { return status; }
    public void setStatus(LoadStatus status) { this.status = status; }

    public Instant getDatePosted() { return datePosted; }
    public void setDatePosted(Instant datePosted) { this.datePosted = datePosted; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
}
