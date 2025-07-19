package com.academix.academix.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class UserDetailedInfoDTO {
    private Long id;
    private String lrn;
    private String name;
    private String email;
    private String contactNumber;
}
