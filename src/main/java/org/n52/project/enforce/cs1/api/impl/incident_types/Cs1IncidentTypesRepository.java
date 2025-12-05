package org.n52.project.enforce.cs1.api.impl.incident_types;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 * Data repository.
 * </p>
 *
 * @author Benjamin Pross 
 * @since 1.0.0
 */
public interface Cs1IncidentTypesRepository extends JpaRepository<Cs1IncidentTypes, Integer> {    

    @Query("select d from Cs1IncidentTypes as d where d.name ilike :name")
    Optional<Cs1IncidentTypes> findByName(@Param("name") String name);
}
