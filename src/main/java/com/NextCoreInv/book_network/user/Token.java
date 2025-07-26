package com.NextCoreInv.book_network.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Token {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String token;
    private LocalDateTime createdAt;
    @Column(name = "expiry_date")
    private LocalDateTime expiresAt;
    private LocalDateTime validatedAt;
    
    @Column(name = "type")
    private String tokenType = "ACTIVATION";

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_Id", nullable = false)
    private User user;

}
