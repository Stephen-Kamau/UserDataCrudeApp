package com.userdata.userdatacrud.controlers;

import com.userdata.userdatacrud.models.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<UserData, Long> {
}
