function profileUpdateActivation() {
    $("#input_name").removeAttr("disabled");
    $("#input_age").removeAttr("disabled");
    $("#input_phone").removeAttr("disabled");
    $("#input_city").hide();
    $("#select_city").show();
    $("#tags_row").show();
    $("#profile_activities").removeAttr("disabled");
    $("#button_submit").show();
    $("#button_cancel").show();
}