package com.myz.npp.service.user.dao.mapper;

import com.myz.npp.service.user.dao.model.User;
import com.myz.npp.service.user.dao.model.UserExample;
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

public interface UserMapper {
    @SelectProvider(type=UserSqlProvider.class, method="countByExample")
    long countByExample(UserExample example);

    @DeleteProvider(type=UserSqlProvider.class, method="deleteByExample")
    int deleteByExample(UserExample example);

    @Delete({
        "delete from npp_user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into npp_user (user_id, platform_id, ",
        "app_id, type, mobile, ",
        "email, passwd, nick_name, ",
        "avatar_url, gender, country, ",
        "province, city, ",
        "create_time, update_time, ",
        "status)",
        "values (#{userId,jdbcType=VARCHAR}, #{platformId,jdbcType=VARCHAR}, ",
        "#{appId,jdbcType=VARCHAR}, #{type,jdbcType=SMALLINT}, #{mobile,jdbcType=VARCHAR}, ",
        "#{email,jdbcType=VARCHAR}, #{passwd,jdbcType=VARCHAR}, #{nickName,jdbcType=VARCHAR}, ",
        "#{avatarUrl,jdbcType=VARCHAR}, #{gender,jdbcType=BIT}, #{country,jdbcType=VARCHAR}, ",
        "#{province,jdbcType=VARCHAR}, #{city,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=VARCHAR}, #{updateTime,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=SMALLINT})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(User record);

    @InsertProvider(type=UserSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(User record);

    @SelectProvider(type=UserSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR),
        @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR),
        @Result(column="app_id", property="appId", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.SMALLINT),
        @Result(column="mobile", property="mobile", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="passwd", property="passwd", jdbcType=JdbcType.VARCHAR),
        @Result(column="nick_name", property="nickName", jdbcType=JdbcType.VARCHAR),
        @Result(column="avatar_url", property="avatarUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="gender", property="gender", jdbcType=JdbcType.BIT),
        @Result(column="country", property="country", jdbcType=JdbcType.VARCHAR),
        @Result(column="province", property="province", jdbcType=JdbcType.VARCHAR),
        @Result(column="city", property="city", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.SMALLINT)
    })
    List<User> selectByExample(UserExample example);

    @Select({
        "select",
        "id, user_id, platform_id, app_id, type, mobile, email, passwd, nick_name, avatar_url, ",
        "gender, country, province, city, create_time, update_time, status",
        "from npp_user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR),
        @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR),
        @Result(column="app_id", property="appId", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.SMALLINT),
        @Result(column="mobile", property="mobile", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="passwd", property="passwd", jdbcType=JdbcType.VARCHAR),
        @Result(column="nick_name", property="nickName", jdbcType=JdbcType.VARCHAR),
        @Result(column="avatar_url", property="avatarUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="gender", property="gender", jdbcType=JdbcType.BIT),
        @Result(column="country", property="country", jdbcType=JdbcType.VARCHAR),
        @Result(column="province", property="province", jdbcType=JdbcType.VARCHAR),
        @Result(column="city", property="city", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.SMALLINT)
    })
    User selectByPrimaryKey(Long id);

    @UpdateProvider(type=UserSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    @UpdateProvider(type=UserSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    @UpdateProvider(type=UserSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(User record);

    @Update({
        "update npp_user",
        "set user_id = #{userId,jdbcType=VARCHAR},",
          "platform_id = #{platformId,jdbcType=VARCHAR},",
          "app_id = #{appId,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=SMALLINT},",
          "mobile = #{mobile,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "passwd = #{passwd,jdbcType=VARCHAR},",
          "nick_name = #{nickName,jdbcType=VARCHAR},",
          "avatar_url = #{avatarUrl,jdbcType=VARCHAR},",
          "gender = #{gender,jdbcType=BIT},",
          "country = #{country,jdbcType=VARCHAR},",
          "province = #{province,jdbcType=VARCHAR},",
          "city = #{city,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=VARCHAR},",
          "update_time = #{updateTime,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=SMALLINT}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(User record);
}