package org.n52.project.enforce.cs1.api.impl.geoquest;

import java.io.IOException;
import java.io.Serializable;

import org.n52.project.enforce.cs1.api.GeoquestApi;
import org.n52.project.enforce.cs1.utils.Cs1Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-04T13:56:21.301070100+01:00[Europe/Berlin]", comments = "Generator version: 7.13.0")
@Controller
@RequestMapping("${openapi.eNFORCEDataAccess.base-path:}")
public class GeoquestApiController implements GeoquestApi {

    private Cs1Utils cs1Utils;
    
    private Cs1DataRepository cs1DataRepository;
    
    @Autowired
    public GeoquestApiController(Cs1DataRepository cs1DataRepository, Cs1Utils cs1Utils) {
        this.cs1DataRepository = cs1DataRepository;
        this.cs1Utils = cs1Utils;
    }

    @Override
    public ResponseEntity<Serializable> addCs1GeoQuestDataAsBody(Resource body) {
        try {
            cs1Utils.readExcelFile(body.getInputStream());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Serializable> getCs1GeoQuestData() {
        try {
            return ResponseEntity.ok(cs1DataRepository.getGeoJson());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
