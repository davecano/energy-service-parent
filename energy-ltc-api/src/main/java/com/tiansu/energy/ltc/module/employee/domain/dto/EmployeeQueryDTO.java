package com.tiansu.energy.ltc.module.employee.domain.dto;
import com.tiansu.energy.ltc.common.domain.PageParamDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 员工列表DTO
 *
 * @author dd
 * @date 2021年01月21日上午09:09:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeQueryDTO extends PageParamDTO {

    private String phone;

    private String actualName;

    private String keyword;

    private Long departmentId;

    private Integer isLeave;

    private Integer isDisabled;

    /**
     * 删除状态 0否 1是
     */
    @ApiModelProperty("删除状态 0否 1是 不需要传")
    private Integer isDelete;

    @ApiModelProperty("员工id集合")
    private List<Long> employeeIds;
}
