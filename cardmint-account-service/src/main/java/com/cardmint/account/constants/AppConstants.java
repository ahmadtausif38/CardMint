package com.cardmint.account.constants;

public final class AppConstants {
    public AppConstants() {
    }

    // ===== HTTP Headers (External Contract) =====
    public static final String HEADER_CORRELATION_ID = "X-Correlation-Id";
    public static final String HEADER_EXTERNAL_REF_ID = "X-External-Ref-Id";

    // ===== MDC Keys (Internal Logging Keys) =====
    public static final String MDC_CORRELATION_ID = "correlationId";
    public static final String MDC_EXTERNAL_REF_ID = "externalRefId";
}
