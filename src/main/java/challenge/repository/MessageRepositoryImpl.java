package challenge.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import challenge.entity.Message;
import challenge.entity.User;

@Repository
public class MessageRepositoryImpl implements MessageRepository{
	   @Autowired
	   private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	   
	@Override
	public List<Message> getMessages(User user) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", user.getId());

		return namedParameterJdbcTemplate.query(
                "SELECT id, person_id, content FROM messages where person_id=:id",namedParameters,
                (rs, rowNum) -> new Message(rs.getInt("id"),
                        rs.getInt("person_id"), rs.getString("content"))
        );

	}

	@Override
	public List<Message> searchMessages(User user, String keyword) {
		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("id", user.getId())
				.addValue("keyword", "%"+keyword+"%");

		return namedParameterJdbcTemplate.query(
                "SELECT id, person_id, content FROM messages where person_id=:id and content like :keyword",namedParameters,
                (rs, rowNum) -> new Message(rs.getInt("id"),
                        rs.getInt("person_id"), rs.getString("content"))
        );
	}

}
