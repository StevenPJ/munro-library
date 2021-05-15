package com.stevenpj.config;

import com.stevenpj.domain.MunroRepository;
import com.stevenpj.infra.csv.CsvMunroLoader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class MunroDataInitializer implements InitializingBean {

    private final CsvMunroLoader csvMunroLoader;
    private final MunroRepository munroRepository;
    private final Resource munroCsv;

    @Autowired
    public MunroDataInitializer(
            CsvMunroLoader csvMunroLoader,
            MunroRepository munroRepository,
            @Value("${munro_data}") String csvPath
    ) {
        this.csvMunroLoader = csvMunroLoader;
        this.munroRepository = munroRepository;
        this.munroCsv = new ClassPathResource(csvPath);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        csvMunroLoader.loadFrom(this.munroCsv)
                .forEach(munroRepository::save);
    }
}