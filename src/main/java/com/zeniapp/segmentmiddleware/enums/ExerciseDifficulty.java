package com.zeniapp.segmentmiddleware.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ExerciseDifficulty {
    EASY("EASY"),
    MEDIUM("MEDIUM"),
    DIFFICULT("DIFFICULT"),
    EXTREME("EXTREME");
    
    @Getter
    private String exerciseDifficulty;
}
