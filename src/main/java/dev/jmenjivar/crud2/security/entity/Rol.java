package dev.jmenjivar.crud2.security.entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


import dev.jmenjivar.crud2.security.enums.*;

@Entity
@Table(name = "Rol")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Rol implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Enumerated (EnumType.STRING)
    private RolNombre rolNombre;

    
    public Rol(@NotNull RolNombre rolNombre){
        this.rolNombre = rolNombre;
    }
}