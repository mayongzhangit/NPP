package com.myz.npp.service.usermapping.dao.mapper;

import com.myz.npp.service.usermapping.dao.model.UserMapping;
import com.myz.npp.service.usermapping.dao.model.UserMappingExample;
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

public interface UserMappingMapper {
    @SelectProvider(type=UserMappingSqlProvider.class, method="countByExample")
    long countByExample(UserMappingExample example);

    @DeleteProvider(type=UserMappingSqlProvider.class, method="deleteByExample")
    int deleteByExample(UserMappingExample example);

    @Delete({
        "delete from npp_user_mapping",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into npp_user_mapping (user_id, mobile, ",
        "email, create_time, ",
        "update_time, status)",
        "values (#{userId,jdbcType=VARCHAR}, #{mobile,jdbcType=VARCHAR}, ",
        "#{email,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{status,jdbcType=SMALLINT})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(UserMapping record);

    @InsertProvider(type=UserMappingSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(UserMapping record);

    @SelectProvider(type=UserMappingSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR),
        @Result(column="mobile", property="mobile", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="status", property="status", jdbcType=JdbcType.SMALLINT)
    })
    List<UserMapping> selectByExample(UserMappingExample example);

    @Select({
        "select",
        "id, user_id, mobile, email, create_time, update_time, status",
        "from npp_user_mapping",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR),
        @Result(column="mobile", property="mobile", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="status", property="status", jdbcType=JdbcType.SMALLINT)
    })
    UserMapping selectByPrimaryKey(Long id);

    @UpdateProvider(type=UserMappingSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") UserMapping record, @Param("example") UserMappingExample example);

    @UpdateProvider(type=UserMappingSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") UserMapping record, @Param("example") UserMappingExample example);

    @UpdateProvider(type=UserMappingSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserMapping record);

    @Update({
        "update npp_user_mapping",
        "set user_id = #{userId,jdbcType=VARCHAR},",
          "mobile = #{mobile,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "status = #{status,jdbcType=SMALLINT}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserMapping record);
}