package com.github.jakicdong.techub.service.statistics.converter;

import com.github.jakicdong.techub.api.model.vo.statistics.dto.StatisticsDayDTO;
import com.github.jakicdong.techub.service.statistics.repository.entity.RequestCountDO;
import com.github.jakicdong.techub.service.statistics.repository.entity.RequestCountExcelDO;
import com.github.jakicdong.techub.service.statistics.repository.entity.StatisticsDayExcelDO;

import java.util.List;
import java.util.stream.Collectors;

/*
* @author JakicDong
* @description 统计数据的转换器
* @time 2025/9/20 20:16
*/
public class StatisticsConverter {
    public static StatisticsDayExcelDO convertToExcelDO(StatisticsDayDTO dto) {
        StatisticsDayExcelDO excelDO = new StatisticsDayExcelDO();
        excelDO.setDate(dto.getDate());
        excelDO.setUvCount(dto.getUvCount());
        excelDO.setPvCount(dto.getPvCount());
        return excelDO;
    }

    public static RequestCountExcelDO ConvertToRequestCountDO(RequestCountDO requestCountDO) {
        RequestCountExcelDO excelDO = new RequestCountExcelDO();
        excelDO.setHost(requestCountDO.getHost());
        excelDO.setCnt(requestCountDO.getCnt());
        excelDO.setDate(requestCountDO.getDate());
        return excelDO;
    }

    public static List<RequestCountExcelDO> convertToRequestCountExcelDOList(List<RequestCountDO> requestCountDOList) {
        return requestCountDOList.stream()
                .map(StatisticsConverter::ConvertToRequestCountDO)
                .collect(Collectors.toList());
    }

    public static List<StatisticsDayExcelDO> convertToExcelDOList(List<StatisticsDayDTO> dtoList) {
        return dtoList.stream()
                .map(StatisticsConverter::convertToExcelDO)
                .collect(Collectors.toList());
    }
}
