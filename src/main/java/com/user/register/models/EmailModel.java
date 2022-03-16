package com.user.register.models;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
public class EmailModel {
    private String emailFrom;
    private String emailTo;
    private String subject;
    private String text;
}
