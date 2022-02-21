package com.maple.srb.core.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.common.result.R;

public class PageUtils {

    private PageUtils() {}

    /**
     * 封装分页数据
     * @param pageModel
     * @param <T> 指定泛型
     * @return 返回包装过的分页数据
     */
    public static <T> R page(IPage<T> pageModel)  {
        return R.ok().data("list",pageModel.getRecords()).data("current",pageModel.getCurrent()).data("size",pageModel.getSize()).data("pages",pageModel.getPages()).data("total",pageModel.getTotal());
    }
}
