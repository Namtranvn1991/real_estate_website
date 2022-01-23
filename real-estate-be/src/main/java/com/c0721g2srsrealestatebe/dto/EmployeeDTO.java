package com.c0721g2srsrealestatebe.dto;

import com.c0721g2srsrealestatebe.model.account.AppUser;
import com.c0721g2srsrealestatebe.model.account.Role;
import com.c0721g2srsrealestatebe.model.image.Image;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Period;

public class EmployeeDTO implements Validator {
    private String id;
    @NotBlank(message = "Không được bỏ trống tên")
    @Pattern(regexp = "^[a-zA-ZàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ]+(\\s[a-zA-ZàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ]+)*$", message = "Không được nhập số")
    @Size(min = 6, max = 40, message = "Tên phải từ 6 đến 40 ký tự")
    private String name;

    @NotBlank(message = "Không được bỏ trống email")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+.[a-z]{2,6}$")
//    @Size(min = 8, max = 40, message = "Email phải từ 6 đến 40 ký tự")
    private String email;

    //    @Pattern(regexp = "^(09|\\(84\\)\\+9)[01]\\d{7}$")
    @NotBlank(message = "Không được bỏ trống số điện thoại")
    @Pattern(regexp = "^(0?)(3[2-9]|5[6|89]|7[0|6-9]|8[0-6|89]|9[0-4|6-9])[0-9]{7}$", message = "Phải nhập đúng định dạng số điện thoại")
    private String phoneNumber;

    @NotBlank(message = "Không được bỏ trống địa chỉ")
    @Size(min = 6, max = 255, message = "Địa chỉ phải từ 6 đến 255 ký tự")
    private String address;

    @NotNull(message = "Không được để trống ngày sinh")
//    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Không đúng định dạng ngày sinh")
    private LocalDate dateOfBirth;

        @NotBlank(message = "Không được để trống CMND")
    @Pattern(regexp = "^([0-9]{9})$|([0-9]{12})$",
            message = "Số CMND phải đúng định dạng: XXXXXXXXX hoặc XXXXXXXXXXXX.")
    private String idCard;

    private Integer gender;

    private DegreeDTO degreeDTO;

    private PositionDTO positionDTO;

    private AppUser appUser;

    private Image image;

    private Boolean deleted = Boolean.FALSE;

    private Long roleDTO;
    //tạo ra 1 employeeEditdto

    public Long getRoleDTO() {
        return roleDTO;
    }

    public void setRoleDTO(Long roleDTO) {
        this.roleDTO = roleDTO;
    }

    public EmployeeDTO() {
        //comment

    }


    public DegreeDTO getDegreeDTO() {
        return degreeDTO;
    }

    public void setDegreeDTO(DegreeDTO degreeDTO) {
        this.degreeDTO = degreeDTO;
    }

    public PositionDTO getPositionDTO() {
        return positionDTO;
    }

    public void setPositionDTO(PositionDTO positionDTO) {
        this.positionDTO = positionDTO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

//        EmployeeDTO employee = (EmployeeDTO) target;
//
//        try {
//            LocalDate birthDay = LocalDate.parse(employee.dateOfBirth);
//            LocalDate today = LocalDate.now();
//            if (Period.between(birthDay, today).getYears() < 18) {
//                errors.rejectValue("dateOfBirth", "birthday18");
//
//            }
//        } catch (Exception e) {
//            errors.rejectValue("employeeBirthday", "birthday.empty");
//        }

    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", idCard='" + idCard + '\'' +
                ", gender=" + gender +
                ", degreeDTO=" + degreeDTO +
                ", positionDTO=" + positionDTO +
                ", appUser=" + appUser +
                ", image=" + image +
                ", deleted=" + deleted +
                '}';
    }
}
