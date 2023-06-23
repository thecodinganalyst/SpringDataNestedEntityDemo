package com.hevlar.springdatanestedentitydemo.repository;

import com.hevlar.springdatanestedentitydemo.models.Father;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FatherRepository extends JpaRepository<Father, Long> {
}
