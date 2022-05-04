package net.devTestAssignment.devTest.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "filters")
public class Filter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name",nullable = false)
    private String name;

    @OneToMany(
            mappedBy = "filter",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Criteria> criteria = new ArrayList<>();


    public Filter(String name) {
        this.name = name;
    }
}
