package com.nixsolutions.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nixsolutions.exception.DBException;
import com.nixsolutions.exception.Messages;
import com.nixsolutions.model.Role;

@Repository("userProfileDao")
public class HibernateRoleDao extends AbstractDao<Integer, Role> implements RoleDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(HibernateRoleDao.class);

	@Override
	@Transactional(readOnly = true)
	public Role findById(int id) {
		return getByKey(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Role findByName(String name) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("name", name));
		return (Role) crit.uniqueResult();
	}

	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Role> findAll() {
		Criteria crit = createEntityCriteria();
		crit.addOrder(Order.asc("name"));
		return crit.list();
	}

	@Override
	public void create(Role role) {
		LOGGER.trace("Create role {} ", role);
		try {
			persist(role);
		} catch (Exception ex) {
			LOGGER.error(Messages.ERR_CANNOT_CREATE_ROLE, ex);
			throw new DBException(Messages.ERR_CANNOT_CREATE_ROLE, ex);
		}
	}

	@Override
	public void update(Role role) {
		LOGGER.trace("Update role {} ", role);
		try {
			update(role);
		} catch (Exception ex) {
			LOGGER.error(Messages.ERR_CANNOT_UPDATE_ROLE, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_ROLE, ex);
		}
	}

	@Override
	public void remove(Role role) {
		LOGGER.trace("Remove role {} ", role);
		try {
			remove(role);
		} catch (Exception ex) {
			LOGGER.error(Messages.ERR_CANNOT_REMOVE_ROLE, ex);
			throw new DBException(Messages.ERR_CANNOT_REMOVE_ROLE, ex);
		}
	}

}
