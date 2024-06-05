package com.our.chat.dao;

import com.our.chat.dto.FriendRequestDTO;
import com.our.chat.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendsRequestRepository extends JpaRepository<FriendsRequest,Integer> {

    @Query("SELECT NEW com.our.chat.dto.FriendRequestDTO(u.account, u.name,u.avatar_url,f.status) " +
            "FROM User u INNER JOIN FriendsRequest f ON u.account IN (f.sender_id, f.receiver_id) " +
            "WHERE u.account != :accountId AND (:accountId IN (f.sender_id, f.receiver_id))")
    List<FriendRequestDTO> findFriendsRequestByAccount(@Param("accountId") Integer accountId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE FriendsRequest fr SET fr.status = :newStatus WHERE fr.sender_id = :senderId AND fr.receiver_id = :receiverId")
    int updateStatusBySenderIdAndReceiverId(@Param("senderId") Integer senderId,
                                            @Param("receiverId") Integer receiverId,
                                            @Param("newStatus") String newStatus);



}
