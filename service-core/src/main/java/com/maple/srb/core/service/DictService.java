package com.maple.srb.core.service;

import com.maple.srb.core.pojo.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author ggq
 * @since 2022-02-01
 */
public interface DictService extends IService<Dict> {

    void importData(InputStream inputStream);

}
