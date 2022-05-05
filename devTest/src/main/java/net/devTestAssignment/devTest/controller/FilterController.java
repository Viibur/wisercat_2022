package net.devTestAssignment.devTest.controller;

import net.devTestAssignment.devTest.entity.Criteria;
import net.devTestAssignment.devTest.entity.Filter;
import net.devTestAssignment.devTest.repository.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:8081/")
public class FilterController {

    @Autowired
    private FilterRepository filterRepository;

    @GetMapping("/filters")
    public List<Filter> fetchFilters(){
        return filterRepository.findAll();
    }

    @PostMapping("/filters")
    public List<Filter> postFilter(@RequestBody Filter filter){
        List<Criteria> criteriaList = filter.getCriteria();
        for (Criteria criteria:criteriaList) {
            criteria.setFilter(filter);
        }
        filterRepository.save(filter);
        return fetchFilters();
    }

}
