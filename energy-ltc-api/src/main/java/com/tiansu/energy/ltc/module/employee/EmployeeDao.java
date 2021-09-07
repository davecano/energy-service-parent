package com.tiansu.energy.ltc.module.employee;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiansu.energy.ltc.module.employee.domain.dto.EmployeeDTO;
import com.tiansu.energy.ltc.module.employee.domain.dto.EmployeeQueryDTO;
import com.tiansu.energy.ltc.module.employee.domain.entity.EmployeeEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeDao extends BaseMapper<EmployeeEntity> {
    /**
     * 查询员工列表
     *
     * @param page
     * @param queryDTO
     * @return
     */
    List<EmployeeDTO> selectEmployeeList(Page page, @Param("queryDTO") EmployeeQueryDTO queryDTO);
}
