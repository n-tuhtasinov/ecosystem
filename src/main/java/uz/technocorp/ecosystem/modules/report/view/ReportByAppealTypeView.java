package uz.technocorp.ecosystem.modules.report.view;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 20.08.2025
 * @since v1.0
 */
public interface ReportByAppealTypeView {
    @Value("#{@reportServiceImpl.getLabelByAppealType(target.appealType)}")
    String getAppealType();
    Integer getTotal();
    Integer getCommittee();
    Integer getKarakalpakstan();
    Integer getAndijan();
    Integer getBukhara();
    Integer getFergana();
    Integer getJizzakh();
    Integer getKashkadarya();
    Integer getKhorezm();
    Integer getNamangan();
    Integer getNavoi();
    Integer getSamarkand();
    Integer getSyrdarya();
    Integer getSurkhandarya();
    Integer getTashkent();
    Integer getTashkentRegion();
}
