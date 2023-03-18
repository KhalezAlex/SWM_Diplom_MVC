function tagsDeleteListener(input) {
    $.ajax({
        url: "/profile/activity/delete",
        method: "get",
        dataType: "html",
        data: {
            profileId: $("#profileId").val(),
            tag: input.getAttribute("value")},
        success: function(data) {
            input.remove();
            addTagToSelect(data);
            tagsAmount--;
        }
    })
}

function addTagToSelect(tag) {
    let option = document.createElement("option");
    option.innerHTML = tag;
    document.getElementById("profile_activities").appendChild(option);
}