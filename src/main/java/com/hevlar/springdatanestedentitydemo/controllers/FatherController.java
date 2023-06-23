package com.hevlar.springdatanestedentitydemo.controllers;

import com.hevlar.springdatanestedentitydemo.models.Father;
import com.hevlar.springdatanestedentitydemo.models.Son;
import com.hevlar.springdatanestedentitydemo.repository.FatherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/fathers")
public class FatherController {
    FatherRepository repository;
    public FatherController(FatherRepository repository){
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Father create(@RequestBody Father father){
        return this.repository.save(father);
    }

    @GetMapping("/{id}")
    public Father get(@PathVariable("id") Long id){
        return this.repository.findById(id).orElseThrow();
    }

    @PutMapping("/{id}")
    public Father update(@PathVariable("id") Long id, @RequestBody Father father){
        Father retrievedFather = this.repository.findById(id).orElseThrow();
        retrievedFather.setName(father.getName());

        List<Son> sonListUpdates = new ArrayList<>(father.getSonList());
        sonListUpdates.forEach(son -> son.setFather(retrievedFather));

        retrievedFather.setSonList(sonListUpdates);
        return this.repository.save(retrievedFather);
    }
}
