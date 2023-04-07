function tagsDeleteListener(img) {
    let str = img.getAttribute('src');
    $.ajax({
        url: "/profile/activity/delete",
        method: "get",
        dataType: "html",
        data: {
            profileId: $("#input_profile_id").val(),
            tagId: str.substring(22, str.length - 4)},
        success: function(data) {
            img.remove();
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