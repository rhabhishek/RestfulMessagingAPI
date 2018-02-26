package challenge.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import challenge.entity.User;

@Repository
public class UserRepositoryImpl implements UserRepository{

	@Autowired
	   private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public List<User> findUser(String name) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("handle", name);

		return namedParameterJdbcTemplate.query(
                "SELECT id, handle, name FROM people where handle=:handle",namedParameters,
                (rs, rowNum) -> new User(rs.getInt("id"),
                rs.getString("handle"), rs.getString("name"))
        );
	}

	@Override
	public List<User> getUsersIFollow(User user) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", user.getId());

		return namedParameterJdbcTemplate.query(
                "select p.id, p.handle,p.name from followers f, people p where p.id=f.person_id and f.follower_person_id=:id",namedParameters,
                (rs, rowNum) -> new User(rs.getInt("id"),
                rs.getString("handle"), rs.getString("name"))
        );
	}
	
	@Override
	public List<User> getFollowers(User user) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", user.getId());

		return namedParameterJdbcTemplate.query(
                "select p.id, p.handle,p.name from followers f, people p where p.id=f.follower_person_id and f.person_id=:id",namedParameters,
                (rs, rowNum) -> new User(rs.getInt("id"),
                rs.getString("handle"), rs.getString("name"))
        );
	}

	@Override
	public User getPopularFollower(User user) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", user.getId());

		return namedParameterJdbcTemplate.queryForObject(
                "SELECT id, handle, name from people where id = (select person_id from (select count(follower_person_id) c,person_id "
                + "from followers where person_id in (select follower_person_id from followers where person_id=:id)"
                + " group by person_id order by c desc limit 1))",namedParameters,
                (rs, rowNum) -> new User(rs.getInt("id"),
                rs.getString("handle"), rs.getString("name"))
        );
	}

	@Override
	public List<User> getAllUsers() {
		return namedParameterJdbcTemplate.query(
                "SELECT id, handle, name FROM people",
                (rs, rowNum) -> new User(rs.getInt("id"),
                rs.getString("handle"), rs.getString("name"))
        );
	}

	@Override
	public int doFollow(User user, User toFollow) {
		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("userId", toFollow.getId())
				.addValue("followerId", user.getId());
		return namedParameterJdbcTemplate.update("insert into followers (person_id, follower_person_id) VALUES (:userId,:followerId)", namedParameters);
	}

	@Override
	public int doUnfollow(User user, User toUnFollow) {
		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("userId", toUnFollow.getId())
				.addValue("followerId", user.getId());
		return namedParameterJdbcTemplate.update("delete from followers where person_id=:userId and follower_person_id=:followerId", namedParameters);
	}

	@Override
	public List<Integer> checkFollow(User user, User paramUser) {
		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("personId", paramUser.getId())
				.addValue("followerId", user.getId());

		return namedParameterJdbcTemplate.query(
                "select id from followers where follower_person_id=:followerId and person_id=:personId",namedParameters,
                (rs, rowNum) -> (rs.getInt("id"))
                );
	}
	
	
	
	
}
