package com.xdd.init.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Data
public class BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    private transient Integer pageIndex;
    private transient Integer pageSize;
}
