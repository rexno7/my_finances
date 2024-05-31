$(document).ready(function() {
    $(".delete-button").on("click", function() {
        var id = $(this).data("id");

        $.ajax({
            url: `/myfinances/rule/${id}`,
            method: 'DELETE',
            contentType: 'appliction/json',
            success: function(response) {
                console.log(`${id} Deleted`);
                location.reload();
            },
            error: function(xhr, status, error) {
                console.log(`Delete failed for rule ${id}: ${error}`);
            }
        });
    });
});