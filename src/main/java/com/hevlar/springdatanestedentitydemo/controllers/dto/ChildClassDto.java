package com.hevlar.springdatanestedentitydemo.controllers.dto;

import com.hevlar.springdatanestedentitydemo.models.ChildClass;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChildClassDto {
    @EqualsAndHashCode.Exclude
    Long id;
    String name;

    public ChildClass toChildClass(){
        return ChildClass.builder()
                .id(id)
                .name(name)
                .build();
    }

    public static ChildClassDto fromChildClass(ChildClass childClass){
        return ChildClassDto.builder()
                .id(childClass.getId())
                .name(childClass.getName())
                .build();
    }

    public static List<ChildClass> fromChildClassDtoList(List<ChildClassDto> childClassDtoList){
        return childClassDtoList.stream()
                .map(ChildClassDto::toChildClass)
                .toList();
    }
}
