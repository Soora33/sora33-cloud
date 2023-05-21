package com.sora.system.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 7973568515260228373L;
    private Integer id;
    private String name;
    private String age;
    private String hobby;
    private String test;
    private Date time;

}
