package org.n52.project.enforce.cs1.api.impl;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.n52.project.enforce.cs1.utils.Cs1Utils;
import org.springframework.beans.factory.annotation.Autowired;

public class Cs1UtilsTest extends DBTest{
    
    @Autowired
    Cs1Utils cs1Utils;
    
    @Test
    void testReadExcelFile() {
        try {
            cs1Utils.readExcelFile(getClass().getResourceAsStream("test_data.xlsx"));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }    
    
}
