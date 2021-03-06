package com.bc.bookcrossing.src.ClientModels.Enums;

import javax.annotation.Nullable;

/**
 * @author Paganessi Andrea - Piffari Michele - Villa Stefano
 * @version 1.0
 * @since 2018/2019
 */

public enum BookType {
    ACTION("ACTION"),
    ADVENTURE("ADVENTURE"),
    THRILLER("THRILLER"),
    FANTASY("FANTASY"),
    HORROR("HORROR"),
    FAIRYTALE("FAIRYTALE"),
    OTHER("OTHER");
	
    public String description;

    BookType(String desc) {
        this.description = desc;
    }

    public String getSigla() {
        return description;
    }

    @Nullable
    public static BookType fromString(String text) {
        for (BookType b : BookType.values()) {
            if (b.description.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return BookType.OTHER;
    }
}
