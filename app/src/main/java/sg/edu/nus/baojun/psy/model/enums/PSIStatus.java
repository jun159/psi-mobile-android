package sg.edu.nus.baojun.psy.model.enums;

/**
 * PSI status extracted from http://www.haze.gov.sg/
 */

public enum PSIStatus {
    HEALTHY_MAX(50),
    MODERATE_MIN(51),
    MODERATE_MAX(100),
    UNHEALTHY_MIN(101),
    UNHEALTHY_MAX(200),
    VERY_UNHEALTHY_MIN(201),
    VERY_UNHEALTHY_MAX(300),
    HAZARDOUS(300);

    private int value;

    PSIStatus(final int value) {
        this.value = value;
    }

    public int getValue() { return value; }
}