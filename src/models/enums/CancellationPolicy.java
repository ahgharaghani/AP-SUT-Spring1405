package models.enums;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public enum CancellationPolicy {
    FLEXIBLE {
        @Override
        public int calculateRefund(int totalPrice, LocalDate currentDate, LocalDate fromDate) {
            long daysLeft = ChronoUnit.DAYS.between(currentDate, fromDate);
            return daysLeft >= 1 ? totalPrice : 0;
        }

        @Override
        public String getPolicyName() {
            return "flexible";
        }
    },

    MODERATE {
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
    },

    STRICT {
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
    };

    public abstract int calculateRefund(int totalPrice, LocalDate currentDate, LocalDate fromDate);
    public abstract String getPolicyName();
}
