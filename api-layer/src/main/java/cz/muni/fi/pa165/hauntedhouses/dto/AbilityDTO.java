/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.hauntedhouses.dto;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotNull;

/**
 * Data transfer object for class Ability.
 * @author Kristyna Loukotova
 * @version 17.11.2016
 */
public class AbilityDTO {
    @NotNull
    private Long id;

    @NotNull
    private String name;

    private String description;

    private Set<Long> monsterIds = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Set<Long> getMonsterIds() {
        return monsterIds;
    }

    public void addMonsterId(Long monsterId) {
        monsterIds.add(monsterId);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        AbilityDTO other = (AbilityDTO) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return "{id=" + id + "}";
    }
}