package com.recruitment.app.infrastructure.persistence.posting;

import com.recruitment.app.domain.model.Posting;
import com.recruitment.app.domain.port.out.PostingDataPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface SpringDataPostingRepository extends JpaRepository<PostingJpaEntity, UUID> {
    @Query("select p from PostingJpaEntity p JOIN FETCH p.hiringManager WHERE p.recruiter.id = :id")
    List<PostingJpaEntity> findAllByRecruiterId(UUID id);
    @Query("select p from PostingJpaEntity p JOIN FETCH p.recruiter WHERE p.hiringManager.id = :id")
    List<PostingJpaEntity> findAllByHiringManagerId(UUID id);
    List<PostingJpaEntity> findAllByStatus(String status);
}

@Repository
@RequiredArgsConstructor
public class PostgresPostingDataAdapter implements PostingDataPort {
    private final SpringDataPostingRepository jpaRepository;

    @Override
    public Posting addPosting(Posting posting) {
        PostingJpaEntity entity = PostingMapper.toJpaEntity(posting);
        return PostingMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public List<Posting> findPostingsByIdForRecruiter(UUID id) {
        return jpaRepository.findAllByRecruiterId(id).stream().map(PostingMapper::toDomain).toList();
    }

    @Override
    public List<Posting> findPostingsByIdForHiringManager(UUID id) {
        return jpaRepository.findAllByHiringManagerId(id).stream().map(PostingMapper::toDomain).toList();
    }

    @Override
    public Optional<Posting> findById(UUID postingId) {
        return jpaRepository.findById(postingId).map(PostingMapper::toDomain);
    }

    @Override
    public List<Posting> findAllByStatus(String status) {
        return jpaRepository.findAllByStatus(status).stream().map(PostingMapper::toDomain).toList();
    }
}
