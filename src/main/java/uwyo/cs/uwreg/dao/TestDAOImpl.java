package uwyo.cs.uwreg.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import uwyo.cs.uwreg.dao.model.Foo;

public class TestDAOImpl implements TestDAO {
	
    private JdbcTemplate jdbcTemplate = null;
    
    public TestDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

	@Override
	public List<Foo> getFoos() {
	    String sql = "SELECT a, b FROM foo";
	    List<Foo> foos = jdbcTemplate.query(sql, new RowMapper<Foo>() {
	 
	        @Override
	        public Foo mapRow(ResultSet rs, int rowNum) throws SQLException {
	            Foo foo = new Foo(rs.getInt("a"), rs.getInt("b"));
	            return foo;
	        }
	 
	    });
	 
		
		return foos;
	}
	
	@Override
	public void deleteFoo(int a) {
	    String sql = "DELETE FROM foo WHERE a=?";
	    jdbcTemplate.update(sql, a);
	}

}
