package uz.technocorp.ecosystem.modules.hf.view;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 19.05.2025
 * @since v1.0
 */
public interface HfPageView extends HfSelectView{

    String getRegionName();
    String getDistrictName();
    String getAddress();
    String getTypeName();
    String getEmail();
    String getLegalName();
    Long getLegalTin();
}
