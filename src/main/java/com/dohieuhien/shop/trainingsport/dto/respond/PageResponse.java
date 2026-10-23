package com.dohieuhien.shop.trainingsport.dto.respond;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponse<T>(List<T> content, long totalElements, long totalPages, int page, int size) {
    public static <T> PageResponse<T> of (Page<T> p){
        return new PageResponse<>(p.getContent(), p.getTotalElements(), p.getTotalPages(), p.getNumber(), p.getSize());
    }
}
