package com.atlas.tourguide.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atlas.tourguide.domain.entities.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {

}
