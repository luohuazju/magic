package cn.sccl.tasksupervisor.commons.base;

import java.io.Serializable;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

public class BaseDAO implements Serializable {

	private static final long serialVersionUID = -6460312218594909700L;

	//数据源
	protected DataSource dataSource;

	@Autowired
	public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(this.dataSource);
	}

}
