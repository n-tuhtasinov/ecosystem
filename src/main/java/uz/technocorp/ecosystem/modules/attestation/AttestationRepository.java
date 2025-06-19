package uz.technocorp.ecosystem.modules.attestation;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Suxrob
 * @version 1.0
 * @created 18.06.2025
 * @since v1.0
 */
@Repository
public interface AttestationRepository extends JpaRepository<Attestation, UUID>, JpaSpecificationExecutor<Attestation> {


}
