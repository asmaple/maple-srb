package com.maple.srb.core.mapper;

import com.maple.srb.core.pojo.dto.ExcelDictDTO;
import com.maple.srb.core.pojo.entity.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author ggq
 * @since 2022-02-01
 */
public interface DictMapper extends BaseMapper<Dict> {

    void insertBatch(List<ExcelDictDTO> list);
}
