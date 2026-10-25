package com.bennu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bennu.model.Department;

@Mapper 
public interface DepartmentMapper {

    @Insert("""
        INSERT INTO department(name)
        VALUES(#{name})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "departmentId")
    int create(Department department);

    @Select("""
        SELECT department_id, name
        FROM department    
    """)
    List<Department> readAll();
    
    @Select("""
        SELECT department_id, name
        FROM department
        WHERE department_id = #{id}
    """)
    Department readById(Long id);

    @Update("""
        UPDATE department
        SET name = #{name}
        WHERE department_id = #{departmentId}
    """)
    int update(Department department);

    @Delete("""
        DELETE FROM department
        WHERE department_id = #{id}
    """)
    int delete(Long id);
    
    // auxiliares
    @Select("SELECT EXISTS(SELECT 1 FROM department WHERE department_id = #{id})")
    boolean exists(Long id);
}
