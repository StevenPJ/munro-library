package com.stevenpj.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Munro {

    private String name;
    private int heightInMeters;
    private String hillCategory;
    private String gridReference;
}
