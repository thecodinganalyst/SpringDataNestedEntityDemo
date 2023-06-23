package com.hevlar.springdatanestedentitydemo.repository;

import com.hevlar.springdatanestedentitydemo.models.ParentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentClassRepository extends JpaRepository<ParentClass, Long> {
}
