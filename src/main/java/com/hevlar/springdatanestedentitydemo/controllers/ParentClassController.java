package com.hevlar.springdatanestedentitydemo.controllers;

import com.hevlar.springdatanestedentitydemo.controllers.dto.ParentClassDto;
import com.hevlar.springdatanestedentitydemo.models.ChildClass;
import com.hevlar.springdatanestedentitydemo.models.ParentClass;
import com.hevlar.springdatanestedentitydemo.repository.ParentClassRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ParentClassController {
    ParentClassRepository parentClassRepository;

    public ParentClassController(ParentClassRepository parentClassRepository){
        this.parentClassRepository = parentClassRepository;
    }

    @GetMapping
    public List<ParentClassDto> list(){
        return parentClassRepository.findAll()
                .stream().
                map(ParentClassDto::fromParentClass)
                .toList();
    }

    @GetMapping("/{id}")
    public ParentClassDto get(@PathVariable("id") String id){
        ParentClass parentClass = parentClassRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ParentClassDto.fromParentClass(parentClass);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParentClassDto create(@RequestBody ParentClassDto parentClassDto){
        ParentClass parentClass = parentClassRepository.save(parentClassDto.toParentClass());
        return ParentClassDto.fromParentClass(parentClass);
    }

    @PutMapping("/{id}")
    public ParentClassDto update(@PathVariable("id") String id, @RequestBody ParentClassDto parentClassDto){
        ParentClass parentClass = parentClassDto.toParentClass();

        ParentClass retrievedParentClass = parentClassRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        retrievedParentClass.setName(parentClassDto.getName());

        retrievedParentClass.getChildren().clear();
        List<ChildClass> updatedChildren = new ArrayList<>(parentClass.getChildren());
        updatedChildren.forEach(childClass -> childClass.setParent(retrievedParentClass));
        retrievedParentClass.getChildren().addAll(updatedChildren);

        ParentClass updatedParentClass = parentClassRepository.save(retrievedParentClass);
        return ParentClassDto.fromParentClass(updatedParentClass);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id){
        parentClassRepository.deleteById(Long.valueOf(id));
    }
}
