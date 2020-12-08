package com.myz.npp.service.accountbook.dao.mapper;

import com.myz.npp.service.accountbook.dao.model.AccountBook;
import com.myz.npp.service.accountbook.dao.model.AccountBookExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface AccountBookMapper {
    @SelectProvider(type=AccountBookSqlProvider.class, method="countByExample")
    long countByExample(AccountBookExample example);

    @DeleteProvider(type=AccountBookSqlProvider.class, method="deleteByExample")
    int deleteByExample(AccountBookExample example);

    @Delete({
        "delete from account_book",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into account_book (user_id, month, ",
        "type_id, amount, ",
        "money, create_time, ",
        "update_time, status)",
        "values (#{userId,jdbcType=VARCHAR}, #{month,jdbcType=VARCHAR}, ",
        "#{typeId,jdbcType=INTEGER}, #{amount,jdbcType=INTEGER}, ",
        "#{money,jdbcType=INTEGER}, #{createTime,jdbcType=VARCHAR}, ",
        "#{updateTime,jdbcType=VARCHAR}, #{status,jdbcType=SMALLINT})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(AccountBook record);

    @InsertProvider(type=AccountBookSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(AccountBook record);

    @SelectProvider(type=AccountBookSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR),
        @Result(column="month", property="month", jdbcType=JdbcType.VARCHAR),
        @Result(column="type_id", property="typeId", jdbcType=JdbcType.INTEGER),
        @Result(column="amount", property="amount", jdbcType=JdbcType.INTEGER),
        @Result(column="money", property="money", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.SMALLINT)
    })
    List<AccountBook> selectByExample(AccountBookExample example);

    @Select({
        "select",
        "id, user_id, month, type_id, amount, money, create_time, update_time, status",
        "from account_book",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR),
        @Result(column="month", property="month", jdbcType=JdbcType.VARCHAR),
        @Result(column="type_id", property="typeId", jdbcType=JdbcType.INTEGER),
        @Result(column="amount", property="amount", jdbcType=JdbcType.INTEGER),
        @Result(column="money", property="money", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.SMALLINT)
    })
    AccountBook selectByPrimaryKey(Long id);

    @UpdateProvider(type=AccountBookSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") AccountBook record, @Param("example") AccountBookExample example);

    @UpdateProvider(type=AccountBookSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") AccountBook record, @Param("example") AccountBookExample example);

    @UpdateProvider(type=AccountBookSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(AccountBook record);

    @Update({
        "update account_book",
        "set user_id = #{userId,jdbcType=VARCHAR},",
          "month = #{month,jdbcType=VARCHAR},",
          "type_id = #{typeId,jdbcType=INTEGER},",
          "amount = #{amount,jdbcType=INTEGER},",
          "money = #{money,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=VARCHAR},",
          "update_time = #{updateTime,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=SMALLINT}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(AccountBook record);
}