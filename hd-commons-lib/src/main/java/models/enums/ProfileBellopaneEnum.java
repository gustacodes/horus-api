package models.enums;

import java.util.Arrays;

public enum ProfileBellopaneEnum {
    ROLE_MANAGER("ROLE_MANAGER"),
    ROLE_OWNER("ROLE_OWNER"),
    ROLE_CASHIER("ROLE_CASHIER"),
    ROLE_BAKER("ROLE_BAKER"),
    ROLE_ATTENDANT("ROLE_ATTENDANT"),
    ROLE_GENERAL_SERVICES("ROLE_GENERAL_SERVICES");

    private final String description;

    ProfileBellopaneEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static ProfileEnum toEnum(final String description) {
        return Arrays.stream(ProfileEnum.values())
                .filter(profileEnum -> profileEnum.getDescription().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid description: " + description));
    }
}
