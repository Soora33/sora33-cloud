package com.sora.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sora.domain.user.Employee;
import org.springframework.stereotype.Repository;

/**
 * @Classname UserMapper
 * @Description
 * @Date 2023/07/01 14:38
 * @Author by Sora33
 */
@Repository
public interface UserMapper extends BaseMapper<Employee> {
}
