package com.maple.srb.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.srb.core.listener.ExcelDictDTOListener;
import com.maple.srb.core.mapper.DictMapper;
import com.maple.srb.core.mapper.FileInfoMapper;
import com.maple.srb.core.pojo.dto.ExcelDictDTO;
import com.maple.srb.core.pojo.entity.Dict;
import com.maple.srb.core.pojo.entity.FileInfo;
import com.maple.srb.core.service.DictService;
import com.maple.srb.core.service.FileInfoService;
import com.maple.srb.core.service.LendService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 文件信息 服务实现类
 * </p>
 *
 * @author ggq
 * @since 2022-02-01
 */
@Slf4j
@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements FileInfoService {

}
