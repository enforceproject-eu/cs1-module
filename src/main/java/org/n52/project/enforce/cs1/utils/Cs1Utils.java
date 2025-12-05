package org.n52.project.enforce.cs1.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.n52.project.enforce.cs1.api.impl.geoquest.Cs1Data;
import org.n52.project.enforce.cs1.api.impl.geoquest.Cs1DataRepository;
import org.n52.project.enforce.cs1.api.impl.incident_types.Cs1IncidentTypes;
import org.n52.project.enforce.cs1.api.impl.incident_types.Cs1IncidentTypesRepository;
import org.n52.project.enforce.cs1.api.impl.municipalities.Cs1Municipalities;
import org.n52.project.enforce.cs1.api.impl.municipalities.Cs1MunicipalitiesRepository;
import org.n52.project.enforce.cs1.api.impl.odor_inducing_types.Cs1OdorInducingTypes;
import org.n52.project.enforce.cs1.api.impl.odor_inducing_types.Cs1OdorInducingTypesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Cs1Utils {

    private Cs1DataRepository cs1DataRepository;
    
    private Cs1IncidentTypesRepository cs1IncidentTypesRepository;    
    
    private Cs1MunicipalitiesRepository cs1MunicipalitiesRepository;
    
    private Cs1OdorInducingTypesRepository cs1OdorInducingTypesRepository;

    DateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd'T'HH:MM:SS");

    ZoneId zoneIdEuropeRome = ZoneId.of("Europe/Rome");
    
    GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    private static Logger LOG = LoggerFactory.getLogger(Cs1Utils.class);

    public Cs1Utils(Cs1DataRepository cs1DataRepository, Cs1IncidentTypesRepository cs1IncidentTypesRepository, Cs1MunicipalitiesRepository cs1MunicipalitiesRepository,
            Cs1OdorInducingTypesRepository cs1OdorInducingTypesRepository) {
        this.cs1DataRepository = cs1DataRepository;
        this.cs1IncidentTypesRepository = cs1IncidentTypesRepository;
        this.cs1MunicipalitiesRepository = cs1MunicipalitiesRepository;
        this.cs1OdorInducingTypesRepository = cs1OdorInducingTypesRepository;
    }
    
    public void readExcelFile(InputStream inputstream) throws IOException {
        File tmpExcelFile = File.createTempFile("excel", ".xlsx");
        IOUtils.copy(inputstream, new FileOutputStream(tmpExcelFile));
        readExcelFile(tmpExcelFile);
    }
    
    public void readExcelFile(File excelFile) throws IOException {
        Workbook workbook = WorkbookFactory.create(excelFile);
        Sheet sheet = workbook.getSheetAt(0);
        Row firstRow = sheet.getRow(0);
        int rowCount = sheet.getLastRowNum();
        int columnCount = firstRow.getLastCellNum();
        Cs1Data data = null;
        Row row = null;
        Cell cell = null;
        for (int i = 1; i <= rowCount; i++) {
            row = sheet.getRow(i);
            if(row == null) {
                LOG.info(String.format("Stop parsing excel file at row %d, which is reported null. Estimated row count: %d.", i, rowCount));
                break;
            }
            data = new Cs1Data();
            for (int k = 0; k < columnCount; k++) {
                cell = row.getCell(k);
                if(cell == null) {
                    LOG.info(String.format("Cell %d is null for row %d. Skipping value.", k, i));
                    continue;
                }
                switch (k) {
                case 0:
                    String incidentTypeFromCellValue = cell.getStringCellValue().trim();
                    int incidentTypeId = 1;
                    if(incidentTypeFromCellValue != null && !incidentTypeFromCellValue.isBlank()) {
                        Optional<Cs1IncidentTypes> incidentTypeOpt = cs1IncidentTypesRepository.findByName(incidentTypeFromCellValue);
                        if(incidentTypeOpt.isPresent()) {
                            incidentTypeId = incidentTypeOpt.get().getId();
                        } else {
                            Cs1IncidentTypes incidentTypes = new Cs1IncidentTypes(incidentTypeFromCellValue);
                            incidentTypeId = cs1IncidentTypesRepository.saveAndFlush(incidentTypes).getId();
                        }
                    }
                    data.setIncidentTypeId(incidentTypeId);
                    break;
                case 1:
                    String municipalitiyFromCellValue = cell.getStringCellValue().trim();
                    int municipalitiyId = 1;
                    if(municipalitiyFromCellValue != null && !municipalitiyFromCellValue.isBlank()) {
                        Optional<Cs1Municipalities> municipalityOpt = cs1MunicipalitiesRepository.findByName(municipalitiyFromCellValue);
                        if(municipalityOpt.isPresent()) {
                            municipalitiyId = municipalityOpt.get().getId();
                        } else {
                            Cs1Municipalities incidentTypes = new Cs1Municipalities(municipalitiyFromCellValue);
                            municipalitiyId = cs1MunicipalitiesRepository.saveAndFlush(incidentTypes).getId();
                        }
                    }
                    data.setMunicipalityId(municipalitiyId);
                    break;
                case 2:
                    data.setPlace(cell.getStringCellValue());
                    break;
                case 3:
                    String dateString = cell.getStringCellValue();
                    try {
                        data.setDate(LocalDateTime.ofInstant(dateFormat.parse(dateString).toInstant(), zoneIdEuropeRome));
                    } catch (ParseException e) {
                        LOG.error("Could not parse date: " + dateString);
                        break;
                    }
                    break;
                case 4:
                    String odorInducingTypesFromCellValue = cell.getStringCellValue().trim();
                    int odorInducingTypesId = 1;
                    if(odorInducingTypesFromCellValue != null && !odorInducingTypesFromCellValue.isBlank()) {
                        Optional<Cs1OdorInducingTypes> odorInducingTypesOpt = cs1OdorInducingTypesRepository.findByName(odorInducingTypesFromCellValue);
                        if(odorInducingTypesOpt.isPresent()) {
                            odorInducingTypesId = odorInducingTypesOpt.get().getId();
                        } else {
                            Cs1OdorInducingTypes odorInducingTypes = new Cs1OdorInducingTypes(odorInducingTypesFromCellValue);
                            odorInducingTypesId = cs1OdorInducingTypesRepository.saveAndFlush(odorInducingTypes).getId();
                        }
                    }
                    data.setOdorInducingTypeId(odorInducingTypesId);
                    break;
                case 5:
                    double intensity = -1d;
                    if(cell.getCellType().equals(CellType.NUMERIC)) {
                        intensity = cell.getNumericCellValue();
                    }                     
                    data.setIntensity(intensity);
                    break;
                case 6:
                    double burntSurface = -1d;
                    if(cell.getCellType().equals(CellType.NUMERIC)) {
                        burntSurface = cell.getNumericCellValue();
                    }                     
                    data.setBurnSurface(burntSurface);
                    break;
                case 7:
                    Boolean visibleSmoke = null;
                    if(cell.getCellType().equals(CellType.STRING)) {
                        String visibleSmokeString = cell.getStringCellValue();
                        if(visibleSmokeString.equalsIgnoreCase("Sì")) {
                            visibleSmoke = true;
                        } else if(visibleSmokeString.equalsIgnoreCase("No")) {
                            visibleSmoke = false;
                        }
                    }   
                    data.setVisibleSmoke(visibleSmoke);
                    break;
                case 8:
                    data.setLink(cell.getStringCellValue());
                    break;
                case 9:
                    data.setCoordinate(this.createPoint(cell.getStringCellValue()));
                    break;
                default:
                    break;
                }
            }
            data = cs1DataRepository.saveAndFlush(data);
            LOG.info(String.format("Data with id %d added to cs1 data.", data.getId()));
        }
        workbook.close();
    }

    private Point createPoint(String pointAsString) {
        if (pointAsString != null) {
            String[] coordinateArray = pointAsString.split(",");
            if (coordinateArray.length == 2) {
                String latStrg = coordinateArray[1];
                String lngStrg = coordinateArray[0];
                if ((latStrg != null && !latStrg.isEmpty()) && (lngStrg != null && !lngStrg.isEmpty())) {
                    double lat = Double.parseDouble(latStrg);
                    double lng = Double.parseDouble(lngStrg);
                    return geometryFactory.createPoint(new Coordinate(lat, lng));
                }
            }
        }
        return geometryFactory.createPoint(new Coordinate(0, 0));
    }

}
