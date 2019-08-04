package ua.alxmute.data.access.domain.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum PaymentStatus {
    SUCCESSFUL,
    IN_PROGRESS,
    FAILED;

    private static final List<PaymentStatus> VALUES = Arrays.asList(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static PaymentStatus randomStatus() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
