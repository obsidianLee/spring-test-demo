package com.test;

import com.test.demo.Demo;
import com.test.demo.Demo2;
import com.test.repository.ClassOneRepository;
import lombok.extern.slf4j.Slf4j;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.UUID;

/**
 * @author Lixueyuan
 * @date 2019/9/14 0:47
 */
@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.test"})
@EnableTransactionManagement(mode = AdviceMode.PROXY)
public class Application implements ApplicationRunner {
    @Autowired
    Demo demo;
    @Autowired
    Demo2 demo2;
    @Autowired
    GridDoRepository gridRepository;
    @Autowired
    ClassOneRepository classOneRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        GridDO gd = new GridDO();
        gd.setKey(UUID.randomUUID().toString().replace("-", ""));
        gd.setName("gggggggggggg");
        Coordinate[] coords  =
                new Coordinate[] {new Coordinate(4, 0), new Coordinate(2, 2),
                        new Coordinate(4, 4), new Coordinate(6, 2), new Coordinate(4, 0) };
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        LinearRing ring = geometryFactory.createLinearRing(coords);
        Polygon polygon = geometryFactory.createPolygon(ring, null);
        gd.setShape(polygon);
        gridRepository.save(gd);
    }
}

