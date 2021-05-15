package com.stevenpj.infra;

import com.stevenpj.domain.Munro;
import com.stevenpj.domain.MunroRepository;
import java.util.ArrayList;
import java.util.List;

public class InMemoryMunroRepository implements MunroRepository {

    private final List<Munro> munros = new ArrayList<>();

    @Override
    public List<Munro> findAll() {
        return munros;
    }

    @Override
    public void save(Munro munro) {
        munros.add(munro);
    }
}
