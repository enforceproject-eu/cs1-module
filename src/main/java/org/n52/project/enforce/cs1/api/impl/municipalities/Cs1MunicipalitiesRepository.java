package org.n52.project.enforce.cs1.api.impl.municipalities;

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
public interface Cs1MunicipalitiesRepository extends JpaRepository<Cs1Municipalities, Integer> {

    @Query("select d from Cs1Municipalities as d where d.name ilike :name")
    Optional<Cs1Municipalities> findByName(@Param("name") String name);
}
