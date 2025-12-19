package com.rdc.weflow_server.repository.checklist;

import com.rdc.weflow_server.entity.checklist.Checklist;
import com.rdc.weflow_server.entity.checklist.QChecklist;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

public class ChecklistRepositoryImpl implements ChecklistRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QChecklist checklist = QChecklist.checklist;

    public ChecklistRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Checklist> findTemplatesByDynamicCriteria(
            Pageable pageable,
            String keyword,
            String category) {

        BooleanBuilder builder = new BooleanBuilder();

        // 1. 템플릿 필터링 (필수)
        builder.and(checklist.isTemplate.eq(true));

        // 2. 키워드 검색 (제목 또는 설명)
        if (StringUtils.hasText(keyword)) {
            String likeKeyword = "%" + keyword.toLowerCase() + "%";
            builder.and(
                    checklist.title.toLowerCase().like(likeKeyword)
                            .or(checklist.description.toLowerCase().like(likeKeyword))
            );
        }

        // 3. 카테고리 필터링
        if (StringUtils.hasText(category)) {
            builder.and(checklist.category.eq(category));
        }

        // 쿼리 실행
        List<Checklist> content = queryFactory
                .selectFrom(checklist)
                .where(builder)
                .orderBy(checklist.createdAt.desc()) // 정렬 기준 예시
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 카운트 쿼리
        Long total = queryFactory
                .select(checklist.count())
                .from(checklist)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }
}