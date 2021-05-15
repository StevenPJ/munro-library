package com.stevenpj.infra;

import com.stevenpj.domain.Munro;
import com.stevenpj.domain.MunroCriteria;
import com.stevenpj.domain.MunroRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryMunroRepository implements MunroRepository {

    private final List<Munro> munros = new ArrayList<>();

    @Override
    public void save(Munro munro) {
        munros.add(munro);
    }

    @Override
    public List<Munro> findAll(MunroCriteria criteria) {
        Stream<Munro> munros = this.munros.stream().filter(criteria::matches);

        if (criteria.getSort() != null) {
            munros = munros.sorted(criteria::sort);
        }

        if (criteria.getLimit() != null) {
            munros = munros.limit(criteria.getLimit());
        }

        return munros.collect(Collectors.toList());
    }
}
