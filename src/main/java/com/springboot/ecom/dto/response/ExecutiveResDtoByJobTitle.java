package com.springboot.ecom.dto.response;

import com.springboot.ecom.enums.JobTitle;

public record ExecutiveResDtoByJobTitle(

        long id,
          String name,
          JobTitle jobTitle
) {
}
