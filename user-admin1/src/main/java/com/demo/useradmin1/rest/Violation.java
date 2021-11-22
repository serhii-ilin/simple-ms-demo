package com.demo.useradmin1.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Violation {
  private String fieldName;
  private String message;
}
