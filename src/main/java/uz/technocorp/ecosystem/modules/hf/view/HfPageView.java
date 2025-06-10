package uz.technocorp.ecosystem.modules.hf.view;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 19.05.2025
 * @since v1.0
 */
public interface HfPageView extends HfSelectView{

    String getInspectorName();
    String getRegistryNumber();
    String getAddress();
    String getName();
    String getLegalName();
    Long getLegalTin();
    UUID getId();
}
