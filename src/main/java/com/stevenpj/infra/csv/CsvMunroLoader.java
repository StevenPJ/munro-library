package com.stevenpj.infra.csv;

import com.opencsv.bean.BeanVerifier;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.stevenpj.domain.Munro;
import java.io.InputStreamReader;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class CsvMunroLoader {

    public List<Munro> loadFrom(Resource resource) {
        try {
            BeanVerifier<Munro> verifier = munro ->  munro.getName() != null && munro.getHeightInMeters() != null;
            return new CsvToBeanBuilder(new InputStreamReader(resource.getInputStream()))
                    .withType(Munro.class)
                    .withVerifier(verifier)
                    .build()
                    .parse();
        } catch (Exception e) {
            throw new CsvLoadingException(e);
        }
    }
}
