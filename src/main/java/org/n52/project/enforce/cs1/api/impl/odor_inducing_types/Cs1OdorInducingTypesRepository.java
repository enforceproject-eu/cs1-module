package org.n52.project.enforce.cs1.api.impl.odor_inducing_types;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 * Cs1OdorInducingTypesRepository repository.
 * </p>
 *
 * @author Benjamin Pross 
 * @since 1.0.0
 */
public interface Cs1OdorInducingTypesRepository extends JpaRepository<Cs1OdorInducingTypes, Integer> {
    
    @Query("select d from Cs1OdorInducingTypes as d where d.name ilike :name")
    Optional<Cs1OdorInducingTypes> findByName(@Param("name") String name);
}
