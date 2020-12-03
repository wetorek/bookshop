package com.bookshop.repository;

import com.bookshop.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByBookIdAndUserId(Long bookId, Long UserId);

    List<Vote> findAllByBookId(Long bookId);
}
