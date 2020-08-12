package com.money.stocks.resource;

import com.money.stocks.domain.Reit;
import com.money.stocks.domain.enuns.TypeReitSearch;
import com.money.stocks.repository.ReitRepository;
import com.money.stocks.service.reit.ReitService;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/reits")
public class ReitController {

    private final ReitRepository reitRepository;

    private final ReitService reitService;

    public ReitController(com.money.stocks.repository.ReitRepository reitRepository, ReitService reitService) {
        this.reitRepository = reitRepository;
        this.reitService = reitService;
    }


    @GetMapping(value = "/dividendYield/{reitCod}")
    public ResponseEntity<BigDecimal> getReitDividendYield(@PathVariable String reitCod) {
        return reitRepository.findByPublicCodIgnoreCase(reitCod)
                .map(Reit::getDividendYield)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/{reitCod}")
    public ResponseEntity<Reit> getFullInfo(@PathVariable String reitCod) {
        return reitRepository.findByPublicCodIgnoreCase(reitCod)
                .or(() -> reitService.updateReit(reitCod))
                .map(reitEntity -> {
                    final Link updateLink = linkTo(
                            methodOn(ReitController.class).updateReitInfo(reitCod, TypeReitSearch.STATUS_INVEST))
                            .withRel("update_date")
                            .withType("PUT");
                    reitEntity.add(updateLink);
                    final Link findTypes = linkTo(
                            methodOn(ReitController.class).findTypesSearch())
                            .withRel("find_types")
                            .withType("GET");
                    reitEntity.add(findTypes);
                    return reitEntity;
                })
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{reitCod}")
    public ResponseEntity<String> updateReitInfo(@PathVariable String reitCod,
                                                 @RequestParam(value = "typeSearch", required = false, defaultValue = "STATUS_INVEST") TypeReitSearch typeSearch) {
        Reit reit = reitService.updateReit(reitCod, typeSearch);
        if (reit != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().body("Error when trying to update a Reit of cod: " + reitCod);
    }

    @GetMapping(value = "/typeSearch")
    public ResponseEntity<List<String>> findTypesSearch() {
        return ResponseEntity.ok(Stream.of(TypeReitSearch.values())
                .map(Enum::name)
                .collect(Collectors.toList()));
    }

    @PutMapping
    public ResponseEntity<String> updateAllReitInfo(@RequestParam(value = "typeSearch", required = false, defaultValue = "STATUS_INVEST") TypeReitSearch typeSearch) {
        reitService.updateAllReit(typeSearch);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<String> getAllReitsIds() {
        return ResponseEntity.ok(reitRepository.findAll()
                .stream()
                .map(Reit::getPublicCod)
                .collect(Collectors.joining(",")));
    }
}
