package com.zeniapp.segmentmiddleware.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import com.zeniapp.segmentmiddleware.enums.FoodCategory;
import com.zeniapp.segmentmiddleware.enums.Mineral;
import com.zeniapp.segmentmiddleware.enums.Vitamin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "foods")
public class Food {
    @Id
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    @Indexed(unique = true)
    private String name;

    @Getter
    @Setter
    private FoodCategory category;

    @Getter
    @Setter
    private Integer energy; // expressed in kcal/referencePortion

    @Getter
    @Setter
    private Integer referencePortion; // expressed in grams

    @Getter
    @Setter
    private Integer proteins; // expressed in grams/referencePortion

    @Getter
    @Setter
    private Integer simplexCarbs; // expressed in grams/referencePortion

    @Getter
    @Setter
    private Integer complexCarbs; // expressed in grams/referencePortion

    @Getter
    @Setter
    private Integer saturatedFats; // expressed in grams/referencePortion

    @Getter
    @Setter
    private Integer unsaturatedFats; // expressed in grams/referencePortion

    @Getter
    @Setter
    private Integer fibers; // expressed in grams/referencePortion

    @Getter
    @Setter
    private Integer cholesterol; // expressed in milligrams/referencePortion

    @Getter
    @Setter
    private List<Vitamin> vitamins; // top 5 vitamins most present

    @Getter
    @Setter
    private List<Mineral> minerals; // top 5 minerals most present
    
    @Getter
    @Setter
    private Boolean isPublished;

    @Getter
    @Setter
    private Boolean isDeleted;
}