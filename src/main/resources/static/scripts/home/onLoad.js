console.log($("#select_city").val());

$.ajax({
    url: "/home/onLoad",
    method: "get",
    dataType: "html",
    data: {
        city: $("#select_city").val()
    },
    success: function(data) {
        console.log(data);
    }
})