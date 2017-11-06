package ch.overney.springBootWatering.util;

public class User {
    private final long companyId;

    public User(long companyId) {
        this.companyId = companyId;
    }

    public long getCompanyId () {
        return companyId;
    }
}
