package com.luka.sneksibetting.repositories;

import com.luka.sneksibetting.models.user.User;
import com.luka.sneksibetting.models.user.UserRegisterCreds;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.luka.jooq.generated.tables.Users.USERS;

@Repository
public class UserRepository {
    private final DSLContext dslContext;

    public UserRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public Optional<User> getUserByUsername(String username) {
        return dslContext
                .selectFrom(USERS)
                .where(USERS.USERNAME.eq(username))
                .fetchOptional()
                .map(r -> r.into(User.class));
    }

    public User registerUser(UserRegisterCreds creds) {
        return dslContext
                .insertInto(USERS)
                .set(USERS.USERNAME, creds.username())
                .set(USERS.PASSWORD, creds.password())
                .set(USERS.ID, UUID.randomUUID())
                .returning()
                .fetchOne(r -> r.into(User.class));
    }

    public User getUserByUserId(UUID userId) {
        return Objects.requireNonNull(dslContext.selectFrom(USERS).where(USERS.ID.eq(userId)).fetchOne()).map(r -> r.into(User.class));
    }
}
