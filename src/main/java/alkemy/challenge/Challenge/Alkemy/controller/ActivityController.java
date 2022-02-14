package alkemy.challenge.Challenge.Alkemy.controller;

import alkemy.challenge.Challenge.Alkemy.exception.ActivitiesNotFoundException;
import alkemy.challenge.Challenge.Alkemy.model.Activity;
import alkemy.challenge.Challenge.Alkemy.service.ActivityService;
import alkemy.challenge.Challenge.Alkemy.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@ApiIgnore
@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping
    public List<Activity> listActivities(){
        return activityService.findAll();
    }

    @PostMapping
    public ResponseEntity<Activity> createActivities(@RequestBody @Valid Activity activity) {
        activityService.save(activity);
        return ResponseEntity.ok(activity);
    }

    /*Endpoint para actualizar actividades */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateActivities(@PathVariable Long id, @Valid @RequestBody Activity activity) {
        Optional<Activity> activityAct = activityService.findById(id);
        if (activityAct.isEmpty()) {
            return new ResponseEntity(new Message("no se ha encontrado una categoria con el id: "+id),
                    HttpStatus.NOT_FOUND);
        }
        return activityService.updateActivities(activity, activityAct.get());
    }

    @GetMapping("/{id}")
    public Optional<Activity> getActivitiesByID(@PathVariable Long id) {
        Optional<Activity> a = activityService.getActivitiesByID(id);
        if (a.isEmpty()) {
            throw new ActivitiesNotFoundException("Activities with ID[" + id + "] not found");
        }
        return a;
    }
}
