package com.bc.bookcrossing.src.requestManager;

/**
 *
 * Enum utilizzato per caratterizzare la tipologia della richiesta che si sta eseguendo verso il server.
 *
 * @author Paganessi Andrea - Piffari Michele - Villa Stefano
 * @version 1.0
 * @since 2018/2019
 */

public enum RequestType {
    // ===================== ITERAZIONE 1 ===================================
    BOOK_REGISTRATION_MANUAL("0"),
    BOOK_SEARCH("8"),

    // ===================== ITERAZIONE 2 ===================================
    BOOK_RESERVATION("1"),
    LOGIN("2"),
    BOOK_REGISTRATION_AUTOMATIC("4"),

    // ===================== PROSSIMA ITERAZIONE ===================================
    SIGN_IN("3"),
    PROFILE_INFO("5"),
    TAKEN_BOOKS("6"),
    PICK_UP("7");


    public String description;

    RequestType(String desc) {
        this.description = desc;
    }

    public static RequestType getEnumReqType (String desc) {
        for (RequestType r : RequestType.values()) {
            if (r.description.equalsIgnoreCase(desc)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
