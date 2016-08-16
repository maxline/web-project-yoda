package com.yoda.model.dao;

import com.yoda.model.entities.Category;
import com.yoda.model.idao.ICategoryDAO;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergey Mikhluk.
 */
public class CategoryDAO extends DAOBase implements ICategoryDAO {
    private static final Logger logger = Logger.getLogger(CategoryDAO.class.getName());
    private static final int INITIAL_CAPACITY = 5;
    //language=MySQL
    private static final String SELECT_ALL_CATEGORIES = "SELECT * FROM category ORDER BY categoryId";
    //language=MySQL
    private static final String SELECT_CATEGORY_BY_ID = "SELECT * FROM category WHERE categoryId=?";
    //language=MySQL
    private static final String INSERT_CATEGORY = "INSERT INTO category(name) VALUES(?)";
    //language=MySQL
    private static final String UPDATE_CATEGORY = "UPDATE category SET name=? WHERE categoryId=?";
    //language=MySQL
    private static final String DELETE_CATEGORY = "DELETE FROM category WHERE categoryId=?";

    @Override
    public Map<Long, String> findAll() {
        Map<Long, String> map = new HashMap<>(INITIAL_CAPACITY);
        ResultSet rs = executeSelect(SELECT_ALL_CATEGORIES);

        try {
            while (rs.next()) {
                Category category = new Category(
                        rs.getLong("categoryId"),
                        rs.getString("name")
                );

                map.put(category.getCategoryId(), category.getName());
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            close(rs);
        }
        return map;
    }

    @Override
    public Category findById(long id) {
        ResultSet rs = executeSelect(SELECT_CATEGORY_BY_ID, Collections.singletonList(id));
        try {
            if (rs.next()) {
                return new Category(
                        rs.getLong("categoryId"),
                        rs.getString("name")
                );
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            close(rs);
        }
        return null;
    }

    @Override
    public boolean add(Category category) {
        return executeUpdate(INSERT_CATEGORY,
                Collections.singletonList(category.getName()));
    }

    @Override
    public boolean update(Category category) {
        return executeUpdate(UPDATE_CATEGORY, Arrays.asList(
                category.getName(),
                category.getCategoryId()
        ));
    }

    @Override
    public boolean delete(long id) {
        return executeUpdate(DELETE_CATEGORY,
                Collections.singletonList(id));
    }
}
