package com.sangpt.teamchatspring.domain.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sangpt.teamchatspring.domain.entities.enums.MemberRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "member")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String role;

    @ManyToOne
    @JoinColumn(name = "server_id")
    @JsonIgnore
    private Server server;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Message> messages;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DirectMessage> directMessages;

    @OneToMany(mappedBy = "memberOne", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Conversation> conversationsInitiated;

    @OneToMany(mappedBy = "memberTwo", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Conversation> conversationsReceived;

    @PrePersist
    private void setDefaultValues() {
        if (role == null) {
            role = MemberRole.GUEST;
        }
    }

}
