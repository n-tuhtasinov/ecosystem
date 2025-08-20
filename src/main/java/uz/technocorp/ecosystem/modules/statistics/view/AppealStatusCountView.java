package uz.technocorp.ecosystem.modules.statistics.view;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 20.08.2025
 * @since v1.0
 */
public interface AppealStatusCountView {
    String getOfficeName();
    Integer getTotal();
    Integer getInProcess();
    Integer getInAgreement();
    Integer getInApproval();
    Integer getCompleted();
    Integer getCanceled();
    Integer getRejected();
}
