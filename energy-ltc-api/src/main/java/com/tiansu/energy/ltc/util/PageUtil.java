package com.tiansu.energy.ltc.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiansu.energy.ltc.common.domain.OrderItemDTO;
import com.tiansu.energy.ltc.common.domain.PageParamDTO;
import com.tiansu.energy.ltc.common.domain.PageResultDTO;

import java.util.List;
import java.util.stream.Collectors;

public class PageUtil {
    public static <T> PageResultDTO<T> convert2PageResult(IPage<T> page) {
        PageResultDTO<T> result = new PageResultDTO<T>();
        result.setPageNum(page.getCurrent());
        result.setPageSize(page.getSize());
        result.setTotal(page.getTotal());
        result.setPages(page.getPages());
        result.setList(page.getRecords());
        return result;
    }

    public static <T> Page<T> convert2QueryPage(PageParamDTO baseDTO) {
        Page<T> page = new Page<T>();

        List<OrderItemDTO> orders = baseDTO.getOrders();
        if (orders != null && !orders.isEmpty()) {
            List<com.baomidou.mybatisplus.core.metadata.OrderItem> orderItemList = orders.stream().map(PageUtil::convertOrderItem).collect(Collectors.toList());
            page.setOrders(orderItemList);
        }
        page.setCurrent(baseDTO.getPageNum());
        page.setSize(baseDTO.getPageSize());
        if (null != baseDTO.getSearchCount()) {
            page.setSearchCount(baseDTO.getSearchCount());
        }
        return page;
    }

    private static com.baomidou.mybatisplus.core.metadata.OrderItem convertOrderItem(OrderItemDTO orderItemDTO) {
        if (orderItemDTO.isAsc()) {
            return com.baomidou.mybatisplus.core.metadata.OrderItem.asc(orderItemDTO.getColumn());
        } else {
            return com.baomidou.mybatisplus.core.metadata.OrderItem.desc(orderItemDTO.getColumn());
        }
    }

    /**
     * 转换为 PageResultDTO 对象
     *
     * @param page
     * @param sourceList  原list
     * @param targetClazz 目标类
     * @return
     * @author yandanyang
     * @date 2018年5月16日 下午6:05:28
     */
    public static <T, E> PageResultDTO<T> convert2PageResult(IPage page, List<E> sourceList, Class<T> targetClazz) {
        PageResultDTO pageResultDTO = setPage(page);
        List<T> records = BeanUtil.copyList(sourceList, targetClazz);
        page.setRecords(records);
        pageResultDTO.setList(records);
        return pageResultDTO;
    }

    /**
     * 转换为 PageResultDTO 对象
     *
     * @param page
     * @param sourceList list
     * @return
     * @author yandanyang
     * @date 2018年5月16日 下午6:05:28
     */
    public static <T, E> PageResultDTO<T> convert2PageResult(IPage page, List<E> sourceList) {
        PageResultDTO pageResultDTO = setPage(page);
        page.setRecords(sourceList);
        pageResultDTO.setList(sourceList);
        return pageResultDTO;
    }

    private static PageResultDTO setPage(IPage page) {
        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setPageNum(page.getCurrent());
        pageResultDTO.setPageSize(page.getSize());
        pageResultDTO.setTotal(page.getTotal());
        pageResultDTO.setPages(page.getPages());
        return pageResultDTO;
    }
}
