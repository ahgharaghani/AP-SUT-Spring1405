package models.policies;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ModeratePolicy implements CancellationPolicy {
    @Override
    public int calculateRefund(int totalPrice, LocalDate currentDate, LocalDate fromDate) {
        long daysLeft = ChronoUnit.DAYS.between(currentDate, fromDate);

        if (daysLeft >= 3) {
            return totalPrice;
        } else if (daysLeft >= 1) {
            return (int) (totalPrice * 0.7);
        }
        return 0;
    }

    @Override
    public String getPolicyName() {
        return "moderate";
    }
}