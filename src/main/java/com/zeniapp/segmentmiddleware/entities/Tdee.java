package com.zeniapp.segmentmiddleware.entities;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "tdees")
public class Tdee {
    @Id
    @Getter
    @Setter
	private String id;

    @Getter
    @Setter
    @Indexed
    private String title;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String performedOn; // ISO date string

    @Getter
    @Setter
    private Boolean isArchived;
    
    @Getter
    @Setter
    private String accountId;

    @Getter
    @Setter
    private List<String> tags; // max 15 tags

    @Getter
    @Setter
    private String idealWeightFormula;
    
    @Getter
    @Setter
    private String basalMetabolismRateFormula;

    @Getter
    @Setter
    private String weightGoal;

    @Getter
    @Setter
    private TdeePersonalInfo personalInfo;

    @Getter
    @Setter
    private TdeeResult result;
    @Getter
    @Setter
    private String createdOn; // ISO date string

    @Getter
    @Setter
    private String updatedOn; // ISO date string
}
