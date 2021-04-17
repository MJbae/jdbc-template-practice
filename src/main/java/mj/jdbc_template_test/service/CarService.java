package mj.jdbc_template_test.service;

import mj.jdbc_template_test.model.Car;
import mj.jdbc_template_test.repository.ICarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService implements ICarService {

    @Autowired
    private ICarRepository carRepository;

    public Car findByName(String name) {

        return carRepository.findCarByName(name);
    }

    public List<Car> findAll() {

        return carRepository.findAll();
    }
}
