package com.tiansu.energy.ltc.module.employee;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiansu.energy.ltc.module.employee.domain.dto.EmployeeQueryDTO;
import com.tiansu.energy.ltc.module.employee.domain.vo.EmployeeVO;
import com.tiansu.energy.ltc.util.PageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 员工管理
 *
 * @author ddd
 * @date 2021年01月21日上午11:54:52
 */
@Service
public class EmployeeService    {

    @Resource
    private EmployeeDao employeeDao;
    /**
     * 查询员工列表
     *
     * @param queryDTO
     * @return
     */
    public EmployeeVO selectEmployeeList(EmployeeQueryDTO queryDTO) {
        Page pageParam = PageUtil.convert2QueryPage(queryDTO);
       // queryDTO.setIsDelete(JudgeEnum.NO.getValue());
        //List<EmployeeDTO> employeeList = employeeDao.selectEmployeeList(pageParam, queryDTO);
        EmployeeVO employeeVO=new EmployeeVO();
        employeeVO.setId(1L);
        employeeVO.setLoginName("ldd");

        employeeVO.setEmail("170175080@qq.com");

        return employeeVO;
       // return PageUtil.convert2PageResult(pageParam, employeeList, EmployeeVO.class);
    }
}
