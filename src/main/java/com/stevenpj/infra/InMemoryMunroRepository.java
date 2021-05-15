package com.stevenpj.infra;

import com.stevenpj.domain.Munro;
import com.stevenpj.domain.MunroCriteria;
import com.stevenpj.domain.MunroRepository;
import io.micrometer.core.instrument.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
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
    public List<Munro> findAll(MunroCriteria criteria) {
        return findAll().stream()
                .filter(munro -> StringUtils.isNotBlank(munro.getHillCategory()))
                .filter(criteria::matches)
                .collect(Collectors.toList());
    }
}
