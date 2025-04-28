package uz.technocorp.ecosystem.modules.riskassessment.enums;

import lombok.Getter;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 14.04.2025
 * @since v1.0
 */

@Getter
public enum RiskAssessmentIndicator {

    PARAGRAPH_HF_1(13),
    PARAGRAPH_HF_2(2),
    PARAGRAPH_HF_3(14),
    PARAGRAPH_HF_4(6),
    PARAGRAPH_HF_5(10),
    PARAGRAPH_HF_6(12),
    PARAGRAPH_HF_7(1),
    PARAGRAPH_HF_8(6),
    PARAGRAPH_HF_9(15),
    PARAGRAPH_HF_10(15),
    PARAGRAPH_HF_11(6),
    PARAGRAPH_IRS_1(15),
    PARAGRAPH_IRS_2(10),
    PARAGRAPH_IRS_3(5),
    PARAGRAPH_IRS_4(5),
    PARAGRAPH_IRS_5(5),
    PARAGRAPH_IRS_6(5),
    PARAGRAPH_IRS_7(5),
    PARAGRAPH_IRS_8(5),
    PARAGRAPH_IRS_9(10),
    PARAGRAPH_IRS_10(5),
    PARAGRAPH_IRS_11(5),
    PARAGRAPH_IRS_12(5),
    PARAGRAPH_IRS_13(5),
    PARAGRAPH_IRS_14(5),
    PARAGRAPH_IRS_15(10),
    PARAGRAPH_ATTRACTION_1(15),
    PARAGRAPH_ATTRACTION_2(15),
    PARAGRAPH_ATTRACTION_3(15),
    PARAGRAPH_ATTRACTION_4(15),
    PARAGRAPH_ATTRACTION_5(15),
    PARAGRAPH_ATTRACTION_6(15),
    PARAGRAPH_ATTRACTION_7(10),
    PARAGRAPH_ELEVATOR_1(15),
    PARAGRAPH_ELEVATOR_2(15),
    PARAGRAPH_ELEVATOR_3(15),
    PARAGRAPH_ELEVATOR_4(15),
    PARAGRAPH_ELEVATOR_5(15),
    PARAGRAPH_ELEVATOR_6(15),
    PARAGRAPH_ELEVATOR_7(10);

    public final Integer score;

    RiskAssessmentIndicator(Integer score) {
        this.score = score;
    }

}
