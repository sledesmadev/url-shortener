package com.neueda.urlshortener.dto;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullUrlDTO {

	private String url;
	private String alias;
}
