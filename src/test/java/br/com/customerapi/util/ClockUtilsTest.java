package br.com.customerapi.util;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
class ClockUtilsTest {

    @Test
    void getTimestampNow() {
        System.out.println("Time now: " + ClockUtils.getTimestampNow());
        assert true;
    }

    @Test
    void getTimestampFromStringDate() {
        System.out.println("Time now: " + ClockUtils.getTimestampFromStringDate("1989-02-22"));
        assert true;
    }

    @Test
    void getAge() {
        System.out.println("Age: " + ClockUtils.getAge(ClockUtils.getTimestampFromStringDate("1989-02-22")));
        assert true;
    }
}