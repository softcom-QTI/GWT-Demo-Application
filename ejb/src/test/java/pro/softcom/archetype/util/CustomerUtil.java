package pro.softcom.archetype.util;

import java.util.Random;

import pro.softcom.archetype.entity.Customer;

public final class CustomerUtil {

    public static final String[] LASTNAMES = { "Carter", "Lincoln", "Obama", "Kennedy", "Roosevelt", "Nixon" };
    public static final String[] FIRSTNAMES = { "James", "Abraham", "Barrack", "John F.", "Theodore", "Richard" };

    private final static Random random = new Random();

    public static Customer getCustomer() {
        Customer customer = new Customer();

        customer.setFirstname(getRandomValue(FIRSTNAMES));
        customer.setLastname(getRandomValue(LASTNAMES));

        return customer;
    }

    private static String getRandomValue(String[] values) {
        return values[random.nextInt(values.length)];
    }
}
