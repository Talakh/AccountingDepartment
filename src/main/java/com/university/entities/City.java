package com.university.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "city")
public class City {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "Name", nullable = false, length = 25)
    private String name;

    @Column(name = "TravelCost", nullable = false)
    private double travelCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Category_Id", nullable = false)
    private CityCategory cityCategory;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTravelCost() {
        return travelCost;
    }

    public void setTravelCost(double travelCost) {
        this.travelCost = travelCost;
    }

    public CityCategory getCityCategory() {
        return cityCategory;
    }

    public void setCityCategory(CityCategory cityCategory) {
        this.cityCategory = cityCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return id == city.id &&
                Double.compare(city.travelCost, travelCost) == 0 &&
                Objects.equals(name, city.name) &&
                Objects.equals(cityCategory, city.cityCategory);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, travelCost, cityCategory);
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", travelCost=" + travelCost +
                ", cityCategory=" + cityCategory +
                '}';
    }
}
