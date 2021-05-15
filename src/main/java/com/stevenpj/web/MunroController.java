package com.stevenpj.web;

import com.stevenpj.domain.HillCategory;
import com.stevenpj.domain.Munro;
import com.stevenpj.domain.MunroRepository;
import io.micrometer.core.instrument.util.StringUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/munros")
@RequiredArgsConstructor
public class MunroController {

    private final MunroRepository munroRepository;

    @GetMapping
    public List<Munro> find(@RequestParam(value = "hillCategory", required = false) String hillCategory) {
        if (StringUtils.isBlank(hillCategory)) {
            return munroRepository.findAll();
        }
        return munroRepository.findAll(HillCategory.valueOf(hillCategory));

    }
}
