/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.hauntedhouses.entity;

import java.time.LocalTime;
import java.util.Objects;
import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Marek Janco
 */
@Entity
@Table(name = "Monsters")
public class Monster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(unique = true)
    private String name;
    
    @NotNull
    private String description;
    
    @NotNull
    private LocalTime hauntedIntervalStart;

    @NotNull
    private LocalTime hauntedIntervalEnd;

    @ManyToOne
    private House house;
    
    @ManyToMany
    private Set<Ability> abilities = new HashSet<>(); 
    
    public Monster() {
    }
    
    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalTime getHauntedIntervalEnd() {
        return hauntedIntervalEnd;
    }

    public LocalTime getHauntedIntervalStart() {
        return hauntedIntervalStart;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHauntedIntervalEnd(LocalTime hauntedIntervalEnd) {
        if(this.hauntedIntervalStart != null){
            if (hauntedIntervalEnd.isBefore(this.hauntedIntervalStart)){
                throw new IllegalArgumentException("end of hauntedInterval si before start");
            }
        }
        this.hauntedIntervalEnd = hauntedIntervalEnd;
    }

    public void setHauntedIntervalStart(LocalTime hauntedIntervalStart) {
        if(this.hauntedIntervalEnd != null){
            if (hauntedIntervalStart.isAfter(hauntedIntervalEnd)) {
                throw new IllegalArgumentException("start of hauntedInterval si after end");
            }
        }
        this.hauntedIntervalStart = hauntedIntervalStart;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Set<Ability> getAbilities() {
        return abilities;
    }

    public void addAbility(Ability ability) {
        this.abilities.add(ability);
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if(obj == this){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(!(obj instanceof Monster)){
            return false;
        }
        
        final Monster monster = (Monster) obj;
        if( !monster.getId().equals( this.getId() ) ){
            return false;
        }
        return true;
    } 

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.name);
        return hash;
    }
}
