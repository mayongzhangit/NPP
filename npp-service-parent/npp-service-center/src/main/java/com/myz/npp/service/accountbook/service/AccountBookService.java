package com.myz.npp.service.accountbook.service;

import com.myz.common.enums.StatusEnum;
import com.myz.common.exception.MyzBizException;
import com.myz.common.util.DateMonthUtil;
import com.myz.common.util.DateUtil;
import com.myz.npp.service.accountbook.dao.mapper.AccountBookMapper;
import com.myz.npp.service.accountbook.dao.model.AccountBook;
import com.myz.npp.service.accountbook.dao.model.AccountBookExample;
import com.myz.npp.service.accountbook.enums.NetTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.List;

/**
 * @author yzMa
 * @desc
 * @date 2020/12/4 15:19
 * @email 2641007740@qq.com
 */
@Slf4j
@Service
public class AccountBookService {

    @Autowired
    private AccountBookMapper accountBookMapper;

    /**
     *
     * @param userId
     * @return
     */
    public List<AccountBook> getCurrentMonth(String userId){
        String nowMothStr = DateMonthUtil.nowMothStr();

        AccountBookExample example = new AccountBookExample();
        AccountBookExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andMonthEqualTo(nowMothStr);
        return accountBookMapper.selectByExample(example);
    }

    /**
     *
     * @param userId
     * @return
     * @throws ParseException
     */
    public AccountBook getCurrentDay(String userId,int typeId) throws ParseException {
        String truncateDayStr = DateUtil.truncateDayStr();
        String tomorrowTruncateDateStr = DateUtil.truncateDayAndAddDayStr(1);

        AccountBookExample example = new AccountBookExample();
        AccountBookExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andTypeIdEqualTo(typeId);
        criteria.andCreateTimeBetween(truncateDayStr,tomorrowTruncateDateStr);
        List<AccountBook> accountBooks = accountBookMapper.selectByExample(example);
        return CollectionUtils.isEmpty(accountBooks)?null:accountBooks.get(0);
    }

    /**
     * 记账（每日）  有@Transactional情况下，mybatis一级缓存才会生效
     * @param userId
     * @param typeId
     * @param amount
     */
    @Transactional(rollbackFor = Exception.class)
    public int bookkeepingDaily(String userId, int typeId, int amount) throws ParseException {
        AccountBook currentDayAccountBook = this.getCurrentDay(userId,typeId);
        if (currentDayAccountBook != null){
            throw new MyzBizException("20021207","今日已记账",userId,typeId,amount);
        }

        int row = save(userId, typeId, amount);
        return row;
    }

    /**
     *
     * @param userId
     * @param typeId
     * @param amount
     * @return
     */
    public int save(String userId, int typeId, int amount){

        NetTypeEnum netTypeEnum = NetTypeEnum.match(typeId);
        String nowMothStr = DateMonthUtil.nowMothStr();

        AccountBook accountBook = new AccountBook();
        accountBook.setMonth(nowMothStr);
        accountBook.setUserId(userId);
        accountBook.setTypeId(typeId);
        accountBook.setMoney(amount * netTypeEnum.getPrice());
        accountBook.setAmount(amount);
        String nowDateStr = DateUtil.nowDateStr();
        accountBook.setCreateTime(nowDateStr);
        accountBook.setUpdateTime(nowDateStr);
        accountBook.setStatus(StatusEnum.DEFAULT.getStatus());

        int insert = accountBookMapper.insert(accountBook);
        return insert;
    }
}
