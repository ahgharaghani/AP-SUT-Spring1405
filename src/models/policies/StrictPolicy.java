package models.policies;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class StrictPolicy implements CancellationPolicy {
    @Override
    public int calculateRefund(int totalPrice, LocalDate currentDate, LocalDate fromDate) {
        long daysLeft = ChronoUnit.DAYS.between(currentDate, fromDate);

        if (daysLeft >= 7) {
            return (int) (totalPrice * 0.8);
        } else if (daysLeft >= 1) {
            return (int) (totalPrice * 0.3);
        }
        return 0;
    }

    @Override
    public String getPolicyName() {
        return "strict";
    }
}