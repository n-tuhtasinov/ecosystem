package uz.technocorp.ecosystem.modules.riskanalysisinterval;

import java.util.List;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 25.04.2025
 * @since v1.0
 */
public interface RiskAnalysisIntervalService {

    List<RiskAnalysisInterval> findAll(Integer year);
    RiskAnalysisInterval findByStatus();
}
