package com.demo.useradmin1.rest;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ValidationErrorResponse {
  private List<Violation> violations = new ArrayList<>();
}
