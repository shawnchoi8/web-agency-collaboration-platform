package com.rdc.weflow_server.entity.post;

import com.rdc.weflow_server.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post_question_options")
@Entity
public class PostQuestionOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 옵션 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private PostQuestion question; // 질문 ID

    @Column(columnDefinition = "TEXT", nullable = false)
    private String optionText; // 옵션 텍스트

    @Column(nullable = false)
    private Boolean hasInput = false; // 기타 입력 허용 여부 (객관식에서만 사용)

}
