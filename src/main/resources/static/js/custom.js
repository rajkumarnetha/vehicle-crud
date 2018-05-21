var page = 1;
var current_page = 1;
var total_page = 0;
var is_ajax_fire = 0;
var global_id = 0;


manageData();


/* manage data list */
function manageData() {
    $.ajax({
        dataType: 'json',
        url: url,
        data: {
            page: page
        }
    }).done(function(data) {

        total_page = data.total % 5;
        current_page = page;


        /* $('#pagination').twbsPagination({
              totalPages: total_page,
              visiblePages: current_page,
              onPageClick: function (event, pageL) {


                  page = pageL;


                  if(is_ajax_fire != 0){
                     getPageData();
                  }
              }
          });*/

        manageRow(data);
        is_ajax_fire = 1;
    });
}

/* Get Page Data*/
function getPageData() {
    $.ajax({
        url: url,
        data: {
            page: page
        }
    }).done(function(data) {
        manageRow(data);
    });
}


/* Add new Item table row */
function manageRow(data) {
    var rows = '';
    $.each(data, function(key, value) {
        rows = rows + '<tr>';
        rows = rows + '<td>' + value.type + '</td>';
        rows = rows + '<td>' + value.name + '</td>';
        rows = rows + '<td data-id="' + value.id + '">';
        rows = rows + '<button data-toggle="modal" data-target="#edit-item" class="btn btn-primary edit-item">Edit</button> ';
        rows = rows + '<button class="btn btn-danger remove-item">Delete</button>';
        rows = rows + '</td>';
        rows = rows + '</tr>';
    });
    $("tbody").html(rows);
}


/* Create new Item */
$(".crud-submit").click(function(e) {

    e.preventDefault();
    var form_action = $("#create-item").find("form").attr("action");
    var type = $("#create-item").find("select[name='title']").val();
    var name = $("#create-item").find("textarea[name='description']").val();

    $.ajax({
        type: 'post',
        url: form_action,
        data: {
            type: type,
            name: name
        }
    }).done(function(data) {
        getPageData();
        $(".modal").modal('hide');
        toastr.success('Item Created Successfully.', 'Success Alert', {
            timeOut: 5000
        });
    });


});

/* Search Item */
$(".crud-search").click(function(e) {

    e.preventDefault();
    var form_action = $("#search-item").find("form").attr("action");
    var type = $("#search-item").find("select[name='title']").val();
    var name = $("#search-item").find("textarea[name='description']").val();

    $.ajax({
        type: 'post',
        url: form_action,
        data: {
            type: type,
            name: name
        }
    }).done(function(data) {
    	manageRow(data);
        $(".modal").modal('hide');
        toastr.success('Search completed successfully.', 'Success Alert', {
            timeOut: 5000
        });
    });
});


/* Remove Item */
$("body").on("click", ".remove-item", function() {

    var id = $(this).parent("td").data('id');
    var c_obj = $(this).parents("tr");
    $.ajax({
        type: 'delete',
        url: '/deleteVehicle/' + id,
    }).done(function(data) {
        c_obj.remove();
        toastr.success('Item Deleted Successfully.', 'Success Alert', {
            timeOut: 5000
        });
        getPageData();
    });
});


/* Edit Item */
$("body").on("click", ".edit-item", function() {
    var id = $(this).parent("td").data('id');
    var type = $(this).parent("td").prev("td").prev("td").text();
    var name = $(this).parent("td").prev("td").text();
    global_id = id;

    $("#edit-item").find("input[name='title']").val(type);
    $("#edit-item").find("textarea[name='description']").val(name);
    $("#edit-item").find("form").attr("action", url + '/update/' + id);
});


/* Updated new Item */
$(".crud-submit-edit").click(function(e) {

    e.preventDefault();

    var id = global_id;
    var form_action = "/updateVehicle/"+id;
    var type = $("#edit-item").find("input[name='title']").val();
    var name = $("#edit-item").find("textarea[name='description']").val();

    $.ajax({
        type: 'PUT',
        url: form_action,
        data: {
            type: type,
            name: name
        }
    }).done(function(data) {
        getPageData();
        $(".modal").modal('hide');
        toastr.success('Item Updated Successfully.', 'Success Alert', {
            timeOut: 5000
        });
    });
    global_id = 0;

});