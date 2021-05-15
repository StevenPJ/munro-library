package com.stevenpj.infra;

import com.stevenpj.domain.HillCategory;
import com.stevenpj.domain.Munro;
import com.stevenpj.domain.MunroRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<Munro> findAll(HillCategory hillCategory) {
        return findAll().stream()
                .filter(hillCategory::matches)
                .collect(Collectors.toList());
    }
}
