package org.n52.project.enforce.cs1.api.impl;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.n52.project.enforce.cs1.api.impl.odor_inducing_types.Cs1OdorInducingTypes;
import org.n52.project.enforce.cs1.api.impl.odor_inducing_types.Cs1OdorInducingTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class Cs1OdorInducingTypesRepositoryTest extends DBTest {

    @Autowired
    Cs1OdorInducingTypesRepository cs1OdorInducingTypesRepository;
    
    @Test
    void TestAddOdorInducingType() {
        Cs1OdorInducingTypes entity = new Cs1OdorInducingTypes("test");
        cs1OdorInducingTypesRepository.saveAndFlush(entity);
        
        assertTrue(cs1OdorInducingTypesRepository.findByName("fuliggine").isPresent());
    }
    
}
