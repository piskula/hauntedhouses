package cz.muni.fi.pa165.hauntedhouses.dto;

import cz.muni.fi.pa165.hauntedhouses.enums.MonsterAttractionFactor;

import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * @author Filip Petrovic (422334)
 */
public class CursedObjectCreateDTO {
    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private MonsterAttractionFactor monsterAttractionFactor;
    
    @NotNull
    private Long houseId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MonsterAttractionFactor getMonsterAttractionFactor() {
        return monsterAttractionFactor;
    }

    public void setMonsterAttractionFactor(MonsterAttractionFactor monsterAttractionFactor) {
        this.monsterAttractionFactor = monsterAttractionFactor;
    }
    
    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof CursedObjectCreateDTO)) {
            return false;
        }

        if(name == null && ((CursedObjectCreateDTO) other).getName() != null) {
            return false;
        }

        return name.equals(((CursedObjectCreateDTO) other).getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    
    @Override
    public String toString(){
        return "CursedObjectCreateDTO{name = " + getName() + ", description = " + getDescription() + "}";
    }
}
