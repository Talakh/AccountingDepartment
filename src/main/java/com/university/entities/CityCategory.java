package com.university.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "city_category")
public class CityCategory {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "Name", nullable = false, length = 25)
    private String name;

    @Column(name = "CostPerDay", nullable = false)
    private double costPerDay;

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

    public double getCostPerDay() {
        return costPerDay;
    }

    public void setCostPerDay(double costPerDay) {
        this.costPerDay = costPerDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityCategory that = (CityCategory) o;
        return id == that.id &&
                Double.compare(that.costPerDay, costPerDay) == 0 &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, costPerDay);
    }

    @Override
    public String toString() {
        return "CityCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", costPerDay=" + costPerDay +
                '}';
    }
}
