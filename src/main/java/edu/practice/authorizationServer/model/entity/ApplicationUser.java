package edu.practice.authorizationServer.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
@JsonDeserialize(builder = ApplicationUser.ApplicationUserBuilder.class)
@Builder(builderClassName = "ApplicationUserBuilder", toBuilder = true)
@EqualsAndHashCode(of = {"username", "role", "enabled"})
@ToString(exclude = {"password"})
public class ApplicationUser {

    @Id
    @GeneratedValue
    @Getter
    @Setter(AccessLevel.NONE)
    private UUID uuid;

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter(AccessLevel.NONE)
    private ApplicationRole role;

    @Getter
    @Setter
    @Lob
    private String username;

    @Getter
    @Setter
    @Lob
    @JsonIgnore
    private String password;

    @Getter
    @Setter
    private boolean enabled;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ApplicationUserBuilder {

    }
}
