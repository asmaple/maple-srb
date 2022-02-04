package com.maple.srb.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.maple.srb.core.listener.ExcelDictDTOListener;
import com.maple.srb.core.pojo.dto.ExcelDictDTO;
import com.maple.srb.core.pojo.entity.Dict;
import com.maple.srb.core.mapper.DictMapper;
import com.maple.srb.core.service.DictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author ggq
 * @since 2022-02-01
 */
@Slf4j
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Transactional(rollbackFor = {Exception.class}) //添加事务，一旦出现异常 全部回滚 避免导入一部分成功 一部分失败，无法再次导入的情况。事务一旦成功就证明成功了，一旦失败就回滚。
    @Override
    public void importData(InputStream inputStream) {
        EasyExcel.read(inputStream, ExcelDictDTO.class, new ExcelDictDTOListener(baseMapper)).sheet().doRead();
        log.info("Excel导入成功");
    }
}
