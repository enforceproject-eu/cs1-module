package org.n52.project.enforce.cs1.api.impl;

import java.time.LocalDateTime;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.n52.project.enforce.cs1.api.impl.geoquest.Cs1Data;
import org.n52.project.enforce.cs1.api.impl.geoquest.Cs1DataRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class Cs1DataRepositoryTest extends DBTest {

    @Autowired
    Cs1DataRepository cs1DataRepository;
    
    GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
    
    Random random = new Random();
    
    @Test
    void testCreateData() {

        double x = random.nextDouble(50d);
        double y = random.nextDouble(8d);
        Cs1Data cs1Data = new Cs1Data();
        
        cs1Data.setBurnSurface(0d);
        cs1Data.setCoordinate(geometryFactory.createPoint(new Coordinate(x,y)));
        cs1Data.setDate(LocalDateTime.now());
        cs1Data.setIncidentTypeId(1);
        cs1Data.setIntensity(0d);
        cs1Data.setLink("link");
        cs1Data.setMunicipalityId(1);
        cs1Data.setOdorInducingTypeId(1);
        cs1Data.setPlace("place");
        cs1Data.setVisibleSmoke(true);
        
        cs1DataRepository.saveAndFlush(cs1Data);
    }
    
}
