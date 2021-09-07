package com.tiansu.energy.ltc.module.employee;

import com.tiansu.energy.ltc.common.anno.ResponseResult;
import com.tiansu.energy.ltc.common.domain.ResponseDTO;
import com.tiansu.energy.ltc.module.employee.domain.dto.EmployeeQueryDTO;
import com.tiansu.energy.ltc.module.employee.domain.vo.EmployeeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = {"用户管理"})
//@OperateLog
public class EmployeeController {


    @Resource
    private EmployeeService employeeService;

    @PostMapping("/employee/query")
    @Cacheable(cacheNames = {"EmployeeVO","EmployeeVO"},key = "#query.phone")
    @ApiOperation(value = "员工管理查询", notes = "员工管理查询 @author ldd")
    @ResponseResult
    public EmployeeVO query(@RequestBody EmployeeQueryDTO query) {

        return employeeService.selectEmployeeList(query);
    }



    @PostMapping("/employee/query1")
    @ApiOperation(value = "员工管理查询1", notes = "员工管理查询 @author ldd")
    public ResponseDTO<EmployeeVO> query1(@RequestBody EmployeeQueryDTO query) {

        EmployeeVO result= employeeService.selectEmployeeList(query);

        return ResponseDTO.success(result);
    }

  /*  @GetMapping("/employee/get/all")
    @ApiOperation(value = "查询所有员工基本信息，用于选择框", notes = "查询所有员工基本信息，用于选择框")
    @NoValidPrivilege
    public ResponseDTO<List<EmployeeVO>> getAll() {
        return ResponseDTO.succData(employeeService.getAllEmployee());
    }

    @ApiOperation(value = "添加员工", notes = "@author yandanyang")
    @PostMapping("/employee/add")
    public ResponseDTO<String> addEmployee(@Valid @RequestBody EmployeeAddDTO emp) {
        RequestTokenBO requestToken = SmartRequestTokenUtil.getRequestUser();
        return employeeService.addEmployee(emp, requestToken);
    }

    @ApiOperation(value = "更新员工信息", notes = "@author yandanyang")
    @PostMapping("/employee/update")
    public ResponseDTO<String> updateEmployee(@Valid @RequestBody EmployeeUpdateDTO employeeUpdateDto) {
        return employeeService.updateEmployee(employeeUpdateDto);
    }

    @ApiOperation(value = "删除员工信息", notes = "@author yandanyang")
    @PostMapping("/employee/delete/{employeeId}")
    public ResponseDTO<String> deleteEmployeeById(@PathVariable("employeeId") Long employeeId) {
        return employeeService.deleteEmployeeById(employeeId);
    }*/

}
