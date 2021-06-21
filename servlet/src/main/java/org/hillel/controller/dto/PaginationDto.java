package org.hillel.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationDto {

    private int numberPage = 0;
    private int sizePage = 10;
    private long totalElements;
    private int totalPages;
    private String fieldSort;
}
