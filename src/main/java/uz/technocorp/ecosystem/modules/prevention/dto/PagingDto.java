package uz.technocorp.ecosystem.modules.prevention.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagingDto<T> {

    private Integer count = 0;
    private Integer page = 0;
    private Integer size = 0;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sort;
    private List<T> items;

    public PagingDto(Integer count, Integer page, Integer size) {
        this.count = count;
        this.page = page;
        this.size = size;
        this.items = new ArrayList<>();
    }

}
