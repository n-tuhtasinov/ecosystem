package uz.technocorp.ecosystem.shared;

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

//    Pageable pageable= PageRequest.of(
//            Integer.parseInt(params.getOrDefault("page", AppConstants.DEFAULT_PAGE_NUMBER))-1,
//            Integer.parseInt(params.getOrDefault("size", AppConstants.DEFAULT_PAGE_SIZE)),
//            Sort.Direction.DESC,
//            "soato");
}
