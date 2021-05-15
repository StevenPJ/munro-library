package com.stevenpj.domain;

import java.util.List;

public interface MunroRepository {

    void save(Munro munro);

    List<Munro> findAll(MunroCriteria criteria);
}
