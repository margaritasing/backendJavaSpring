package alkemy.challenge.Challenge.Alkemy.service;

import alkemy.challenge.Challenge.Alkemy.model.Activity;
import alkemy.challenge.Challenge.Alkemy.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public Optional<Activity> getActivitiesByID(Long id) {
        return activityRepository.findById(id);
    }

    public ResponseEntity<Activity> updateActivities(@Valid Activity activity, Activity activityAct) {
            activityAct.setName(activity.getName());
            activityAct.setContent(activity.getContent());
            activityAct.setImage(activity.getImage());
            activityRepository.save(activityAct);
            return ResponseEntity.ok(activityAct);
    }

    public void save(Activity activity) {
        activityRepository.save(activity);
    }

    public Optional<Activity> findById(Long id) {
        return activityRepository.findByIdAndDeletedFalse(id);
    }

    public List<Activity> findAll() {
        return activityRepository.findAll();
    }
}
