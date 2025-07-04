package uz.technocorp.ecosystem.modules.accreditation.view;

import uz.technocorp.ecosystem.modules.accreditation.enums.AccreditationSphere;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 02.07.2025
 * @since v1.0
 */
public interface ExpConclusionPageView {

    UUID getId();

    String getCustomerLegalAddress();

    String getCustomerLegalName();

    String getCustomerFullName();

    String getCustomerPhoneNumber();

    Long getCustomerTin();

    List<AccreditationSphere> getSpheres();

    String getExpertOrganizationName();

    LocalDate getExpertiseConclusionDate();

    String getExpertiseConclusionNumber();

    String getExpertiseObjectName();
}
