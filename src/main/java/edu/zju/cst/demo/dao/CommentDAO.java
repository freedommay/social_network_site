package edu.zju.cst.demo.dao;

import edu.zju.cst.demo.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentDAO {
    String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " content, user_id, entity_id, entity_type, created_date, status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{content}, #{userID}, #{entityID}, #{entityType}, #{createdDate}, #{status})"})
    int addComment(Comment comment);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, " where id=#{id}"})
    Comment getCommentByID(int id);

    @Update({"update", TABLE_NAME, "set status=#{status} where entity_id=#{entityId} and entity_type=#{entityType}"})
    void updateStatus(@Param("entityID") int entityID, @Param("entityType") int entityType, @Param("status") int status);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME,
            " where entity_id=#{entityID} and entity_type=#{entityType} order by id desc"})
    List<Comment> selectByEntity(@Param("entityID") int entityID, @Param("entityType") int entityType);

    @Select({"select count(id) from", TABLE_NAME, " where entity_id=#{entityID} and entity_type=#{entityType}"})
    int getCommentCount(@Param("entityID") int entityID, @Param("entityType") int entityType);

    @Select({"select count(id) from", TABLE_NAME, " where user_id=#{userID}"})
    int getUserCommentCount(int userId);
}
