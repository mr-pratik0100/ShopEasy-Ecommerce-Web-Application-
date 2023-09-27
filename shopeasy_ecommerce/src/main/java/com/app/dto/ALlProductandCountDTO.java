package com.app.dto;

import java.util.List;

import com.app.collections.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ALlProductandCountDTO {
private List<Product> product;
private int productsCount;
}
