package com.rdc.weflow_server.repository.post;

import com.rdc.weflow_server.entity.post.PostQuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostQuestionOptionRepository extends JpaRepository<PostQuestionOption, Long> {
}
