package com.example.staff_management.service;

import com.example.staff_management.dto.StaffDto;
import com.example.staff_management.entities.Staff;
import com.example.staff_management.example.bean_component.SpringBean;
import com.example.staff_management.example.bean_component.SpringComponent;
import com.example.staff_management.example.bean_scope_life_cycle.PrototypeBean;
import com.example.staff_management.example.bean_scope_life_cycle.SingletonBean;
import com.example.staff_management.example.di.ExampleBean;
import com.example.staff_management.example.lazy_eager.Loader;
import com.example.staff_management.example.mapper.Employee;
import com.example.staff_management.example.mapper.EmployeeService;
import com.example.staff_management.example.profile_configuration.DevProfileBean;
import com.example.staff_management.example.profile_configuration.LocalProfileBean;
import com.example.staff_management.repository.StaffRepository;
import com.example.staff_management.repository.projection.StaffCheckinProjection;
import com.example.staff_management.repository.projection.StaffProjection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExampleService {

    private final ApplicationContext context;

    public void beanAndComponentExample() {
        SpringBean bean = context.getBean(SpringBean.class);
        bean.print();

        SpringComponent component = context.getBean(SpringComponent.class);
        component.print();
    }

    public void profileAndConfigExample() {
        DevProfileBean profileBean = context.getBean("devProfileBean", DevProfileBean.class);
        profileBean.print();

        LocalProfileBean localProfileBean = context.getBean("localProfileBean", LocalProfileBean.class);
        localProfileBean.print();
    }

    public void DIExample() {
        ExampleBean exampleBean = context.getBean(ExampleBean.class);
        exampleBean.print();
    }

    public void beanScopeExample() {
        SingletonBean bean = context.getBean(SingletonBean.class);
        SingletonBean bean2 = context.getBean(SingletonBean.class);
        System.out.println("Same Singleton Bean: " + (bean == bean2));

        PrototypeBean prototypeBean = context.getBean(PrototypeBean.class);
        PrototypeBean prototypeBean2 = context.getBean(PrototypeBean.class);
        System.out.println("Same Prototype Bean: " + (prototypeBean == prototypeBean2));
    }

    public void lazyEagerExample() {
        Loader loader = context.getBean(Loader.class);
        loader.load();
    }

    public void cascadeExample() {
        Loader loader = context.getBean(Loader.class);
        loader.persist();
//        loader.delete(7);
    }

    public void mapperExample() {
        Employee employee = Employee.builder()
                .id(1)
                .email("email@email.com")
                .name("An")
                .build();

        EmployeeService service = context.getBean(EmployeeService.class);
        System.out.println("mapstruct: " + service.convertToDto(employee));

        System.out.println("modelMapper: " + service.convertToDtoUsingModelMapper(employee));

    }

    public void nativeQueryExample() {
        StaffRepository staffRepository = context.getBean(StaffRepository.class);
        Staff staff = staffRepository.findStaffByEmailNativeQuery("hyusieunhan@gmail.com");
        log.info("Staff: {}", staff.getEmail() + " " + staff.getFirstName());
    }

    public void JPQLExample() {
        StaffRepository staffRepository = context.getBean(StaffRepository.class);
        // get milisecond
        Staff staff = staffRepository.findStaffByEmailJPQL("hyusieunhan@gmail.com");
        log.info("Staff: {}", staff.getEmail() + " " + staff.getFirstName());
    }

    public void CustomQueryWithContructorExample() {
        StaffRepository staffRepository = context.getBean(StaffRepository.class);
        StaffDto staffDto = staffRepository.findStaffDtoByEmailContructor("hyusieunhan@gmail.com");
        log.info("StaffDto: {}", staffDto);
    }

    public void ProjectionExample() {
        StaffRepository staffRepository = context.getBean(StaffRepository.class);
        StaffProjection staffProjection = staffRepository.findStaffProjectionByEmail("hyusieunhan@gmail.com");
        log.info("StaffProjection: {}", staffProjection.getFullname());
    }

    public void JoinTableExample() {
        StaffRepository staffRepository = context.getBean(StaffRepository.class);
        List<StaffCheckinProjection> checkinProjection = staffRepository.findStaffWithoutCheckinOrCheckoutToday();
        // log the list
        checkinProjection.forEach(staffCheckinProjection -> {
            log.info("Staff: {}", staffCheckinProjection.getEmail());
        });
    }
}
