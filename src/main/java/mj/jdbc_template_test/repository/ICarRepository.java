package mj.jdbc_template_test.repository;

import mj.jdbc_template_test.model.Car;

import java.util.List;

public interface ICarRepository {

    void saveCar(Car car);
    Car findCarByName(String name);
    List<Car> findAll();
}
