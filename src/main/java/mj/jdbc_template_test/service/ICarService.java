package mj.jdbc_template_test.service;

import mj.jdbc_template_test.model.Car;

import java.util.List;

public interface ICarService {

    Car findByName(String name);
    List<Car> findAll();
}
