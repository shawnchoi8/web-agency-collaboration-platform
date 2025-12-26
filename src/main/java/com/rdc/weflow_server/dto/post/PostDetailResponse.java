package com.rdc.weflow_server.dto.post;

import com.rdc.weflow_server.entity.post.PostApprovalStatus;
import com.rdc.weflow_server.entity.post.PostOpenStatus;
import com.rdc.weflow_server.entity.project.ProjectPhase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailResponse {

    private Long postId;
    private String title;
    private String content;
    private PostApprovalStatus status;
    private PostOpenStatus openStatus;
    private AuthorDto author;
    private ProjectPhase projectPhase;
    private StepDto step;
    private List<FileDto> files;
    private List<LinkDto> links;
    private List<QuestionDto> questions;
    private ParentPostDto parentPost;
    private Boolean isEdited;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthorDto {
        private Long memberId;
        private String name;
        private String role;
        private String companyName;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StepDto {
        private Long stepId;
        private String stepName;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileDto {
        private Long fileId;
        private String fileName;
        private Long fileSize;
        private String downloadUrl;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LinkDto {
        private Long linkId;
        private String url;
        private String title;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionDto {
        private Long questionId;
        private String content;
        private String questionType; // SINGLE, MULTI, TEXT
        private List<QuestionOptionDto> options;
        private AnswerDto answer;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionOptionDto {
        private Long optionId;
        private String optionText;
        private Boolean hasInput;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnswerDto {
        private List<Long> selectedOptionIds;
        private String textInput;
        private RespondentDto respondent;
        private LocalDateTime respondedAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RespondentDto {
        private Long memberId;
        private String name;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ParentPostDto {
        private Long postId;
        private String title;
        private AuthorDto author;
    }
}
