package com.atlas.tourguide.repositories;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.atlas.tourguide.domain.entities.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {
    @Query("SELECT c FROM Tag c LEFT JOIN FETCH c.posts")
    List<Tag> findAllWithPostCount();
    
    List<Tag> findByNameIn(Set<String> names);
}
