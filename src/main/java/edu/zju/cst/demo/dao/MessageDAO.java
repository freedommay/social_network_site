package edu.zju.cst.demo.dao;

import edu.zju.cst.demo.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageDAO {
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " from_id, to_id, content, has_read, created_date, conversation_id ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{fromID}, #{toID}, #{content}, #{hasRead}, #{createdDate}, #{conversationID})"})
    int addMessage(Message message);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, " where conversation_id=#{conversationID} order by id desc limit #{offset}, #{limit}"})
    List<Message> getConversationDetail(@Param("conversationID") String conversationId,
                                        @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(id) from", TABLE_NAME, "where has_read=0 and to_id=#{userID} and conversation_id=#{conversationID}"})
    int getConversationUnreadCount(@Param("userID") int userID, @Param("conversationID") String conversationID);

    @Select({"select", INSERT_FIELDS, ", count(id) as id from (select * from", TABLE_NAME, "where from_id=#{userID} or to_id=#{userID} order by id desc) tt group by conversation_id order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationList(@Param("userID") int userID,
                                      @Param("offset") int offset, @Param("limit") int limit);
}

