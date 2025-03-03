package com.atlas.tourguide.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atlas.tourguide.domain.entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

}
