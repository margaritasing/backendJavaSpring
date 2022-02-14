package alkemy.challenge.Challenge.Alkemy.dataseed;

import alkemy.challenge.Challenge.Alkemy.model.*;
import alkemy.challenge.Challenge.Alkemy.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    SlideRepository slideRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    TestimonyRepository testimonyRepository;

    @Autowired
    ContactRepository contactRepository;

    @Override
    public void run(String... args) throws Exception {
        loadActivitiesData();
        loadNewsData();
        loadUsersData();
        loadSlidesData();
        loadMembersData();
        loadCommentsData();
        loadTestimonialsData();
        loadContactsData();
    }

    private void loadActivitiesData() {
        if (activityRepository.count() == 0) {
            Activity activity1 = new Activity(
                    "NombreActividadPRUEBA1",
                    "SoyElContenidoActividad",
                    "SoyUnaImagenActividad");
            Activity activity2 = new Activity(
                    "NombreActividadPRUEBA2",
                    "SoyElContenidoActividad",
                    "SoyUnaImagenActividad");
            activityRepository.save(activity1);
            activityRepository.save(activity2);
        }
        System.out.println(activityRepository.count());
    }

    private void loadNewsData() {
        Category category1 = new Category("news","news","image");
        categoryRepository.save(category1);
        for (int i = 0; i < 10; i++) {
            newsRepository.save(new News("name", "imagePath", "content", category1));
        }
    }

    private void loadUsersData() {
        Role userRol = new Role("ROLE_USER", "rol de user");
        Role adminRol = new Role("ROLE_ADMIN", "rol de admin");
        roleRepository.save(userRol);
        roleRepository.save(adminRol);
        userRepository.save(new User("firstName", "lastName", "user@alkemy.com", bCryptPasswordEncoder.encode("password"), "photo", userRol));
        userRepository.save(new User("firstName", "lastName", "admin@alkemy.com", bCryptPasswordEncoder.encode("password"), "photo", adminRol));
        for (int i = 0; i < 10 ; i++) {
            userRepository.save(new User("firstName", "lastName", "user" + i + "@alkemy.com", bCryptPasswordEncoder.encode("user"), "photo", userRol));
            userRepository.save(new User("firstName", "lastName", "admin" + i + "@alkemy.com", bCryptPasswordEncoder.encode("admin"), "photo", adminRol));
        }
    }

    private void loadSlidesData() {
        Organization organization1 = new Organization("name", "image", "address", 123, "email", "welcomeText", "aboutUsText");
        organizationRepository.save(organization1);
        for (int i = 1; i <= 5; i++) {
            slideRepository.save(new Slide( "imageUrl","Texto de ejemplo nro: "+i, i, organization1));
        }
    }

    private void loadMembersData(){
        for (int i = 0; i < 10; i++) {
            memberRepository.save(new Member("name","facebookUrl","instagramUrl","linkedinUrl","image","description"));
        }
    }

    private void loadCommentsData() {
        for (int i = 1; i <= 5 ; i++) {
            for (int j = 0; j < 2; j++) {
                commentRepository.save(new Comment("body", (userRepository.findById(1L).get()), newsRepository.findById(i)));
            }
        }
    }

    private void loadTestimonialsData() {
        for (int i = 0; i < 5 ; i++) {
                testimonyRepository.save(new Testimony("name","image","content"));
        }
    }

    private void loadContactsData() {
        for (int i = 0; i < 3 ; i++) {
            contactRepository.save(new Contact("name",123456789,"email","message"));
        }
    }


}
