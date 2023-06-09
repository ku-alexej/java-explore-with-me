package ru.practicum.stats_server.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "stats", schema = "public")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "app_name", nullable = false)
    String app;

    @Column(name = "uri", nullable = false)
    String uri;

    @Column(name = "user_ip", nullable = false)
    String ip;

    @Column(name = "created", nullable = false)
    LocalDateTime timestamp;

}
