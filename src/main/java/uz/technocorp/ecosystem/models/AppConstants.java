package uz.technocorp.ecosystem.models;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 */
public interface AppConstants {
    String DEFAULT_PAGE_NUMBER="1";
    String DEFAULT_PAGE_SIZE="10";

    String ACCESS_TOKEN = "access_token";
    String REFRESH_TOKEN = "refresh_token";

    // TODO Domain name e-imzo production sertifikatga mos bo'lishi kerak
    String HOST = "technocorp.uz";

//    Pageable pageable= PageRequest.of(
//            Integer.parseInt(params.getOrDefault("page", AppConstants.DEFAULT_PAGE_NUMBER))-1,
//            Integer.parseInt(params.getOrDefault("size", AppConstants.DEFAULT_PAGE_SIZE)),
//            Sort.Direction.DESC,
//            "soato");
}
