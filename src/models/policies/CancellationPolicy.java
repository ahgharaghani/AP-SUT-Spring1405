package models.policies;

import java.time.LocalDate;

public interface CancellationPolicy {
    int calculateRefund(int totalPrice, LocalDate currentDate, LocalDate fromDate);
    String getPolicyName();
}
