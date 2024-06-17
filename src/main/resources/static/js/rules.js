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

function runAllRules() {
    $.ajax({
        url: `/myfinances/rule/runAll`,
        method: 'GET',
        success: function(response) {
            console.log(`rules run successfully`);
        },
        error: function(xhr, status, error) {
            console.log(`Rules failed to run: ${error}`);
        }
    });
}