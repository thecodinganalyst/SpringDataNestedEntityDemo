package com.hevlar.springdatanestedentitydemo.controllers.dto;

import com.hevlar.springdatanestedentitydemo.models.ParentClass;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParentClassDto {
    @EqualsAndHashCode.Exclude
    Long id;
    String name;
    List<ChildClassDto> children;

    public ParentClass toParentClass(){
        ParentClass parentClass = ParentClass.builder()
                .id(id)
                .name(name)
                .children(ChildClassDto.fromChildClassDtoList(children))
                .build();
        parentClass.getChildren().forEach(child -> child.setParent(parentClass));
        return parentClass;
    }

    public static ParentClassDto fromParentClass(ParentClass parentClass){
        return ParentClassDto.builder()
                .id(parentClass.getId())
                .name(parentClass.getName())
                .children(parentClass.getChildren().stream().map(ChildClassDto::fromChildClass).toList())
                .build();
    }
}
