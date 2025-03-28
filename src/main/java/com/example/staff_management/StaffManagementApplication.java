package com.example.staff_management;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@Slf4j
@SpringBootApplication
public class StaffManagementApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(StaffManagementApplication.class, args);
//        beanAndComponentExample(context);
//        profileAndConfigExample(context);
//        DIExample(context);
//        beanScopeExample(context);
//        lazyEagerExample(context);
//        cascadeExample(context);
//        mapperExample(context);
//        nativeQueryExample(context);
//        JPQLExample(context);
//        CustomQueryWithContructorExample(context);
//        ProjectionExample(context);
//       JointableExample(context);

    }

    static void beanAndComponentExample(ApplicationContext context) {
        SpringBean bean = context.getBean(SpringBean.class);
        bean.print();

        SpringComponent component = context.getBean(SpringComponent.class);
        component.print();
    }

    static void profileAndConfigExample(ApplicationContext context) {
        DevProfileBean profileBean = context.getBean("devProfileBean", DevProfileBean.class);
        profileBean.print();

        LocalProfileBean localProfileBean = context.getBean("localProfileBean", LocalProfileBean.class);
        localProfileBean.print();
    }

    static void DIExample(ApplicationContext context) {
        ExampleBean exampleBean = context.getBean(ExampleBean.class);
        exampleBean.print();
    }

    static void beanScopeExample(ApplicationContext context) {
        SingletonBean bean = context.getBean(SingletonBean.class);
        SingletonBean bean2 = context.getBean(SingletonBean.class);
        System.out.println("Same Singleton Bean: " + (bean == bean2));

        PrototypeBean prototypeBean = context.getBean(PrototypeBean.class);
        PrototypeBean prototypeBean2 = context.getBean(PrototypeBean.class);
        System.out.println("Same Prototype Bean: " + (prototypeBean == prototypeBean2));
    }

    static void lazyEagerExample(ApplicationContext context) {
        Loader loader = context.getBean(Loader.class);
        loader.load();
    }

    static void cascadeExample(ApplicationContext context) {
        Loader loader = context.getBean(Loader.class);
//        loader.persist();
//        loader.delete(7);
    }

    static void mapperExample(ApplicationContext context) {
        Employee employee = Employee.builder()
                .id(1)
                .email("email@email.com")
                .name("An")
                .build();

        EmployeeService service = context.getBean(EmployeeService.class);
        System.out.println("mapstruct: " + service.convertToDto(employee));

        System.out.println("modelMapper: " + service.convertToDtoUsingModelMapper(employee));

    }

    static void nativeQueryExample(ApplicationContext context) {
        StaffRepository staffRepository = context.getBean(StaffRepository.class);
        Staff staff = staffRepository.findStaffByEmailNativeQuery("hyusieunhan@gmail.com");
        log.info("Staff: {}", staff.getEmail() + " " + staff.getFirstName());
    }

    static void JPQLExample(ApplicationContext context) {
        StaffRepository staffRepository = context.getBean(StaffRepository.class);
        // get milisecond
        Staff staff = staffRepository.findStaffByEmailJPQL("hyusieunhan@gmail.com");
        log.info("Staff: {}", staff.getEmail() + " " + staff.getFirstName());
    }

    static void CustomQueryWithContructorExample(ApplicationContext context) {
        StaffRepository staffRepository = context.getBean(StaffRepository.class);
        StaffDto staffDto = staffRepository.findStaffDtoByEmailContructor("hyusieunhan@gmail.com");
        log.info("StaffDto: {}", staffDto);
    }

    static void ProjectionExample(ApplicationContext context) {
        StaffRepository staffRepository = context.getBean(StaffRepository.class);
        StaffProjection staffProjection = staffRepository.findStaffProjectionByEmail("hyusieunhan@gmail.com");
        log.info("StaffProjection: {}", staffProjection.getFullname());
    }

    static void JointableExample(ApplicationContext context) {
        StaffRepository staffRepository = context.getBean(StaffRepository.class);
        List<StaffCheckinProjection> checkinProjection = staffRepository.findStaffWithoutCheckinOrCheckoutToday();
        // log the list
        checkinProjection.forEach(staffCheckinProjection -> {
            log.info("Staff: {}", staffCheckinProjection.getEmail());
        });
    }



}
