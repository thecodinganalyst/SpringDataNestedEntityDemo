package com.hevlar.springdatanestedentitydemo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Father {
    @Id
    @GeneratedValue
    Long id;
    String name;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "father", orphanRemoval = true)
    @Builder.Default
    List<Son> sonList = new ArrayList<>();

}
