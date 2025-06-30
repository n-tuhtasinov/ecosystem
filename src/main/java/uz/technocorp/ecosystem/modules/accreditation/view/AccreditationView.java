package uz.technocorp.ecosystem.modules.accreditation.view;

import uz.technocorp.ecosystem.modules.accreditation.enums.AccreditationSphere;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 30.06.2025
 * @since v1.0
 */
public interface AccreditationView {

    UUID getId();
    String getLegalAddress();
    String getLegalName();
    String getFullName();
    String getPhoneNumber();
    List<AccreditationSphere> getSpheres();
    LocalDate getCertificateDate();
    String getCertificateNumber();
    LocalDate getCertificateValidityDate();
}
