package uz.technocorp.ecosystem.modules.statistics.view;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 20.08.2025
 * @since v1.0
 */
public interface StatByAppealTypeView {
    @Value("#{@statisticsServiceImpl.getLabelByAppealType(target.appealType)}")
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
