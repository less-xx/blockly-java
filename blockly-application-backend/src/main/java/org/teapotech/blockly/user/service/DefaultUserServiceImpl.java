/**
 * 
 */
package org.teapotech.blockly.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teapotech.base.service.UserService;
import org.teapotech.blockly.user.repo.UserAccountRepo;
import org.teapotech.user.LogonUserInfo;
import org.teapotech.user.UserDelegate;

/**
 * @author jiangl
 *
 */
@Service
public class DefaultUserServiceImpl implements UserService {

	private static Logger LOG = LoggerFactory.getLogger(DefaultUserServiceImpl.class);

	@Autowired
	private UserAccountRepo userRepo;

	@Override
	public UserDelegate findByUserId(String userId) {
		return userRepo.findById(userId).orElseGet(null);
	}

	@Override
	public void userDidLogon(LogonUserInfo logonInfo) {
		LOG.info("User logon: {},{}", logonInfo.getLogonUser().getUserId(), logonInfo.getSessionId());
	}

}
