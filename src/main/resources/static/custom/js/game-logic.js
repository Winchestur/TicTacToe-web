$(function () {
    //TODO remove all of this and add proper logic
    var prev = 0;

    $('.ttt-box').on('click', function (e) {

        if ($(this).hasClass('sign-o') || $(this).hasClass('sign-x')) {
            alert("Do Not Click Twice!");
            return;
        }

        if (prev % 2 === 0) {
            $(this).addClass('sign-x');
        }
        else {
            $(this).addClass('sign-o');
        }
        prev++;
    });
});