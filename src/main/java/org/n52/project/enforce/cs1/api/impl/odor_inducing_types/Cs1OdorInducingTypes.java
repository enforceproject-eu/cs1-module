package org.n52.project.enforce.cs1.api.impl.odor_inducing_types;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * <p>
 * Data DTO.
 * </p>
 *
 * @author Benjamin Pross (b.pross @52north.org)
 * @since 1.0.0
 */
@Entity
@Table(
        name = "cs1_odor_inducing_types")
public class Cs1OdorInducingTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cs1_odor_inducing_types_generator")
    @SequenceGenerator(name = "cs1_odor_inducing_types_generator", sequenceName = "cs1_odor_inducing_types_seq", allocationSize = 1)
    private Integer id;

    @Column(
            name = "name")
    private String name;

    @Column(
            name = "name_eng")
    private String nameEng;
    
    public Cs1OdorInducingTypes() {}
    
    public Cs1OdorInducingTypes(String name) {
        this.name = name;
    }
    
    public Cs1OdorInducingTypes(String name, String nameEng) {
        this(name);
        this.nameEng = nameEng;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("name: " + name + ", ");
        sb.append("nameEng: " + nameEng + ", ");
        return sb.toString();
    }
}
