package com.bennu.mapper;

import com.bennu.model.Employee;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


@Mapper // Indica que debe tratarse como un Mapper
public interface EmployeeMapper {
    // Hay una anotación por cada tipo de operación (insert, update, select, delete)

    @Insert("""
        INSERT INTO employee(department_id, boss_id, name, salary)
        VALUES(#{departmentId}, #{bossId}, #{name}, #{salary})
    """)
    int create(Employee employee);

    @Select("SELECT * FROM employee")
    List<Employee> readAll();
    
    @Select("SELECT * FROM employee WHERE employee_id = #{id}")
    Employee readById(Long id);

    @Update("""
        UPDATE employee
        SET department_id=#{departmentId}, boss_id=#{bossId}, name=#{name}, salary=#{salary}
        WHERE employee_id=#{employeeId}
    """)
    int update(Employee employee);

    @Delete("""
        DELETE FROM employee
        WHERE employee_id=#{id}
    """)
    int delete(Long id);

    // auxiliares
    @Select("SELECT EXISTS(SELECT 1 FROM employee WHERE employee_id = #{id})")
    boolean exists(Long id);

}