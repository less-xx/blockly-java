package org.teapotech.blockly.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teapotech.blockly.user.entity.UserAccount;


public interface UserAccountRepo extends JpaRepository<UserAccount, String> {

}
