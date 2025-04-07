package com.example.demo.jpa;

import com.example.demo.bo.Car;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * @author i565244
 */
public class DBClient {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cars-pu");
        EntityManager eman = emf.createEntityManager();

        try {

            String sql = "SELECT c FROM Car c";
            Query query = eman.createQuery(sql);
            List<Car> cars = query.getResultList();

            for (Car car : cars) {
                System.out.printf("%d ", car.getId());
                System.out.printf("%s ", car.getName());
                System.out.println(car.getPrice());
            }

        } finally {
            eman.close();
            emf.close();
        }
    }
}
