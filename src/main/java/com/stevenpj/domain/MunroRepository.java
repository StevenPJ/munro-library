package com.stevenpj.domain;

import java.util.List;

public interface MunroRepository {

    List<Munro> findAll();

    void save(Munro munro);
}
