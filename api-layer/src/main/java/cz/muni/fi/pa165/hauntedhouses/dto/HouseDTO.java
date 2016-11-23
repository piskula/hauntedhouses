package cz.muni.fi.pa165.hauntedhouses.dto;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * DTO object for House
 *
 * Created by Ondro on 09-Nov-16.
 */
public class HouseDTO {

    private Long id;

    @NotNull
    private String address;

    @NotNull
    private String name;

    private Set<MonsterDTO> monsters = new HashSet<>();

    private Set<CursedObjectDTO> cursedObjects = new HashSet<>();

    //getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<MonsterDTO> getMonsters() {
        return monsters;
    }

    public void setMonsters(Set<MonsterDTO> monsters) {
        this.monsters = monsters;
    }

    public Set<CursedObjectDTO> getCursedObjects() {
        return cursedObjects;
    }

    public void setCursedObjects(Set<CursedObjectDTO> cursedObjects) {
        this.cursedObjects = cursedObjects;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this) return true;
        if(obj == null) return false;
        if(!(obj instanceof HouseDTO)) return false;

        final HouseDTO other = (HouseDTO) obj;
        if (getName() != null ? !getName().equals(other.getName()) : other.getName() != null) return false;
        if (getAddress() != null ? !getAddress().equals(other.getAddress()) : other.getAddress() != null) return false;

        return true;
    }

    @Override
    public int hashCode(){
        return Objects.hash(name, address);
    }

    @Override
    public String toString(){
        return "HouseCreateDTO{name=" + getName() + ", address=" + getAddress() + "}";
    }
}