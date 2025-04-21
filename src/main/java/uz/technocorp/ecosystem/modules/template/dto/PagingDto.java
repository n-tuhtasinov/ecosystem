package uz.technocorp.ecosystem.modules.template.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 15.04.2025
 * @since v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagingDto<T> {
    private Integer count = 0;
    private Integer page = 0;
    private Integer limit = 0;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sort;

    private List<T> items;

    public PagingDto(Integer count, Integer page, Integer limit) {
        this.count = count;
        this.page = page;
        this.limit = limit;
        this.items = new ArrayList<>();
    }

    public PagingDto(Integer count, Integer page, Integer limit, String name) {
        this.count = count;
        this.page = page;
        this.limit = limit;
        this.name = name;
        this.items = new ArrayList<>();
    }
}
