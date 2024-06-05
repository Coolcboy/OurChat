package com.our.chat.dao;


import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository  extends JpaRepository<Chat_Messages,Long>, MessageRepositoryCustom{

}
