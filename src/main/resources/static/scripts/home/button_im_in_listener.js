function button_im_in_listener(component) {
    console.log(component.id);
    $.ajax({
        url: "/event/participate",
        type: "POST",
        dataType: "html",
        data: {eventId: component.id}
    })
}