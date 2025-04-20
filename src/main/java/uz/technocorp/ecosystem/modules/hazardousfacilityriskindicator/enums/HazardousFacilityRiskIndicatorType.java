package uz.technocorp.ecosystem.modules.hazardousfacilityriskindicator.enums;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 14.04.2025
 * @since v1.0
 */

public enum HazardousFacilityRiskIndicatorType {

    PARAGRAPH_1(13),
    PARAGRAPH_2(2),
    PARAGRAPH_3(14),
    PARAGRAPH_4(6),
    PARAGRAPH_5(10),
    PARAGRAPH_6(12),
    PARAGRAPH_7(1),
    PARAGRAPH_8(6),
    PARAGRAPH_9(15),
    PARAGRAPH_10(15),
    PARAGRAPH_11(6);

    public final Integer score;
    HazardousFacilityRiskIndicatorType(Integer score) {
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }
}
