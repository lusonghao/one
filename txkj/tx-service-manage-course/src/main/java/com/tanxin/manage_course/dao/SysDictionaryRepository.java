package com.tanxin.manage_course.dao;

import com.tanxin.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by Administrator.
 */
@Transactional
public interface SysDictionaryRepository extends MongoRepository<SysDictionary,String> {

    SysDictionary findByDType(String dtype);

}
