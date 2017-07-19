package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * Created by joenedumgottil on 7/19/17.
 */
public class JdbcTimeEntryRepository implements TimeEntryRepository{

    private DataSource dataSource;
    private JdbcTemplate template;

    public JdbcTimeEntryRepository(DataSource source) {
        this.dataSource = source;
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "insert into time_entries (project_id, user_id, date, hours) values(?,?,?,?)");

            statement.setLong(1, timeEntry.getProjectId());
            statement.setLong(2, timeEntry.getUserId());
            statement.setString(3, timeEntry.getDate());
            statement.setInt(4, timeEntry.getHours());

            return statement;
        }, generatedKeyHolder);

        return get(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public TimeEntry get(Long id) {
        return template.query(
            "SELECT id,project_id,user_id,date,hours FROM time_entries WHERE id=?",
                new Object[]{id},
                extractor);
    }

    @Override
    public List<TimeEntry> list() {
        return template.query("SELECT id, project_id, user_id, date, hours FROM time_entries",
                mapper);
    }

    @Override
    public TimeEntry update(Long id, TimeEntry timeEntry) {
        template.update("UPDATE time_entries " +
                        "SET project_id = ?, user_id = ?, date = ?,  hours = ? " +
                        "WHERE id = ?",
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                timeEntry.getDate(),
                timeEntry.getHours(),
                id);

        return get(id);
    }

    @Override
    public void delete(Long id) {
        template.update("DELETE FROM time_entries WHERE id = ?", id);
    }

    private final RowMapper<TimeEntry> mapper = (rs, rowNum) -> new TimeEntry(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getString("date"),
            rs.getInt("hours")
    );

    private final ResultSetExtractor<TimeEntry> extractor =
            (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;
}
