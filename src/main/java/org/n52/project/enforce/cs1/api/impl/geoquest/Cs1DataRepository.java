package org.n52.project.enforce.cs1.api.impl.geoquest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * <p>
 * Data repository.
 * </p>
 *
 * @author Benjamin Pross 
 * @since 1.0.0
 */
public interface Cs1DataRepository extends JpaRepository<Cs1Data, Integer> {
    
    /**
     * <p>
     * getGeoJson.
     * </p>
     * 
     * @return a {@link String} object
     */
    @Query("select ST_CS1DataToGeoJson()")
    String getGeoJson();
}
