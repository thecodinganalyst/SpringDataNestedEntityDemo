package com.hevlar.springdatanestedentitydemo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParentClass {
    @Id
    @GeneratedValue
    Long id;
    String name;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "parent", orphanRemoval = true)
    @Builder.Default
    List<ChildClass> children = new ArrayList<>();
}
