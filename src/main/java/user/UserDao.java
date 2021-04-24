package user;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(User.class)
public interface UserDao {
    @SqlUpdate("""
        CREATE TABLE felhasznalok (
            id IDENTITY PRIMARY KEY,
            username VARCHAR NOT NULL,
            password VARCHAR NOT NULL,
            name VARCHAR NOT NULL,
            email VARCHAR NOT NULL,
            gender ENUM('MALE', 'FEMALE') NOT NULL,
            birthDate DATE NOT NULL,
            enabled BOOLEAN NOT NULL
        )
        """
    )
    void createTable();

    @SqlUpdate("INSERT INTO felhasznalok (username, password, name, email, gender, birthDate, enabled) VALUES (:username, :password, :name, :email, :gender, :birthDate, :enabled)")
    @GetGeneratedKeys("id")
    long insertUser(@BindBean User felhasznalo);

    @SqlQuery("SELECT * FROM felhasznalok WHERE id = :id")
    Optional<User> getUserById(@Bind("id") Long id);

    @SqlQuery("SELECT * FROM felhasznalok WHERE username = :username")
    Optional<User> getUserByUsername(@Bind("username") String username);

    @SqlUpdate("DELETE FROM felhasznalok WHERE id = :id")
    void deleteUser(@BindBean User felhasznalo);

    @SqlQuery("SELECT * FROM felhasznalok ORDER BY id")
    List<User> listUsers();
}
