package com.salesianostriana.dam.TrailQuest_Api.specification;

import com.salesianostriana.dam.TrailQuest_Api.dto.RouteFilterDTO;
import com.salesianostriana.dam.TrailQuest_Api.model.PosiblesRegiones;
import com.salesianostriana.dam.TrailQuest_Api.model.Route;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class RouteSpecification {

    public static Specification<Route> filterBy(RouteFilterDTO filterDTO) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterDTO.title() != null && !filterDTO.title().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + filterDTO.title().toLowerCase() + "%"
                ));
            }

            if (filterDTO.regions() != null && !filterDTO.regions().isEmpty()) {
                List<PosiblesRegiones> regionEnums = new ArrayList<>();
                for (String region : filterDTO.regions()) {
                    regionEnums.add(PosiblesRegiones.valueOf(region));
                }
                predicates.add(root.get("region").in(regionEnums));
            }

            if (filterDTO.difficulties() != null && !filterDTO.difficulties().isEmpty()) {
                predicates.add(root.get("difficulty").in(filterDTO.difficulties()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}