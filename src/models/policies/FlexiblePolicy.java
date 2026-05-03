package models.policies;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FlexiblePolicy implements CancellationPolicy {
    @Override
    public int calculateRefund(int totalPrice, LocalDate currentDate, LocalDate fromDate) {
        long daysLeft = ChronoUnit.DAYS.between(currentDate, fromDate);

        if (daysLeft >= 1) {
            return totalPrice;
        }
        return 0;
    }

    @Override
    public String getPolicyName() {
        return "flexible";
    }
}
