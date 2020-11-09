package com.bookshop.repository;

import com.bookshop.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByBookIdAndUserId(Long bookId, Long UserId);
}
