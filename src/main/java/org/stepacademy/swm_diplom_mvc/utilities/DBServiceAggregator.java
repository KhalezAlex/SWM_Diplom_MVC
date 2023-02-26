package org.stepacademy.swm_diplom_mvc.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stepacademy.swm_diplom_mvc.model.dao.activity.activity.DBServiceActivity;
import org.stepacademy.swm_diplom_mvc.model.dao.activity.event.DBServiceEvent;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.customer.DBServiceCustomer;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.profile.DBServiceProfile;
import org.stepacademy.swm_diplom_mvc.model.dao.customer.role.DBServiceRole;
import org.stepacademy.swm_diplom_mvc.model.dao.location.city.DBServiceCity;

@Service
public class DBServiceAggregator {
    @Autowired
    public DBServiceCustomer customerService;

    @Autowired
    public DBServiceProfile profileService;

    @Autowired
    public DBServiceCity cityService;

    @Autowired
    public DBServiceRole roleService;

    @Autowired
    public DBServiceActivity activityService;

    @Autowired
    public DBServiceEvent eventService;
}
