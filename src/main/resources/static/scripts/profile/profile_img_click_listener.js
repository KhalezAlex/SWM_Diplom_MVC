let upic = $("#upic");

function img_click_listener() {
    upic.click();
}

upic.on('change', function() {
    console.log(upic.val());
    $("img_upic").attr('src', upic.val());
})