package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.dto.Topic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TopicMapper extends BaseMapper<Topic> {
    @Select("""
        select db_topic.*, db_account.username, db_account.avatar
          from db_topic left join db_account on db_topic.uid = db_account.id
         order by db_topic.`time` desc limit ${start}, 10
        """)
    List<Topic> topicList(int start);

    @Select("""
        select db_topic.*, db_account.username, db_account.avatar
          from db_topic left join db_account on db_topic.uid = db_account.id
         where db_topic.type = #{type}
         order by db_topic.`time` desc limit ${start}, 10
        """)
    List<Topic> topicListByType(int start, int type);
}
