package com.daitobedai.nocode.dao;

import com.daitobedai.nocode.domain.badUrl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SomethingBad {
    List<badUrl> getUrl(@Param("text") String text);
}
