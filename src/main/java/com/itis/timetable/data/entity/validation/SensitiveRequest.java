package com.itis.timetable.data.entity.validation;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

public class SensitiveRequest {
    @NotNull
    @Length(max = 20)
    private String userName;
    @NotNull
    @Pattern(regexp = "[A-Z][a-z][0-9]")
    private String password;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date timestamp;
    @Min(18)
    private Integer age;
}
