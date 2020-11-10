package by.epamtc.protsko.rentcar.entity.user;

public enum Role {
    CLIENT(1),
    ADMIN(2);

    Role(int order) {
        this.order = order;
    }

    private int order;

    public static Role getByOrderCode(int code) {
        for (Role t : values()) {
            if (t.order == code) {
                return t;
            }
        }
        throw new IllegalArgumentException("No such element for code: " + code);
    }
}
