package uz.technocorp.ecosystem.modules.hf.enums;

import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 08.04.2025
 * @since v1.0
 */
public enum HFSphere {
    OIL("Neft"),
    GAS("Gaz"),
    BOILER("Issiq suv"),
    CHEMISTRY("Himiya"),
    COAL("Ko'mir"),
    GEOLOGY("Geologiya"),
    GRAIN("Don"),
    RAILWAY("Temir yo'l"),
    AGTKSH("AGTKSH"),
    OKMK("OKMK"),
    AGNKS("AGNKS"),
    NKMK_AJ("NKMK AJ"),
    D_YA("D/ya");


    public final String label;
    private static final Map<String, HFSphere> BY_LABEL = new HashMap<>();

    HFSphere(String label) {
        this.label = label;
    }

    static {
        for (HFSphere sphere : HFSphere.values()) {
            BY_LABEL.put(sphere.label, sphere);
        }
    }

    public static HFSphere getEnumByLabel(String label){
        if (!BY_LABEL.containsKey(label)) throw new ResourceNotFoundException("Xicho tarmog'i", "nom", label);
        return BY_LABEL.get(label);
    }

}
