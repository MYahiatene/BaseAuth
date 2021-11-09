package de.joayahiatene.baseauth.domain.profile;

import de.joayahiatene.baseauth.domain.user.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.UUID;

@Entity
@Data
class ProfilePicture {

    @Id
    @GeneratedValue
    UUID id;

    @Lob
    byte[] content;

    String name;

    String location;

    String username;

    public ProfilePicture(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public ProfilePicture() {

    }
}
