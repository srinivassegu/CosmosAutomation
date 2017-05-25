package com.org.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.org.dao.EmployeeDao;
import com.org.model.EmployeeBean;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    private static Logger logger = LoggerFactory.getLogger(EmployeeDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public EmployeeBean getHumanResourceEmployee(int organizationId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT emp.first_name, emp.last_name, emp.mobile_contact as emp_contact,  dept.department_name, org.address, org.name as org_name, org.id as org_id, org.mobile_contact as org_contact, org.fax_contact ");
        sb.append("FROM automation.employee_tbl emp ");
        sb.append("INNER JOIN automation.department_tbl dept ON emp.department_id=dept.id ");
        sb.append("INNER JOIN automation.organization_tbl org ON dept.organization_id=org.id ");
        sb.append("WHERE org.id=?");

        try {

            return this.getJdbcTemplate().queryForObject(sb.toString(), new Object[] { organizationId }, (resultSet, rowNum) -> {
                EmployeeBean bean = new EmployeeBean();
                bean.setEmployeeName(resultSet.getString("first_name") + resultSet.getString("last_name"));
                bean.setEmployeeDepartment(resultSet.getString("department_name"));
                bean.setEmployeeContact(resultSet.getString("emp_contact"));
                bean.setOrganizationName(resultSet.getString("org_name"));
                bean.setOrganizationId(resultSet.getInt("org_id"));
                bean.setOrganizationAddress(resultSet.getString("address"));
                bean.setOrganizationContact(resultSet.getString("org_contact"));
                bean.setOrganizationFaxContact(resultSet.getString("fax_contact"));
                return bean;
            });
        } catch (IncorrectResultSizeDataAccessException e) {
            String error = String.format("No resultset while retreiving employee details for organizationid: %s", organizationId);
            logger.error(error);
            throw new RuntimeException(error);
        }
    }
}
