package com.maple.srb.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.srb.core.pojo.entity.Borrower;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maple.srb.core.pojo.vo.BorrowerApprovalVO;
import com.maple.srb.core.pojo.vo.BorrowerDetailVO;
import com.maple.srb.core.pojo.vo.BorrowerVO;

/**
 * <p>
 * 借款人 服务类
 * </p>
 *
 * @author ggq
 * @since 2022-02-01
 */
public interface BorrowerService extends IService<Borrower> {

    void saveBorrowerVOByUserId(BorrowerVO borrowerVO, Long userId);

    Integer getStatusByUserId(Long userId);

    IPage<Borrower> listPage(Page<Borrower> pageParam, String keyword);

    BorrowerDetailVO getBorrowerDetailVOById(Long id);

    void approval(BorrowerApprovalVO borrowerApprovalVO);
}
