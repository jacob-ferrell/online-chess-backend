package com.jacobferrell.chess.model;

import org.springframework.data.jpa.repository.JpaRepository;



public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Notification findById(long id);
}