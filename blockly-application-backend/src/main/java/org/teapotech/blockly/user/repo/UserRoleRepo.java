package org.teapotech.blockly.user.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.teapotech.blockly.user.entity.UserRole;
import org.teapotech.blockly.user.entity.UserRole.UserRolePK;


public interface UserRoleRepo extends JpaRepository<UserRole, UserRolePK> {

	@Query("select ur from UserRole ur where ur.primaryKey.userId=:userId")
	Set<UserRole> findByUserId(@Param("userId") String userId);

	@Query("delete from UserRole ur where ur.primaryKey.userId=:userId")
	@Modifying
	void deleteByUserId(@Param("userId") String userId);

}
