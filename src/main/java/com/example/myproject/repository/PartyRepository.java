package com.example.myproject.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.myproject.domain.Party;
import com.example.myproject.domain.PartyType;

public interface PartyRepository extends JpaRepository<Party, Long> {
	Party findById(long id);
		
    List<Party> findPartyCreatedByUserId(long userId);
	
	@Modifying(clearAutomatically=true)
    @Transactional
    @Query("select p from Party p where partyAddress=:partyAddress") 
	List<Party> findByAddress(@Param("partyAddress") String partyAddress);
	
	@Modifying(clearAutomatically=true)
    @Transactional
    @Query("select p from Party p where partyStartTime>=:partyStartTime and partyEndTime<=:partyEndTime") 
	List<Party>findByDuration(@Param("partyStartTime")String partyStartTime,@Param("partyEndTime")String partyEndTime);
	
	@Modifying(clearAutomatically=true)
    @Transactional
    @Query("select p from Party p where partyType=:partyType") 
	List<Party>findByPartyType(@Param("partyType")PartyType partyType);
}
