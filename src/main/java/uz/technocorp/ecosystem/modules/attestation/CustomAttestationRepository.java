package uz.technocorp.ecosystem.modules.attestation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface CustomAttestationRepository {
    Page<UUID> findDistinctAppealIds(Specification<Attestation> spec, Pageable pageable);
}