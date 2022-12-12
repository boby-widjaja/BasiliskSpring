package com.basiliskSB.service.abstraction;

import org.springframework.data.domain.Page;

public interface CrudDetailService {
    public Object getHeader (Object headerId);
    public <T> Page<Object> getGridDetail(Object headerId, Integer page, T filter);
    public Object getUpdateDetail(Object id);
    public <T> Object saveDetail(Object dto);
    public Boolean deleteDetail(Object id);
}