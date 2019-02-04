$(function () {
    //TODO remove all of this and add proper logic
    var prev = 0;

    $('.ttt-box').on('click', function (e) {

        if ($(this).hasClass('sign-o') || $(this).hasClass('sign-x')) {
            var notificationElement = NotificationViewManager.createNotification('Do Not Click Twice!', notificationSeverity.DANGER);
            NotificationViewManager.showNotification(notificationElement, 500);
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