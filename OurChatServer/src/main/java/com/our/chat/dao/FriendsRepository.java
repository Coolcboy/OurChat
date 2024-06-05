package com.our.chat.dao;

import com.our.chat.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendsRepository  extends JpaRepository<Friends,Integer>{

    @Query("SELECT NEW com.our.chat.dto.UserDTO(u.account, u.name,u.avatar_url) " +
            "FROM User u INNER JOIN Friends f ON u.account IN (f.sender_id, f.receiver_id) " +
            "WHERE u.account != :accountId AND (:accountId IN (f.sender_id, f.receiver_id))")
    List<UserDTO> findFriendsByAccount(@Param("accountId") Integer accountId);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END " +
            "FROM Friends f " +
            "WHERE (f.sender_id = :firstAccountId AND f.receiver_id = :secondAccountId) " +
            "OR (f.sender_id = :secondAccountId AND f.receiver_id = :firstAccountId)")
    boolean isFriend(@Param("firstAccountId") Integer firstAccountId, @Param("secondAccountId") Integer secondAccountId);
}
