package com.university.entities;

import javax.persistence.*;

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

        if (id != that.id) return false;
        if (Double.compare(that.costPerDay, costPerDay) != 0) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + name.hashCode();
        temp = Double.doubleToLongBits(costPerDay);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
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
