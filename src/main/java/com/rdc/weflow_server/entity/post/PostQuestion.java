package com.rdc.weflow_server.entity.post;

import com.rdc.weflow_server.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post_questions")
@Entity
public class PostQuestion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 질문 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post; // 게시글 ID

    @Column(columnDefinition = "TEXT", nullable = false)
    private String questionText; // 질문 내용

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private QuestionType questionType; // 질문 타입 (SINGLE, MULTI, TEXT)

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PostQuestionOption> options = new ArrayList<>(); // 질문 옵션들

}
