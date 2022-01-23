package com.c0721g2srsrealestatebe.controller;

import com.c0721g2srsrealestatebe.dto.*;
import com.c0721g2srsrealestatebe.model.account.AppUser;
import com.c0721g2srsrealestatebe.model.account.Role;
import com.c0721g2srsrealestatebe.model.employee.Degree;
import com.c0721g2srsrealestatebe.model.employee.Employee;
import com.c0721g2srsrealestatebe.model.employee.Position;
import com.c0721g2srsrealestatebe.service.account.IRoleService;
import com.c0721g2srsrealestatebe.service.account.impl.AppUserServiceImpl;
import com.c0721g2srsrealestatebe.service.employee.IDegreeService;
import com.c0721g2srsrealestatebe.service.employee.IEmployeeService;
import com.c0721g2srsrealestatebe.service.employee.IPositionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(value = "/api/employee")
@CrossOrigin("http://localhost:4200")
public class EmployeeController {

    @Qualifier("employeeServiceImpl")
    @Autowired
    IEmployeeService iEmployeeService;

    @Qualifier("degreeServiceImpl")
    @Autowired
    IDegreeService iDegreeService;

    @Qualifier("positionServiceImpl")
    @Autowired
    IPositionService iPositionService;

    @Autowired
    AppUserServiceImpl appUserService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //Lấy danh sách position
    @GetMapping(value = "/position")
    public ResponseEntity<List<Position>> getPosition() {
        List<Position> positions = iPositionService.findAll();
        if (positions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(positions, HttpStatus.OK);
    }

    //Lấy danh sách degree
    @GetMapping(value = "/degree")
    public ResponseEntity<List<Degree>> getDegree() {
        List<Degree> degrees = iDegreeService.findAll();
        if (degrees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(degrees, HttpStatus.OK);
    }

    //hiển thị danh sách Employee (Hưng)
    @GetMapping(value = "/list")
    public ResponseEntity<Page<Employee>> showListEmployee(@PageableDefault(value = 10) Pageable pageable) {
        Page<Employee> employeeList = iEmployeeService.findAllEmployeePage(pageable);
        if (employeeList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    //Tìm kiếm nhân viên (Hưng)
    @GetMapping(value = "/search")
    public ResponseEntity<Page<Employee>> searchEmployee(@PageableDefault(value = 10) Pageable pageable,
                                                         @RequestParam(defaultValue = "") String name,
                                                         @RequestParam(defaultValue = "") String email,
                                                         @RequestParam(defaultValue = "") String position_id
    ) {
        Page<Employee> employeeListSearch = iEmployeeService.findAllEmployeeSearch(pageable, name, email, position_id);
        if (employeeListSearch.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employeeListSearch, HttpStatus.OK);
    }

    @GetMapping(value = "/detail/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable String id) {
        Optional<Employee> employee = iEmployeeService.findByIdOp(id);

        if (!employee.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(employee.get(), HttpStatus.OK);
    }

    //Xóa nhân viên (Hưng)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Employee> delete(@PathVariable String id) {
        Optional<Employee> employeeOptional = this.iEmployeeService.findByIdOp(id);
        if (!employeeOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        iEmployeeService.deleteById(id);
        return new ResponseEntity<>(employeeOptional.get(), HttpStatus.OK);
    }

    //Thịnh lấy id
    @GetMapping(value = "/edit/{id}")
    public ResponseEntity<Object> findByIdEmployee(@PathVariable String id) {
        Optional<Employee> employeeOptional = iEmployeeService.findById(id);
        if (!employeeOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        EmployeeEditDTO employeeEditDTO = new EmployeeEditDTO();
        BeanUtils.copyProperties(employeeOptional.get(), employeeEditDTO);
        DegreeDTO degreeDTO = new DegreeDTO(employeeOptional.get().getDegree().getId(), employeeOptional.get().getDegree().getName());
        employeeEditDTO.setDegreeDTO(degreeDTO);
        employeeEditDTO.setPositionDTO(new PositionDTO(employeeOptional.get().getPosition().getId(), employeeOptional.get().getPosition().getName()));

        Set<Role> roles = employeeOptional.get().getAppUser().getRoles();
        Long idRole = 0L;
        for (Role role : roles) {
            idRole = role.getId();
        }
        employeeEditDTO.setRoleDTO(idRole);
        return new ResponseEntity<Object>(employeeEditDTO, HttpStatus.OK);


    }

    //Thịnh create
    @PostMapping(value = "/create")
    public ResponseEntity<Object> createEmployee(@RequestBody @Valid EmployeeDTO employeeDTO,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Test error");
            return new ResponseEntity<>(bindingResult.getFieldErrors(),
                    HttpStatus.NOT_ACCEPTABLE);
        }
        //kiểm tra email có bị trùng lặp hay không
        Map<String, String> listErrors = new HashMap<>();
        if (appUserService.existsByUserName(employeeDTO.getEmail())) {
            System.out.println("Test");
            listErrors.put("errorEmail", "Email đã có người sử dụng");
            return ResponseEntity.badRequest().body(listErrors);
        }

        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);


        //set vị trí
        Position position = new Position();
        position.setId(employeeDTO.getPositionDTO().getId());
        employee.setPosition(position);


        //set bằng cấp
        Degree degree = new Degree();
        degree.setId(employeeDTO.getDegreeDTO().getId());
        employee.setDegree(degree);


        // Set role
        Role role = roleService.getRoleById(employeeDTO.getRoleDTO());
        Set<Role> roles = new HashSet<>();
        roles.add(role);


        //tạo account
        AppUser appUser = new AppUser();
        appUser.setUsername(employeeDTO.getEmail());
        appUser.setPassword(bCryptPasswordEncoder.encode("abc123456"));
        appUser.setEnabled(true);
        // appUser.setPassword("abc123456");
        appUser.setRoles(roles);
        appUser.setEnabled(true);

        employee.setAppUser(appUser);
        this.iEmployeeService.saveEmployee(employee);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Thịnh edit
    @PatchMapping(value = "/edit/{id}")
    public ResponseEntity<Object> updateEmployee(@RequestBody @Valid EmployeeEditDTO employeeDTO,
                                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError(), HttpStatus.NOT_ACCEPTABLE);

        }
        //kiểm tra email có bị trùng lặp hay không
        Map<String, String> listErrors = new HashMap<>();
        if (appUserService.existsByUserName(employeeDTO.getEmail())) {

            listErrors.put("errorEmail", "Email đã có người sử dụng");
            return ResponseEntity.badRequest().body(listErrors);
        }

        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);


        //set vị trí
        Position position = new Position();

        position.setId(employeeDTO.getPositionDTO().getId());
        employee.setPosition(position);


        //set bằng cấp
        Degree degree = new Degree();
        degree.setId(employeeDTO.getDegreeDTO().getId());
        employee.setDegree(degree);


        // Set role
        Role role = roleService.getRoleById(employeeDTO.getRoleDTO());
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        AppUser appUser = appUserService.getAppUserByEmployee(employee.getId());
        appUser.setRoles(roles);
        employee.setAppUser(appUser);

        this.iEmployeeService.saveEmployee(employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
}