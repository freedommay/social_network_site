package edu.zju.cst.demo.dao;

import edu.zju.cst.demo.model.Feed;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FeedDAO {
    String TABLE_NAME = " feed ";
    String INSERT_FIELDS = " created_date, user_id, data, type ";
    String SELECT_FIELDS = " id," + INSERT_FIELDS;

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{createdDate},#{userID},#{data},#{type})"})
    int addFeed(Feed feed);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id=#{id}"})
    Feed getFeedById(int id);

    List<Feed> selectUserFeeds(@Param("maxID") int maxID,
                               @Param("userIDs") List<Integer> userIDs,
                               @Param("count") int count);
}
