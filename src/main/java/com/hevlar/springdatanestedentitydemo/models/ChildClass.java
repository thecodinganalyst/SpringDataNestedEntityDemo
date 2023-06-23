package com.hevlar.springdatanestedentitydemo.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChildClass {
    @Id
    @GeneratedValue
    Long id;
    String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @ToString.Exclude
    ParentClass parent;
}
