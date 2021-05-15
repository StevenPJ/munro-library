package com.stevenpj.domain;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Munro {

    @CsvBindByName(column = "Name")
    private String name;

    @CsvBindByName(column = "Height (m)")
    private Integer heightInMeters;

    @CsvBindByName(column = "Post 1997")
    private String hillCategory;

    @CsvBindByName(column = "Grid Ref")
    private String gridReference;
}
