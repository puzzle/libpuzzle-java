package ch.puzzle.libpuzzle.springframework.boot.rest.dto;

import java.util.Collections;
import java.util.List;

public class PageDto<TDto> {


    private int pageNumber;

    private int pageSize;

    private List<TDto> items;

    public PageDto(int pageNumber, int pageSize, List<TDto> items) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.items = items;
    }

    public static <TDto> PageDto<TDto> empty() {
        return new PageDto<>(0, 0, Collections.emptyList());
    }

}
