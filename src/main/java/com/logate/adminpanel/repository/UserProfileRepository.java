package com.logate.adminpanel.repository;

import com.logate.adminpanel.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the UserProfile entity.
 */
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    @Query("select distinct userProfile from UserProfile userProfile left join fetch userProfile.roles left join fetch userProfile.workGroups left join fetch userProfile.departments")
    List<UserProfile> findAllWithEagerRelationships();

    @Query("select userProfile from UserProfile userProfile left join fetch userProfile.roles left join fetch userProfile.workGroups left join fetch userProfile.departments where userProfile.id =:id")
    UserProfile findOneWithEagerRelationships(@Param("id") Long id);

}
