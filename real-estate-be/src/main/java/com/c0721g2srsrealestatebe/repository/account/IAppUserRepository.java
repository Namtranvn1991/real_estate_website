package com.c0721g2srsrealestatebe.repository.account;

import com.c0721g2srsrealestatebe.model.account.AppUser;
import com.c0721g2srsrealestatebe.model.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface IAppUserRepository extends JpaRepository<AppUser,UUID> {

    //Tung
    boolean existsByUsername(String username);

    @Modifying
    @Query(value = "insert into app_users(username,password) values (:username,:password)", nativeQuery = true)
    void addNewAccount(String username, String password);


    //Hien
    @Query(value=  "SELECT * FROM app_users WHERE username= :username" ,nativeQuery=true)
    AppUser getAppUserByUsername(@Param("username") String username);

    @Query(value=  "SELECT * FROM app_users a " +
            " JOIN employees e ON a.id = e.app_user_id  WHERE e.email= :email" ,nativeQuery=true)
    AppUser getAppUserByEmailEmployee(@Param("email") String email);

    @Query(value=  "SELECT * FROM app_users a " +
            " JOIN customers c ON a.id = c.app_user_id WHERE c.email= :email" ,nativeQuery=true)
    AppUser getAppUserByEmailCustomer(@Param("email") String email);

    @Query(value=  "SELECT a.username FROM app_users a JOIN customers c ON a.id = c.app_user_id WHERE c.email= :email" ,nativeQuery=true)
    String existsUserByEmail(@Param("email") String email);

    @Modifying
    @Query(value ="update app_users set verification_code=?1 where username =?2",nativeQuery = true)
    void addVerificationCode(String code,String username);

    @Modifying
    @Query(value ="update app_users set password=?1 where verification_code=?2",nativeQuery = true)
    void saveNewPassword( String passwordEncode, String code);

    @Query(value = "SELECT * FROM app_users WHERE verification_code=:code" ,nativeQuery=true)
    AppUser findUserByVerificationCode(@Param("code") String code);

    @Modifying
    @Query(value ="update app_users set verification_code=null where username=?1",nativeQuery = true)
    void deleteVerificationCode( String username);


    @Query(value = "select real_estate_news.app_users.password from real_estate_news.app_users where real_estate_news.app_users.username = ?", nativeQuery = true)
    String findPasswordByUsername(String username);

    Optional<AppUser> findAppUserByUsername(String id);

    @Query(value = "SELECT * FROM app_users a join employees e on a.id = e.app_user_id where e.id =?1", nativeQuery = true)
    AppUser findByEmployee(String id);
}